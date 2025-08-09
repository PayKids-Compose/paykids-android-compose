package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.quiz.QuizModel
import com.paykidscompose.common.repository.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.collections.List

class GetWrongAnswerQuizzesUseCase(
    private val quizRepository: QuizRepository
) : FlowUseCase<GetWrongAnswerQuizzesUseCase.Params, DataResourceResult<List<QuizModel>>>() {
    override fun execute(params: Params?): Flow<DataResourceResult<List<QuizModel>>> {
        if (params == null) {
            return flowOf(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = "퀴즈 요청 파라미터가 없습니다."
                    )
                )
            )
        }

        return flow<DataResourceResult<List<QuizModel>>> {
            coroutineScope {
                // 오답 번호 리스트 가져오기
                val wrongNumberResult = quizRepository.getWrongAnswerQuizNumbers(params.stage)
                    .first { it is DataResourceResult.Success || it is DataResourceResult.Failure }

                val wrongNumbers = (wrongNumberResult as? DataResourceResult.Success)?.data
                    ?: throw PayKidsException.ToastException(-1, "오답 번호를 불러올 수 없습니다.")

                // 병렬로 오답 퀴즈 가져오기
                val quizResults = wrongNumbers.map { number ->
                    async {
                        quizRepository.getQuiz(params.stage, number)
                            .first { it is DataResourceResult.Success || it is DataResourceResult.Failure }
                    }
                }.awaitAll()

                val wrongQuizzes = quizResults.mapNotNull { it as? DataResourceResult.Success }
                    .map { it.data }

                emit(DataResourceResult.Success(wrongQuizzes))
            }
        }.catch {
            emit(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(-1, "오답 퀴즈 로딩 실패")
                )
            )
        }
    }

    data class Params(
        val stage: Int
    )
}
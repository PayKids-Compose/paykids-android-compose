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

class GetAllQuizzesUseCase(
    private val quizRepository: QuizRepository
) : FlowUseCase<GetAllQuizzesUseCase.Params, DataResourceResult<List<QuizModel>>>() {
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
                // 첫 번째 퀴즈 가져오기(퀴즈 데이터에 전체 퀴즈 개수가 포함되어 있기 때문에 첫 번째 퀴즈 먼저 로드)
                val firstResult = quizRepository.getQuiz(params.stage, 1)
                    .first { it is DataResourceResult.Success || it is DataResourceResult.Failure }

                val firstQuiz = (firstResult as? DataResourceResult.Success)?.data
                    ?: throw PayKidsException.ToastException(-1, "퀴즈 데이터를 불러올 수 없습니다.")

                val totalCount = firstQuiz.count
                // 나머지 퀴즈 병렬로 가져오기
                val otherResults = (2..totalCount).map { number ->
                    async {
                        quizRepository.getQuiz(params.stage, number)
                            .first { it is DataResourceResult.Success || it is DataResourceResult.Failure }
                    }
                }.awaitAll()
                    .mapNotNull { result ->
                        (result as? DataResourceResult.Success)?.data
                    }

                emit(DataResourceResult.Success(listOf(firstQuiz) + otherResults))
            }
        }.catch {
            emit(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(-1, "퀴즈 전체 로딩 실패)")
                )
            )
        }
    }

    data class Params(
        val stage: Int
    )
}
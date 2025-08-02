package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.WrongAnswerNoteStatus
import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GetWrongAnswerStatusUseCase(
    private val quizRepository: QuizRepository
) : FlowUseCase<GetWrongAnswerStatusUseCase.Params, DataResourceResult<WrongAnswerNoteStatus>>() {
    override fun execute(params: Params?): Flow<DataResourceResult<WrongAnswerNoteStatus>> {
        if (params == null) {
            return flowOf(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = "오답노트 상태 요청 파라미터가 없습니다."
                    )
                )
            )
        }

        return flow<DataResourceResult<WrongAnswerNoteStatus>> {
            val stage = params.stage

            val stageResult = quizRepository.getCheckStage(stage)
            val wrongNumberResult = quizRepository.getWrongAnswerQuizNumbers(stage)
                .first { it is DataResourceResult.Success || it is DataResourceResult.Failure }

            val checkStage = (stageResult as? DataResourceResult.Success)?.data
                ?: throw PayKidsException.ToastException(-1, "스테이지 클리어 여부를 불러올 수 없습니다.")

            val wrongNumbers = (wrongNumberResult as? DataResourceResult.Success)?.data
                ?: throw PayKidsException.ToastException(-1, "오답 번호를 불러올 수 없습니다.")

            val status = when {
                checkStage.message == "복습" && wrongNumbers.isEmpty() ->
                    WrongAnswerNoteStatus.AllCorrect

                checkStage.message == "First" && wrongNumbers.isEmpty() ->
                    WrongAnswerNoteStatus.NoAttempt

                else -> WrongAnswerNoteStatus.HasWrongAnswerNote(wrongNumbers)
            }

            emit(DataResourceResult.Success(status))
        }.catch {
            emit(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = "오답노트 상태 확인 실패"
                    )
                )
            )
        }
    }

    data class Params(
        val stage: Int
    )
}
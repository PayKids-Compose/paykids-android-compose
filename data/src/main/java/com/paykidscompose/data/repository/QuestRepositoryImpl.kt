package com.paykidscompose.data.repository

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.quest.QuestModel
import com.paykidscompose.common.repositories.QuestRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.quest.QuestMapper
import com.paykidscompose.data.network.service.QuestApiService

class QuestRepositoryImpl(private val questApiService: QuestApiService) : QuestRepository {
    override suspend fun getQuests(): DataResourceResult<List<QuestModel>> {
        return runCatching {
            questApiService.getQuests()
        }.fold(
            onSuccess = {
                DataResourceResult.Success(
                    it.data.map { questDTO ->
                        QuestMapper.mapToModel(questDTO)
                    }
                )
            },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: ""
                    )
                )
            }
        )
    }
}
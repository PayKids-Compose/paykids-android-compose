package com.paykidscompose.common.usecase.quest

import com.paykidscompose.common.model.quest.QuestModel
import com.paykidscompose.common.repository.QuestRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetQuestsUseCase @Inject constructor(
    private val questRepository: QuestRepository
) : SuspendUseCase<Unit, DataResourceResult<List<QuestModel>>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<List<QuestModel>> {
        return questRepository.getQuests()
    }
}
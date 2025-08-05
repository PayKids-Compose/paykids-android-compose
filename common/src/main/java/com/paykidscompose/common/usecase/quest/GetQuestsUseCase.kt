package com.paykidscompose.common.usecase.quest

import com.paykidscompose.common.model.quest.QuestModel
import com.paykidscompose.common.repositories.QuestRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetQuestsUseCase(
    private val questRepository: QuestRepository
) : SuspendUseCase<Unit, DataResourceResult<List<QuestModel>>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<List<QuestModel>> {
        return questRepository.getQuests()
    }
}
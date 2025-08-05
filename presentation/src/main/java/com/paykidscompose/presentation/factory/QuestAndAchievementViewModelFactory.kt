package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.achievement.GetAchievementsUseCase
import com.paykidscompose.common.usecase.quest.GetQuestsUseCase
import com.paykidscompose.presentation.screens.quest.QuestAndAchievementViewModel

class QuestAndAchievementViewModelFactory(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getQuestsUseCase: GetQuestsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestAndAchievementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestAndAchievementViewModel(getAchievementsUseCase, getQuestsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
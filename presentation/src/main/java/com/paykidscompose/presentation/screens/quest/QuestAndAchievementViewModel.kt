package com.paykidscompose.presentation.screens.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.achievement.GetAchievementsUseCase
import com.paykidscompose.common.usecase.quest.GetQuestsUseCase
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.achievement.AchievementUIModelMapper
import com.paykidscompose.presentation.mapper.quest.QuestUIModelMapper
import com.paykidscompose.presentation.model.achievement.AchievementUIModel
import com.paykidscompose.presentation.model.quest.QuestUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestAndAchievementViewModel(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getQuestsUseCase: GetQuestsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestAndAchievementUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadQuests()
            loadAchievements()
            _uiState.update {
                it.copy(isLoading = false, error = null)
            }
        }
    }

    suspend fun loadQuests() {
        when (val result = getQuestsUseCase()) {
            is DataResourceResult.Success -> {
                _uiState.update {
                    it.copy(
                        quests = result.data.map { questModel ->
                            QuestUIModelMapper.mapToLayerModel(questModel)
                        }
                    )
                }
            }

            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(error = result.exception)
                }
            }

            DataResourceResult.DummyConstructor -> {}
            DataResourceResult.Loading -> {}
        }
    }

    suspend fun loadAchievements() {
        when (val result = getAchievementsUseCase()) {
            is DataResourceResult.Success -> {
                _uiState.update {
                    it.copy(
                        achievements = result.data.map { achievementModel ->
                            AchievementUIModelMapper.mapToLayerModel(achievementModel)
                        }
                    )
                }
            }

            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(error = result.exception)
                }
            }

            DataResourceResult.DummyConstructor -> {}
            DataResourceResult.Loading -> {}
        }
    }

    fun removeQuest(name: String) {
        _uiState.update { current ->
            current.copy(
                quests = current.quests.filterNot { it.name == name }
            )
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}

data class QuestAndAchievementUIState(
    override val isLoading: Boolean = true,
    override val error: PayKidsException? = null,
    val quests: List<QuestUIModel> = emptyList(),
    val achievements: List<AchievementUIModel> = emptyList()
) : UIState()
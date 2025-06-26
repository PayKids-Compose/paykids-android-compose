package com.paykidscompose.presentation.navigation.route

import com.paykidscompose.presentation.model.QuizClearType
import kotlinx.serialization.Serializable


@Serializable
sealed interface QuizNavigationRoute {
    @Serializable
    data class QuizRoute(var stageNumber: Int = 1): QuizNavigationRoute

    @Serializable
    data class QuizEntryRoute(var stageNumber: Int = 1): QuizNavigationRoute

    @Serializable
    data class QuizClearRoute(var clear: QuizClearType? = null): QuizNavigationRoute

}

fun QuizNavigationRoute.toRoute(): String = when(this) {
    is QuizNavigationRoute.QuizRoute -> "quiz/${stageNumber}"
    is QuizNavigationRoute.QuizEntryRoute -> "quiz_entry/${stageNumber}"
    is QuizNavigationRoute.QuizClearRoute -> "quiz_clear/${clear?.name}"
}
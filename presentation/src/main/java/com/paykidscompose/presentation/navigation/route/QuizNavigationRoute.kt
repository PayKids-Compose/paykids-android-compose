package com.paykidscompose.presentation.navigation.route

import com.paykidscompose.presentation.model.type.QuizClearType
import kotlinx.serialization.Serializable


@Serializable
sealed interface QuizNavigationRoute: NavigationRoute {
    @Serializable
    data class QuizRoute(val stageNumber: Int): QuizNavigationRoute

    @Serializable
    data class QuizEntryRoute(val stageNumber: Int): QuizNavigationRoute

    @Serializable
    data class QuizClearRoute(val clear: QuizClearType): QuizNavigationRoute

    @Serializable
    data object StudyRoute: QuizNavigationRoute
}
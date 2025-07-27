package com.paykidscompose.app.di

import android.content.Context
import com.paykidscompose.common.di.ApplicationContainer
import com.paykidscompose.common.usecase.authentication.LoginUseCase
import com.paykidscompose.common.usecase.authentication.LogoutUseCase
import com.paykidscompose.common.usecase.quiz.GetStageCountUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase
import com.paykidscompose.common.usecase.quiz.GetStageToGoUseCase
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.ReplaceNicknameUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.authentication.KakaoLoginService
import com.paykidscompose.data.repositories.AuthRepositoryImpl
import com.paykidscompose.data.repositories.QuizRepositoryImpl
import com.paykidscompose.data.repositories.UserRepositoryImpl

class ApplicationContainerImpl(
    context: Context
) : ApplicationContainer {

    private val authRepository = AuthRepositoryImpl(
        NetworkModule.provideAuthApiService(),
        KakaoLoginService(context)
    )

    private val userRepository = UserRepositoryImpl(
        NetworkModule.provideUserApiService()
    )

    private val quizRepository = QuizRepositoryImpl(
        NetworkModule.provideQuizApiService()
    )

    override val loginUseCase: LoginUseCase = LoginUseCase(authRepository)
    override val saveNicknameUseCase: SaveNicknameUseCase = SaveNicknameUseCase(userRepository)
    override val getUserUseCase: GetUserUseCase = GetUserUseCase(userRepository)
    override val logoutUseCase: LogoutUseCase = LogoutUseCase(authRepository)
    override val deleteUserUseCase: DeleteUserUseCase = DeleteUserUseCase(userRepository)
    override val replaceNicknameUseCase: ReplaceNicknameUseCase = ReplaceNicknameUseCase(userRepository)

    override val getStageCountUseCase: GetStageCountUseCase = GetStageCountUseCase(quizRepository)
    override val getStageToGoUseCase: GetStageToGoUseCase = GetStageToGoUseCase(quizRepository)
    override val getStageNameUseCase: GetStageNameUseCase = GetStageNameUseCase(quizRepository)
}
package com.paykidscompose.common.di

import com.paykidscompose.common.usecase.authentication.LoginUseCase
import com.paykidscompose.common.usecase.authentication.LogoutUseCase
import com.paykidscompose.common.usecase.quiz.GetStageCountUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase
import com.paykidscompose.common.usecase.quiz.GetStageToGoUseCase
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.ReplaceNicknameUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase

interface ApplicationContainer {
    val loginUseCase: LoginUseCase
    val saveNicknameUseCase: SaveNicknameUseCase
    val replaceNicknameUseCase: ReplaceNicknameUseCase
    val getUserUseCase: GetUserUseCase
    val logoutUseCase: LogoutUseCase
    val deleteUserUseCase: DeleteUserUseCase
    val getStageCountUseCase: GetStageCountUseCase
    val getStageToGoUseCase: GetStageToGoUseCase
    val getStageNameUseCase: GetStageNameUseCase
}
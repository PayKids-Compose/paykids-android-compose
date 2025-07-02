package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer


@Serializable
sealed interface NavigationRoute

/*
    toRoute가 다형성이 지원 안 되는 거 같아서 현재 루트를 문자열로 받기 때문에
    직렬화된 루트도 문자열로 변환해서 비교하기 위한 확장 함수 입니다.
 */
@OptIn(InternalSerializationApi::class)
fun NavigationRoute.serialName(): String =
    // 직렬화기를 사용해서 직렬화 구조의 이름을 가져옴.
    this::class.serializer().descriptor.serialName
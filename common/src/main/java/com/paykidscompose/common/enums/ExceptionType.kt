package com.paykidscompose.common.enums

/**
 * 화면의 에러 즉 로드할 때 에러가 발생하면 스낵바랑 토스트보다 다이얼로그를 띄우기
 * 그 외에 저장 변경 삭제 등은 다이얼로그가 아닌 스낵바와 토스트를 사용하기.
 */

sealed interface ExceptionType {
    object Snack: ExceptionType

    object Toast: ExceptionType

    object Dialog: ExceptionType
}

enum class ActionType {
    RETRY_API
}
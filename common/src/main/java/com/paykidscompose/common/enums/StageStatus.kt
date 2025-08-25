package com.paykidscompose.common.enums

/** 서버에서 받는 스테이지 상태 문자열 */
enum class StageStatus(val message: String) {
    ALL_CLEAR("All Clear"),
    FIRST("First"),
    WRONG_ANSWER_NOTE("오답 노트"),
    REVIEW("복습")
}
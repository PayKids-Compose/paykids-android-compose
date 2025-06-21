package com.paykidscompose.presentation.dummy

private val dummyStageTitles = listOf(
    "저금이 뭐예요?",
    "은행은 어떤 곳인가요?",
    "카드를 어떻게 써요?",
    "계좌 만들기",
    "용돈을 관리해요",
    "목표 세우기",
    "우선순위 정하기",
    "소득과 지출",
    "세금이란?",
    "기부해요",
    "예산 짜기",
    "현명한 소비",
    "비상금이 필요해요",
    "통장 구분하기",
    "환율과 외화",
    "주식이 뭐예요?",
    "보험 이해하기",
    "사기 조심하기",
    "적금 vs 예금",
    "이자 계산하기",
    "대출이란?",
    "신용등급",
    "할부와 일시불",
    "온라인 결제",
    "계약서 이해하기",
    "재정 목표 설정"
)

fun getStageTitle(index: Int): String {
    return dummyStageTitles.getOrNull(index) ?: "알 수 없는 스테이지"
}
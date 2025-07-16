package com.paykidscompose.presentation.dummy

data class Quest(
    val title: String,
    val progress: Int,
    val maxProgress: Int
)

val dummyQuests = listOf<Quest>(
    Quest("오답노트 풀기 2회", 1, 2),
    Quest("단어 10개 외우기", 0, 1),
    Quest("퀴즈 3문제 풀기", 3, 3),
    Quest("오늘의 퀘스트 완료하기", 0, 1),
    Quest("용돈 기록하기", 1, 1),
    Quest("누적 퀴즈 5개 풀기", 2, 5),
    Quest("오답노트 다시보기", 1, 2),
    Quest("나의 소비 돌아보기", 0, 1),
    Quest("미션 공유하기", 0, 1),
    Quest("단어 다시 외우기", 1, 1)
)
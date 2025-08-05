package com.paykidscompose.presentation.dummy

import com.paykidscompose.presentation.model.quest.QuestUIModel

val dummyQuests = listOf<QuestUIModel>(
    QuestUIModel("오답노트 풀기 2회", false, 1, 2),
    QuestUIModel("단어 10개 외우기", false, 0, 1),
    QuestUIModel("퀴즈 3문제 풀기", true, 3, 3),
    QuestUIModel("오늘의 퀘스트 완료하기", false, 0, 1),
    QuestUIModel("용돈 기록하기", true, 1, 1),
    QuestUIModel("누적 퀴즈 5개 풀기", false, 2, 5),
    QuestUIModel("오답노트 다시보기", false, 1, 2),
    QuestUIModel("나의 소비 돌아보기", false, 0, 1),
    QuestUIModel("미션 공유하기", false, 0, 1),
    QuestUIModel("단어 다시 외우기", true, 1, 1)
)
package com.paykidscompose.presentation.dummy

data class Achievement(
    val imageUrl: String,
    val title: String,
    val description: String
)

val dummyAchievements = listOf<Achievement>(
    Achievement("https://picsum.photos/200", "퀘스트 정복자", "모든 퀘스트를 완료했어요!"),
    Achievement("https://picsum.photos/201", "완벽주의자", "모든 답을 정확히 맞히다니,\n놀라워요!"),
    Achievement("https://picsum.photos/202", "첫 걸음", "첫 퀘스트를 완료했어요!"),
    Achievement("https://picsum.photos/203", "도전 정신", "새로운 퀘스트에 도전했어요!"),
    Achievement("https://picsum.photos/204", "기록자", "오답노트를 작성했어요!"),
    Achievement("https://picsum.photos/200", "퀘스트 정복자", "모든 퀘스트를 완료했어요!"),
    Achievement("https://picsum.photos/201", "근성왕", "꾸준히 문제를 풀었어요!"),
    Achievement("https://picsum.photos/202", "첫 걸음", "첫 퀘스트를 완료했어요!"),
    Achievement("https://picsum.photos/203", "도전 정신", "새로운 퀘스트에 도전했어요!"),
    Achievement("https://picsum.photos/204", "기록자", "오답노트를 작성했어요!")
)
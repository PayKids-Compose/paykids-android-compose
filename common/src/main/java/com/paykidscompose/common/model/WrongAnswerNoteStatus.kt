package com.paykidscompose.common.model

sealed class WrongAnswerNoteStatus {
    object NoAttempt : WrongAnswerNoteStatus()
    object AllCorrect : WrongAnswerNoteStatus()
    data class HasWrongAnswerNote(val quizNumbers: List<Int>) : WrongAnswerNoteStatus()
}
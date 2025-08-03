package com.paykidscompose.common.model.quiz

sealed class WrongAnswerNoteStatus {
    object NoAttempt : WrongAnswerNoteStatus()
    object AllCorrect : WrongAnswerNoteStatus()
    data class HasWrongAnswerNote(val quizNumbers: List<Int>) : WrongAnswerNoteStatus()
}
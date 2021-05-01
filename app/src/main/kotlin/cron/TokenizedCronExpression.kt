package cron

data class TokenizedCronExpression(
    val minutes: String,
    val hour: String,
    val dayOfMonth: String,
    val month: String,
    val dayOfWeek: String,
    val command: String
)

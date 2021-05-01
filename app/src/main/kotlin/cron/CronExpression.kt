package cron

data class CronExpression(
    val minutes: Set<Int>,
    val hour: Set<Int>,
    val dayOfMonth: Set<Int>,
    val month: Set<Int>,
    val dayOfWeek: Set<Int>,
    val command: String,
)

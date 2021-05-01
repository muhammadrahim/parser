package cron

object OutputGenerator {
    fun output(cronExpression: CronExpression): String = with(cronExpression) {
        """minute        ${convertSetToStringAndTrim(minutes)}
            |hour          ${convertSetToStringAndTrim(hour)}
            |day of month  ${convertSetToStringAndTrim(dayOfMonth)}
            |month         ${convertSetToStringAndTrim(month)}
            |day of week   ${convertSetToStringAndTrim(dayOfWeek)}
            |command       $command""".trimMargin()
    }

    private fun convertSetToStringAndTrim(set: Set<Int>): String {
        val stringBuilder = StringBuilder()
        set.forEach { stringBuilder.append("$it ") }
        return stringBuilder.toString().trim()
    }
}

package cron

object OutputGenerator {
    fun output(cronExpression: CronExpression): String {
        var counter: Int = 0
        val results = mutableSetOf<List<Int>>()
        while (counter < 5) {
            val list = cronExpression.minutes.
            results.add(list)
        }
        return with(cronExpression) {
            """minute        ${convertSetToStringAndTrim(minutes)}
                |hour          ${convertSetToStringAndTrim(hour)}
                |day of month  ${convertSetToStringAndTrim(dayOfMonth)}
                |month         ${convertSetToStringAndTrim(month)}
                |day of week   ${convertSetToStringAndTrim(dayOfWeek)}
                |command       $command
                |first 5 runs $results""".trimMargin()
        }
    }

    private fun convertSetToStringAndTrim(set: Set<Int>): String {
        val stringBuilder = StringBuilder()
        set.forEach { stringBuilder.append("$it ") }
        return stringBuilder.toString().trim()
    }
}

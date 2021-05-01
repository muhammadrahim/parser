package cron

import cron.Interval.DAY_OF_MONTH
import cron.Interval.DAY_OF_WEEK
import cron.Interval.HOUR
import cron.Interval.MINUTE
import cron.Interval.MONTH

object CronExpressionBuilder {

    fun build(tokenizedCronExpression: TokenizedCronExpression): CronExpression =
        with(tokenizedCronExpression) {
            CronExpression(
                minutes = findIntervals(minutes, MINUTE),
                hour = findIntervals(hour, HOUR),
                dayOfMonth = findIntervals(dayOfMonth, DAY_OF_MONTH),
                month = findIntervals(month, MONTH),
                dayOfWeek = findIntervals(dayOfWeek, DAY_OF_WEEK),
                command = command
            )
        }

    private fun findIntervals(period: String, interval: Interval): Set<Int> = when {
        period == "*" -> interval.range.toSet()
        period.length == 1 -> setOf(Integer.parseInt(period))
        period.contains("-") -> generateFromRange(period)
        period.contains(",") -> generateFromSet(period)
        else -> checkForStepSizes(interval.range.toSet(), period)
    }

    private fun checkForStepSizes(set: Set<Int>, period: String): Set<Int> {
        return if (period.contains("/")) {
            val stepSize = Integer.parseInt(period.substring(period.indexOf("/") + 1))
            set.filter { it % stepSize == 0 }.toSet()
        } else {
            set
        }
    }

    private fun generateFromSet(period: String): Set<Int> {
        val bounds = period.split(",")
        return checkForStepSizes(bounds.map { Integer.parseInt(it) }.toSet(), period)
    }

    private fun generateFromRange(period: String): Set<Int> {
        val bounds = period.split("-")
        val start = Integer.parseInt(bounds[0])
        val endInclusive = Integer.parseInt(bounds[1])
        return checkForStepSizes(IntRange(start, endInclusive).toSet(), period)
    }
}

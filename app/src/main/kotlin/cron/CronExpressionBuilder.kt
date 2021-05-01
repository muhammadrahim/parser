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
        period == "?" && interval in setOf(DAY_OF_MONTH, DAY_OF_WEEK) -> interval.range.toSet()
        period.length <= 2 -> setOf(Integer.parseInt(period))
        period.contains("-") -> generateFromRange(period, interval)
        period.contains(",") -> generateFromSet(period, interval)
        period.contains("/") -> checkForStepSizes(interval.range.toSet(), period, interval) // TODO: potentially need to mod this
        else -> throw IllegalStateException("cannot parse. period=$period interval=$interval")
    }

    private fun checkForStepSizes(set: Set<Int>, period: String, interval: Interval): Set<Int> {
        return if (period.contains("/")) {
            val stepSize = Integer.parseInt(period.substring(period.indexOf("/") + 1))
            if (interval == DAY_OF_MONTH && period.contains("1/")) {
                set.asSequence().filter { it % stepSize == 0 }.map { it + 1 }.plus(1).toSet()
            } else {
                set.asSequence().filter { it % stepSize == 0 }.toSet()
            }
        } else {
            set
        }
    }

    private fun generateFromSet(period: String, interval: Interval): Set<Int> {
        val bounds = period.split(",")
        return checkForStepSizes(bounds.map { Integer.parseInt(it) }.toSet(), period, interval)
    }

    private fun generateFromRange(period: String, interval: Interval): Set<Int> {
        val bounds = period.split("-")
        val start = Integer.parseInt(bounds[0])
        val endInclusive = Integer.parseInt(bounds[1])
        return checkForStepSizes(IntRange(start, endInclusive).toSet(), period, interval)
    }
}

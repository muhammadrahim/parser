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
        period.contains("/") -> checkForStepSizes(
            interval.range.toSet(),
            period,
            interval
        ) // TODO: potentially need to mod this
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
        val start: Int
        val endInclusive: Int
        if (period.length <= 6) {
            start = Integer.parseInt(bounds[0])
            endInclusive = Integer.parseInt(bounds[1])
        } else {
            start = DayOfWeek.valueOf(bounds[0]).value
            endInclusive = DayOfWeek.valueOf(bounds[1]).value
        }
        // start = 5, end = 3. 1-12. ---- 5,6,7,8,9,10,11,12.  1,2,3.
        val range = if (start > endInclusive) {
            IntRange(interval.range.first, endInclusive).plus(IntRange(start, interval.range.last)).toSet()
        } else {
            IntRange(start, endInclusive).toSet()
        }
        val test = OnClick { Integer.parseInt(it) }
        test.accept("100")
        return checkForStepSizes(range, period, interval)
    }
}

enum class DayOfWeek(val value: Int) {
    MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6), SUN(7)
}

fun interface OnClick {
    fun accept(string: String): Int
}

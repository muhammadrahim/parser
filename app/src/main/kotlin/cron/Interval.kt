package cron

enum class Interval(val range: IntRange) {
    MINUTE(IntRange(0, 59)),
    HOUR(IntRange(0, 23)),
    DAY_OF_MONTH(IntRange(1, 31)),
    MONTH(IntRange(1, 12)),
    DAY_OF_WEEK(IntRange(1, 7))
}

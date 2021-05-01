package cron

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class OutputGeneratorTest {
    @Test
    fun `should print cron expression correctly`() {
        val cronExpression = CronExpression(
            setOf(0, 15, 30, 45),
            setOf(0),
            setOf(1, 15),
            setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            setOf(1, 2, 3, 4, 5),
            "command"
        )
        val expected = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "day of week   1 2 3 4 5\n" +
            "command       command"

        val actual = OutputGenerator.output(cronExpression)

        assertEquals(expected, actual)
    }
}

package cron

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CronParserComponentTest {

    @Test
    fun `should successfully parse a cron expression and output it`() {
        val input = arrayOf("*/15", "0", "1,15", "*", "1-5", "echo", "\"random", "text\"")
        val expectedOutput = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "day of week   1 2 3 4 5\n" +
            "command       echo \"random text\""

        val tokenizedCronExpression = Tokenizer.tokenize(input)
        val cronExpression = CronExpressionBuilder.build(tokenizedCronExpression)
        val actualOutput = OutputGenerator.output(cronExpression)

        assertEquals(expectedOutput, actualOutput)
    }
    @Test
    fun `should successfully parse a cron expression and output it`() {
        val input = arrayOf("*/15", "0", "1,15", "*", "1-5", "echo", "\"random", "text\"")
        val expectedOutput = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "day of week   1 2 3 4 5\n" +
            "command       echo \"random text\"" +
            "first 5 runs  [0,0,1,1,1], [15,0,1,1,1], "

        val tokenizedCronExpression = Tokenizer.tokenize(input)
        val cronExpression = CronExpressionBuilder.build(tokenizedCronExpression)
        val actualOutput = OutputGenerator.output(cronExpression)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `should successfully parse a cron expression and output it for provided example`() {
        val input = arrayOf("*/15", "100", "1,15", "*", "1-5", "/usr/bin/find")
        val expectedOutput = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "day of week   1 2 3 4 5\n" +
            "command       echo \"random text\""

        val tokenizedCronExpression = Tokenizer.tokenize(input)
        val cronExpression = CronExpressionBuilder.build(tokenizedCronExpression)
        val actualOutput = OutputGenerator.output(cronExpression)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `should successfully parse a cron expression with named days of week mon-fri`() {
        val input = arrayOf("*/15", "0", "1,15", "*", "MON-FRI", "/usr/bin/find")
        val expectedOutput = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "day of week   1 2 3 4 5\n" +
            "command       /usr/bin/find"

        val tokenizedCronExpression = Tokenizer.tokenize(input)
        val cronExpression = CronExpressionBuilder.build(tokenizedCronExpression)
        val actualOutput = OutputGenerator.output(cronExpression)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `should successfully parse a cron expression with named days of week TUE-SUN`() {
        val input = arrayOf("*/15", "0", "1,15", "*", "TUE-SUN", "/usr/bin/find")
        val expectedOutput = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "day of week   2 3 4 5 6 7\n" +
            "command       /usr/bin/find"

        val tokenizedCronExpression = Tokenizer.tokenize(input)
        val cronExpression = CronExpressionBuilder.build(tokenizedCronExpression)
        val actualOutput = OutputGenerator.output(cronExpression)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `should successfully parse a cron expression with wrap around for range`() {
        val input = arrayOf("*/15", "0", "1,15", "12-1", "SUN-TUE", "/usr/bin/find")
        val expectedOutput = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 12\n" +
            "day of week   2 3 4 5 6 7\n" +
            "command       /usr/bin/find"

        val tokenizedCronExpression = Tokenizer.tokenize(input)
        val cronExpression = CronExpressionBuilder.build(tokenizedCronExpression)
        val actualOutput = OutputGenerator.output(cronExpression)

        assertEquals(expectedOutput, actualOutput)
    }
}

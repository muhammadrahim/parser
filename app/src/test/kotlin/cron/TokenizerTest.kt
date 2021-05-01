package cron

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TokenizerTest {
    @Test
    fun `should tokenize input arguments into a cron expression`() {
        val expected = TokenizedCronExpression("*/15", "0", "1,15", "*", "1-5", """echo "random text"""")

        val actual = Tokenizer.tokenize(arrayOf("*/15", "0", "1,15", "*", "1-5", "echo", "\"random", "text\""))

        assertEquals(expected, actual)
    }
}

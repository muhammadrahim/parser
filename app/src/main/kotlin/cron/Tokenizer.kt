package cron

object Tokenizer {
    fun tokenize(input: Array<String>): TokenizedCronExpression {
        val stringBuilder = StringBuilder()
        input.forEach { stringBuilder.append("$it ") }
        return tokenize(stringBuilder.toString().trim())
    }

    private fun tokenize(input: String): TokenizedCronExpression {
        val split: List<String> = input.split(" ")
        val command = input.subSequence(input.indexOf(split[5]), input.length).toString()
        return TokenizedCronExpression(
            minutes = split[0],
            hour = split[1],
            dayOfMonth = split[2],
            month = split[3],
            dayOfWeek = split[4],
            command = command
        )
    }
}

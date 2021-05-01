package cron

fun main(args: Array<String>) {
    val tokenizedCronExpression = Tokenizer.tokenize(args)
    val cronExpression = CronExpressionBuilder.build(tokenizedCronExpression)
    val output = OutputGenerator.output(cronExpression)
    print(output)
}

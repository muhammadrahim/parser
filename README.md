# Cron Expression Parser

Parses a cron string and expands each field to show the times at which it will run. 

This only considers the standard cron format with five time fields (minute, hour, day of month, month, and day of week) plus a command, and it does not handle the special time strings such as "@yearly".

The output is formatted as a table with the field name taking the first 14 columns and the times as a space-separated list following it.

### Example usage:
If given input:  
`*/15 0 1,15 * 1-5 /usr/bin/find`

Then run:  
`./gradlew run --args="*/15 0 1,15 * 1-5 /usr/bin/find"`

### Missing edge cases

There is also unhandled edge cases, such as:
* when the input contains `1/15` for any given parameter except `day_of_month`.  
* Data that uses letters e.g. `MON-FRI` or `JAN-DEC`.
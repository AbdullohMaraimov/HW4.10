package hw2;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return "\nNotification: " + record.getMessage() + "\n";
    }
}

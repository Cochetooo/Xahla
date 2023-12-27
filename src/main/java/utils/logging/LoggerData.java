package utils.logging;

import java.io.PrintStream;
import java.util.List;

public record LoggerData(
    LoggingLevel loggingLevel,
    PrintStream printer,
    boolean logFile,
    boolean prefix,
    String dateFormat
) {



}

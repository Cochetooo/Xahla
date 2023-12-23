package utils.logging;

import java.io.PrintStream;
import java.util.List;

public record LoggerData(
    LoggingLevel loggingLevel,
    PrintStream printer,
    boolean internalLogging,
    boolean logFile,
    boolean prefix,
    List<String> excepts
) {
}

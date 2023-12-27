package utils.logging;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.StringTemplate.STR;

/** Logger
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexiscochet.pro@gmail.com>, December 2023
 */
public final class Logger {

    /*
     * Properties
     */

    private LoggingLevel loggingLevel;
    private PrintStream printer;
    private boolean logFile;
    private boolean prefix;
    private String dateFormat;
    private List<String> excepts;

    /*
     * Constructor
     */

    /** Default constructor */
    public Logger() {
        this(new LoggerData(
            /* @var loggingLevel    */ LoggingLevel.CONFIG,
            /* @var printer         */ System.out,
            /* @var logFile         */ false,
            /* @var prefix          */ true,
            /* @var dateFormat      */ "dd/MM/yyyy HH:mm:ss.SSS"
        ));
    }

    public Logger(LoggerData pLoggerData) {
        this.loggingLevel   = pLoggerData.loggingLevel();
        this.printer        = pLoggerData.printer();
        this.logFile        = pLoggerData.logFile();
        this.prefix         = pLoggerData.prefix();
        this.dateFormat     = pLoggerData.dateFormat();

        this.excepts        = new ArrayList<>();
    }

    /*
     * Methods
     */

    /** Error logging */

    public void error(Object message) {
        this.error(
            /* @var message     */ message,
            /* @var classSource */ null
        );
    }

    public void error(Object message, String classSource) {
        var tmp = this.printer;
        this.printer = System.err;
        this.log(message, LoggingLevel.SEVERE, classSource);
        this.printer = tmp;
    }

    /** Logging */

    public void log(Object message) {
        this.log(
           /* @var message      */ message,
           /* @var loggingLevel */ LoggingLevel.INFO
        );
    }

    public void log(Object message, LoggingLevel loggingLevel) {
        this.log(
            /* @var message      */ message,
            /* @var loggingLevel */ LoggingLevel.INFO,
            /* @var classSource  */ null
        );
    }

    public void log(Object message, @NotNull LoggingLevel loggingLevel, String classSource) {
        if (0 < loggingLevel.compareTo(this.loggingLevel)) {
            return;
        }

        if (true == this.excepts.contains(classSource)) {
            return;
        }

        if (true == this.prefix) {
            this.printer.print(STR."[\{this.loggingLevel}] ");
        }

        if (null != classSource) {
            this.printer.print(STR."<\{classSource}> ");
        }

        this.printer.println(message);
    }

    /** Throw exception */

    public void throwException(Throwable exception) {
        this.throwException(
            /* @var exception       */ exception,
            /* @var message         */ null
        );
    }

    public void throwException(Throwable exception, Object message) {
        this.throwException(
            /* @var exception       */ exception,
            /* @var message         */ message,
            /* @var classSource     */ null
        );
    }

    public void throwException(Throwable exception, Object message, String classSource) {
        this.throwException(
            /* @var exception       */ exception,
            /* @var message         */ message,
            /* @var classSource     */ classSource,
            /* @var statusCode      */ -1
        );
    }

    public void throwException(@NotNull Throwable exception, Object message, String classSource,
                               int statusCode) {
        this.error(STR."""
                ###### AN ERROR HAS OCCURED ######
                # Exception type: \{exception.getClass().getSimpleName()}
                # Exception message: \{exception.getLocalizedMessage()}
                # Exit Status Code: \{statusCode}
                # Additional message: \{null == message ? "None" : message}
                # From: \{null == classSource ? "Unknown class" : classSource}
                # Occured during: \{new SimpleDateFormat(this.dateFormat).format(new Date())}
                """.stripIndent());

        if (true == this.logFile) {
            /**
             * @TODO
             */
        }

        System.exit(statusCode);
    }

    /** Warning logging */

    public void warning(Object message) {
        this.warning(message, null);
    }

    public void warning(Object message, String classSource) {
        this.log(message, LoggingLevel.WARNING, classSource);
    }

    /*
     * Getters
     */

    public String getStackTrace(Throwable exception) {
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter, true);
        exception.printStackTrace(printWriter);

        return stringWriter.getBuffer().toString();
    }

    /*
     * Static Methods
     */

    public static void setGlobalExceptionHandler(Logger loggerInstance) {
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            loggerInstance.error(exception.getLocalizedMessage(), thread.getName());
        });
    }

}

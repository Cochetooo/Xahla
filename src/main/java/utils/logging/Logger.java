package utils.logging;

import java.io.PrintStream;

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

    private static LoggingLevel loggingLevel = LoggingLevel.CONFIG;
    private static PrintStream printer = System.out;
    private static boolean internalLogging = true;
    private static boolean logFile = false;
    private static boolean prefix = true;

    /*
     * Constructor
     */

    private Logger() {}



}

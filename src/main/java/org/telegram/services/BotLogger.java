package org.telegram.services;

import org.telegram.BuildVars;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ruben Bermudez
 * @version 2.0
 * @brief Logger to file
 * @date 21/01/15
 */
public class BotLogger {
    private static final Object lockToWrite = new Object();
    private static volatile PrintWriter logginFile;
    private static volatile String currentFileName;
    private static final Logger logger = Logger.getLogger("Tsupport Bot");
    private static volatile LocalDateTime lastFileDate;
    private static LoggerThread loggerThread = new LoggerThread();
    private static final ConcurrentLinkedQueue<String> logsToFile = new ConcurrentLinkedQueue<>();

    static {
        logger.setLevel(Level.ALL);
        logger.addHandler(new ConsoleHandler());
        loggerThread.start();
        lastFileDate = LocalDateTime.now();
        if ((currentFileName == null) || (currentFileName.compareTo("") == 0)) {
            currentFileName = dateFormatterForFileName(lastFileDate) + ".log";
            try {
                final File file = new File(currentFileName);
                if (file.exists()) {
                    logginFile = new PrintWriter(new BufferedWriter(new FileWriter(currentFileName, true)));
                } else {
                    final boolean created = file.createNewFile();
                    if (created) {
                        logginFile = new PrintWriter(new BufferedWriter(new FileWriter(currentFileName, true)));
                    } else {
                        throw new NullPointerException("File for logging error");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void log(@NotNull Level level, String tag, String msg) {
        logger.log(level, String.format("[%s] %s", tag, msg));
        logToFile(level, tag, msg);
    }


    public static void severe(String tag, String msg) {
        logger.severe(String.format("[%s] %s", tag, msg));
        logToFile(Level.SEVERE, tag, msg);
    }

    public static void warn(String tag, String msg) {
        warning(tag, msg);
    }

    public static void debug(String tag, String msg) {
        fine(tag, msg);
    }

    public static void error(String tag, String msg) {
        severe(tag, msg);
    }

    public static void trace(String tag, String msg) {
        finer(tag, msg);
    }

    public static void warning(String tag, String msg) {
        logger.warning(String.format("[%s] %s", tag, msg));
        logToFile(Level.WARNING, tag, msg);
    }


    public static void info(String tag, String msg) {
        logger.info(String.format("[%s] %s", tag, msg));
        logToFile(Level.INFO, tag, msg);
    }


    public static void config(String tag, String msg) {
        logger.config(String.format("[%s] %s", tag, msg));
        logToFile(Level.CONFIG, tag, msg);
    }


    public static void fine(String tag, String msg) {
        logger.fine(String.format("[%s] %s", tag, msg));
        logToFile(Level.FINE, tag, msg);
    }


    public static void finer(String tag, String msg) {
        logger.finer(String.format("[%s] %s", tag, msg));
        logToFile(Level.FINER, tag, msg);
    }


    public static void finest(String tag, String msg) {
        logger.finest(String.format("[%s] %s", tag, msg));
        logToFile(Level.FINEST, tag, msg);
    }


    public static void log(@NotNull Level level, @NotNull String tag, @NotNull Throwable throwable) {
        logger.log(level, String.format("[%s] Exception", tag), throwable);
        logToFile(level, tag, throwable);
    }

    public static void log(@NotNull Level level, @NotNull String tag, @NotNull String msg, @NotNull Throwable thrown) {
        logger.log(level, msg, thrown);
        logToFile(level, msg, thrown);
    }

    public static void severe(@NotNull String tag, @NotNull Throwable throwable) {
        logToFile(Level.SEVERE, tag, throwable);
    }

    public static void warning(@NotNull String tag, @NotNull Throwable throwable) {
        logToFile(Level.WARNING, tag, throwable);
    }

    public static void info(@NotNull String tag, @NotNull Throwable throwable) {
        logToFile(Level.INFO, tag, throwable);
    }

    public static void config(@NotNull String tag, @NotNull Throwable throwable) {
        logToFile(Level.CONFIG, tag, throwable);
    }

    public static void fine(@NotNull String tag, @NotNull Throwable throwable) {
        logToFile(Level.FINE, tag, throwable);
    }

    public static void finer(@NotNull String tag, @NotNull Throwable throwable) {
        logToFile(Level.FINER, tag, throwable);
    }

    public static void finest(@NotNull String tag, @NotNull Throwable throwable) {
        logToFile(Level.FINEST, tag, throwable);
    }

    public static void warn(@NotNull String tag, Throwable throwable) {
        warning(tag, throwable);
    }

    public static void debug(@NotNull String tag, Throwable throwable) {
        fine(tag, throwable);
    }

    public static void error(@NotNull String tag, Throwable throwable) {
        severe(tag, throwable);
    }

    public static void trace(@NotNull String tag, Throwable throwable) {
        finer(tag, throwable);
    }

    public static void severe(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.SEVERE, tag, msg, throwable);
    }

    public static void warning(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.WARNING, tag, msg, throwable);
    }

    public static void info(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.INFO, tag, msg, throwable);
    }

    public static void config(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.CONFIG, tag, msg, throwable);
    }

    public static void fine(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.FINE, tag, msg, throwable);
    }

    public static void finer(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.FINER, tag, msg, throwable);
    }

    public static void finest(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.FINEST, msg, throwable);
    }

    public static void warn(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.WARNING, tag, msg, throwable);
    }

    public static void debug(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.FINE, tag, msg, throwable);
    }

    public static void error(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.SEVERE, tag, msg, throwable);
    }

    public static void trace(@NotNull String msg, @NotNull String tag, @NotNull Throwable throwable) {
        log(Level.FINER, tag, msg, throwable);
    }

    private static boolean isCurrentDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate().isEqual(lastFileDate.toLocalDate());
    }

    private static String dateFormatterForFileName(@NotNull LocalDateTime dateTime) {
        String dateString = "";
        dateString += dateTime.getDayOfMonth();
        dateString += dateTime.getMonthValue();
        dateString += dateTime.getYear();
        return dateString;
    }

    private static String dateFormatterForLogs(@NotNull LocalDateTime dateTime) {
        String dateString = "[";
        dateString += dateTime.getDayOfMonth() + "_";
        dateString += dateTime.getMonthValue() + "_";
        dateString += dateTime.getYear() + "_";
        dateString += dateTime.getHour() + ":";
        dateString += dateTime.getMinute() + ":";
        dateString += dateTime.getSecond();
        dateString += "] ";
        return dateString;
    }

    private static void updateAndCreateFile(LocalDateTime dateTime) {
        if (!isCurrentDate(dateTime)) {
            lastFileDate = LocalDateTime.now();
            currentFileName = BuildVars.pathToLogs + dateFormatterForFileName(lastFileDate) + ".log";
            try {
                logginFile.flush();
                logginFile.close();
                final File file = new File(currentFileName);
                if (file.exists()) {
                    logginFile = new PrintWriter(new BufferedWriter(new FileWriter(currentFileName, true)));
                } else {
                    final boolean created = file.createNewFile();
                    if (created) {
                        logginFile = new PrintWriter(new BufferedWriter(new FileWriter(currentFileName, true)));
                    } else {
                        throw new NullPointerException("Error updating log file");
                    }
                }
            } catch (IOException ignored) {
            }
        }
    }


    private static void logToFile(@NotNull Level level, @NotNull String tag, @NotNull Throwable throwable) {
        if (isLoggable(level)) {
            synchronized (lockToWrite) {
                final LocalDateTime currentDate = LocalDateTime.now();
                final String dateForLog = dateFormatterForLogs(currentDate);
                updateAndCreateFile(currentDate);
                logThrowableToFile(level, tag, throwable, dateForLog);
            }
        }
    }



    private static void logToFile(@NotNull Level level, @NotNull String tag, @NotNull String msg) {
        if (isLoggable(level)) {
            synchronized (lockToWrite) {
                final LocalDateTime currentDate = LocalDateTime.now();
                updateAndCreateFile(currentDate);
                final String dateForLog = dateFormatterForLogs(currentDate);
                logMsgToFile(level, tag, msg, dateForLog);
            }
        }
    }

    private static void logToFile(Level level, @NotNull String tag, @NotNull String msg, @NotNull Throwable throwable) {
        if (isLoggable(level)) {
            synchronized (lockToWrite) {
                final LocalDateTime currentDate = LocalDateTime.now();
                updateAndCreateFile(currentDate);
                final String dateForLog = dateFormatterForLogs(currentDate);
                logMsgToFile(level, tag, msg, dateForLog);
                logThrowableToFile(level, tag, throwable, dateForLog);
            }
        }
    }

    private static void logMsgToFile(@NotNull Level level, @NotNull String tag, @NotNull String msg, @NotNull String dateForLog) {
        final String logMessage = String.format("%s{%s} %s - %s", dateForLog, level.toString(), tag, msg);
        logsToFile.add(logMessage);
        synchronized (logsToFile) {
            logsToFile.notifyAll();
        }
    }

    private static void logThrowableToFile(@NotNull Level level, @NotNull String tag, @NotNull Throwable throwable, @NotNull String dateForLog) {
        String throwableLog = String.format("%s{%s} %s - %s", dateForLog, level.toString(), tag, throwable.toString());
        for (StackTraceElement element : throwable.getStackTrace()) {
            throwableLog += "\tat " + element + "\n";
        }
        logsToFile.add(throwableLog);
        synchronized (logsToFile) {
            logsToFile.notifyAll();
        }
    }

    private static boolean isLoggable(Level level) {
        return logger.isLoggable(level) && BuildVars.debug;
    }

    private static class LoggerThread extends Thread {

        @Override
        public void run() {
            while(true) {
                final ConcurrentLinkedQueue<String> stringsToLog = new ConcurrentLinkedQueue<>();
                synchronized (logsToFile) {
                    if (logsToFile.isEmpty()) {
                        try {
                            logsToFile.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                        if (logsToFile.isEmpty()) {
                            continue;
                        }
                    }
                    stringsToLog.addAll(logsToFile);
                    logsToFile.clear();
                }

                stringsToLog.stream().forEach(logginFile::println);
                logginFile.flush();
            }
        }
    }
}

package org.telegram.telegrambots.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ruben Bermudez
 * @version 2.0
 * @brief Logger to file
 * @date 21/01/15
 */
public class BotLogger {
    private static final Logger logger = Logger.getLogger("Telegram Bots Api");

    public static void setLevel(Level level) {
        logger.setLevel(level);
    }

    public static Level getLevel() {
        return logger.getLevel();
    }

    public static void registerLogger(Handler handler) {
        logger.addHandler(handler);
    }

    public static void log(Level level, String tag, String msg) {
        logger.log(level, String.format("%s - %s", tag, msg));
    }

    public static void severe(String tag, String msg) {
        logger.severe(String.format("%s - %s", tag, msg));
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
        logger.warning(String.format("%s - %s", tag, msg));
    }

    public static void info(String tag, String msg) {
        logger.info(String.format("%s - %s", tag, msg));
    }

    public static void config(String tag, String msg) {
        logger.config(String.format("%s - %s", tag, msg));
    }

    public static void fine(String tag, String msg) {
        logger.fine(String.format("%s - %s", tag, msg));
    }

    public static void finer(String tag, String msg) {
        logger.finer(String.format("%s - %s", tag, msg));
    }

    public static void finest(String tag, String msg) {
        logger.finest(String.format("%s - %s", tag, msg));
    }

    public static void log(Level level, String tag, Throwable throwable) {
        logger.log(level, tag, throwable);
    }

    public static void log(Level level, String tag, String msg, Throwable thrown) {
        logger.log(level, String.format("%s - %s", tag, msg), thrown);
    }

    public static void severe(String tag, Throwable throwable) {
        logger.log(Level.SEVERE, tag, throwable);
    }

    public static void warning(String tag, Throwable throwable) {
        logger.log(Level.WARNING, tag, throwable);
    }

    public static void info(String tag, Throwable throwable) {
        logger.log(Level.INFO, tag, throwable);
    }

    public static void config(String tag, Throwable throwable) {
        logger.log(Level.CONFIG, tag, throwable);
    }

    public static void fine(String tag, Throwable throwable) {
        logger.log(Level.FINE, tag, throwable);
    }

    public static void finer(String tag, Throwable throwable) {
        logger.log(Level.FINER, tag, throwable);
    }

    public static void finest(String tag, Throwable throwable) {
        logger.log(Level.FINEST, tag, throwable);
    }

    public static void warn(String tag, Throwable throwable) {
        warning(tag, throwable);
    }

    public static void debug(String tag, Throwable throwable) {
        fine(tag, throwable);
    }

    public static void error(String tag, Throwable throwable) {
        severe(tag, throwable);
    }

    public static void trace(String tag, Throwable throwable) {
        finer(tag, throwable);
    }

    public static void severe(String msg, String tag, Throwable throwable) {
        log(Level.SEVERE, tag, msg, throwable);
    }

    public static void warning(String msg, String tag, Throwable throwable) {
        log(Level.WARNING, tag, msg, throwable);
    }

    public static void info(String msg, String tag, Throwable throwable) {
        log(Level.INFO, tag, msg, throwable);
    }

    public static void config(String msg, String tag, Throwable throwable) {
        log(Level.CONFIG, tag, msg, throwable);
    }

    public static void fine(String msg, String tag, Throwable throwable) {
        log(Level.FINE, tag, msg, throwable);
    }

    public static void finer(String msg, String tag, Throwable throwable) {
        log(Level.FINER, tag, msg, throwable);
    }

    public static void finest(String msg, String tag, Throwable throwable) {
        log(Level.FINEST, tag, msg, throwable);
    }

    public static void warn(String msg, String tag, Throwable throwable) {
        log(Level.WARNING, tag, msg, throwable);
    }

    public static void debug(String msg, String tag, Throwable throwable) {
        log(Level.FINE, tag, msg, throwable);
    }

    public static void error(String msg, String tag, Throwable throwable) {
        log(Level.SEVERE, tag, msg, throwable);
    }

    public static void trace(String msg, String tag, Throwable throwable) {
        log(Level.FINER, tag, msg, throwable);
    }
}

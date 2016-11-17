package org.telegram.telegrambots.logging;

import java.time.LocalDateTime;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Formatter for {@link BotsFileHandler}
 * @date 19 of May of 2016
 */
class FileFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        final LocalDateTime currentDate = LocalDateTime.now();
        final String dateForLog = dateFormatterForLogs(currentDate);
        String result;
        if (record.getThrown() == null) {
            result = logMsgToFile(record.getLevel(), record.getMessage(), dateForLog);
        } else {
            result = logThrowableToFile(record.getLevel(), record.getMessage(), record.getThrown(), dateForLog);
        }
        return result;
    }

    private static String dateFormatterForLogs(LocalDateTime dateTime) {
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

    private static String logMsgToFile(Level level, String msg, String dateForLog) {
        return String.format("%s{%s} %s\n", dateForLog, level.toString(), msg);
    }

    private static String logThrowableToFile(Level level, String message, Throwable throwable, String dateForLog) {
        String throwableLog = String.format("%s{%s} %s - %s\n", dateForLog, level.toString(), message, throwable.toString());
        for (StackTraceElement element : throwable.getStackTrace()) {
            throwableLog += "\tat " + element + "\n";
        }
        return throwableLog;
    }
}

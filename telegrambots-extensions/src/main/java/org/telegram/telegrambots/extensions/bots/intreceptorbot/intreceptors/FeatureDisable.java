package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

/**
 * Thrown if anyone try to call {@link Interceptor#getSendMessages(Class)} but saving sent objects did not enabled
 */
public class FeatureDisable extends RuntimeException {
}

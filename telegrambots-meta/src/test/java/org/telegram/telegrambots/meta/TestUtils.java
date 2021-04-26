package org.telegram.telegrambots.meta;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {
    public static String getTextOfSize(int n) {
        char[] text = new char[33];
        Arrays.fill(text, 'A');
        return new String(text);
    }
}

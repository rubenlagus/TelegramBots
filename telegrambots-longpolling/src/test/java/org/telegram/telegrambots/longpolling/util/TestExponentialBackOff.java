/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.telegram.telegrambots.longpolling.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link ExponentialBackOff}.
 *
 * @author Ravi Mistry
 */
public class TestExponentialBackOff {
    @Test
    public void testConstructor() {
        ExponentialBackOff backOffPolicy = new ExponentialBackOff();
        assertEquals(500, backOffPolicy.getInitialIntervalMillis());
        assertEquals(500, backOffPolicy.getCurrentIntervalMillis());
        assertEquals(0.5, backOffPolicy.getRandomizationFactor());
        assertEquals(1.5, backOffPolicy.getMultiplier());
        assertEquals(60000, backOffPolicy.getMaxIntervalMillis());
        assertEquals(900000, backOffPolicy.getMaxElapsedTimeMillis());
    }

    @Test
    public void testBuilder() {
        ExponentialBackOff backOffPolicy = new ExponentialBackOff.Builder().build();
        assertEquals(500, backOffPolicy.getInitialIntervalMillis());
        assertEquals(500, backOffPolicy.getCurrentIntervalMillis());
        assertEquals(0.5, backOffPolicy.getRandomizationFactor());
        assertEquals(1.5, backOffPolicy.getMultiplier());
        assertEquals(60000, backOffPolicy.getMaxIntervalMillis());
        assertEquals(900000, backOffPolicy.getMaxElapsedTimeMillis());

        int testInitialInterval = 1;
        double testRandomizationFactor = 0.1;
        double testMultiplier = 5.0;
        int testMaxInterval = 10;
        int testMaxElapsedTime = 900000;

        backOffPolicy =
                new ExponentialBackOff.Builder()
                        .setInitialIntervalMillis(testInitialInterval)
                        .setRandomizationFactor(testRandomizationFactor)
                        .setMultiplier(testMultiplier)
                        .setMaxIntervalMillis(testMaxInterval)
                        .setMaxElapsedTimeMillis(testMaxElapsedTime)
                        .build();
        assertEquals(testInitialInterval, backOffPolicy.getInitialIntervalMillis());
        assertEquals(testInitialInterval, backOffPolicy.getCurrentIntervalMillis());
        assertEquals(testRandomizationFactor, backOffPolicy.getRandomizationFactor());
        assertEquals(testMultiplier, backOffPolicy.getMultiplier());
        assertEquals(testMaxInterval, backOffPolicy.getMaxIntervalMillis());
        assertEquals(testMaxElapsedTime, backOffPolicy.getMaxElapsedTimeMillis());
    }

    @Test
    public void testBackOff() {
        int testInitialInterval = 500;
        double testRandomizationFactor = 0.1;
        double testMultiplier = 2.0;
        int testMaxInterval = 5000;
        int testMaxElapsedTime = 900000;

        ExponentialBackOff backOffPolicy =
                new ExponentialBackOff.Builder()
                        .setInitialIntervalMillis(testInitialInterval)
                        .setRandomizationFactor(testRandomizationFactor)
                        .setMultiplier(testMultiplier)
                        .setMaxIntervalMillis(testMaxInterval)
                        .setMaxElapsedTimeMillis(testMaxElapsedTime)
                        .build();
        int[] expectedResults = {500, 1000, 2000, 4000, 5000, 5000, 5000, 5000, 5000, 5000};
        for (int expected : expectedResults) {
            assertEquals(expected, backOffPolicy.getCurrentIntervalMillis());
            // Assert that the next back off falls in the expected range.
            int minInterval = (int) (expected - (testRandomizationFactor * expected));
            int maxInterval = (int) (expected + (testRandomizationFactor * expected));
            long actualInterval = backOffPolicy.nextBackOffMillis();
            assertTrue(minInterval <= actualInterval && actualInterval <= maxInterval);
        }
    }

    @Test
    public void testGetRandomizedInterval() {
        // 33% chance of being 1.
        assertEquals(1, ExponentialBackOff.getRandomValueFromInterval(0.5, 0, 2));
        assertEquals(1, ExponentialBackOff.getRandomValueFromInterval(0.5, 0.33, 2));
        // 33% chance of being 2.
        assertEquals(2, ExponentialBackOff.getRandomValueFromInterval(0.5, 0.34, 2));
        assertEquals(2, ExponentialBackOff.getRandomValueFromInterval(0.5, 0.66, 2));
        // 33% chance of being 3.
        assertEquals(3, ExponentialBackOff.getRandomValueFromInterval(0.5, 0.67, 2));
        assertEquals(3, ExponentialBackOff.getRandomValueFromInterval(0.5, 0.99, 2));
    }

    @Test
    public void testBackOffOverflow() {
        int testInitialInterval = Integer.MAX_VALUE / 2;
        double testMultiplier = 2.1;
        int testMaxInterval = Integer.MAX_VALUE;
        ExponentialBackOff backOffPolicy =
                new ExponentialBackOff.Builder()
                        .setInitialIntervalMillis(testInitialInterval)
                        .setMultiplier(testMultiplier)
                        .setMaxIntervalMillis(testMaxInterval)
                        .build();
        backOffPolicy.nextBackOffMillis();
        // Assert that when an overflow is possible the current interval is set to the max interval.
        assertEquals(testMaxInterval, backOffPolicy.getCurrentIntervalMillis());
    }
}
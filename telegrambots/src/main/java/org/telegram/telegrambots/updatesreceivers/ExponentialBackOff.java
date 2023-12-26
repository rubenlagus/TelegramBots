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
package org.telegram.telegrambots.updatesreceivers;

import org.telegram.telegrambots.meta.generics.BackOff;

/**
 * Implementation of BackOff that increases the back off period for each retry attempt using
 * a randomization function that grows exponentially.
 *
 * <p>
 * {@link #nextBackOffMillis()} is calculated using the following formula:
 * </p>
 *
 * <pre>
 randomized_interval =
 retry_interval * (random value in range [1 - randomization_factor, 1 + randomization_factor])
 * </pre>
 *
 * <p>
 * In other words {@link #nextBackOffMillis()} will range between the randomization factor
 * percentage below and above the retry interval. For example, using 2 seconds as the base retry
 * interval and 0.5 as the randomization factor, the actual back off period used in the next retry
 * attempt will be between 1 and 3 seconds.
 * </p>
 *
 * <p>
 * <b>Note:</b> max_interval caps the retry_interval and not the randomized_interval.
 * </p>
 *
 *
 * <p>
 * Example: The default retry_interval is .5 seconds, default randomization_factor is 0.5, default
 * multiplier is 1.5 and the default max_interval is 1 minute. For 10 tries the sequence will be
 * (values in seconds) and assuming we go over the max_elapsed_time on the 10th try:
 * </p>
 *
 * <pre>
 request#     retry_interval     randomized_interval

 1             0.5                [0.25,   0.75]
 2             0.75               [0.375,  1.125]
 3             1.125              [0.562,  1.687]
 4             1.687              [0.8435, 2.53]
 5             2.53               [1.265,  3.795]
 6             3.795              [1.897,  5.692]
 7             5.692              [2.846,  8.538]
 8             8.538              [4.269, 12.807]
 9            12.807              [6.403, 19.210]
 10           19.210              {@link ExponentialBackOff#maxElapsedTimeMillis}
 * </pre>
 *
 * <p>
 * Implementation is not thread-safe.
 * </p>
 *
 * @since 1.15
 * @author Ravi Mistry
 */
public class ExponentialBackOff implements BackOff {
    /** The default initial interval value in milliseconds (0.5 seconds). */
    private static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;

    /**
     * The default randomization factor (0.5 which results in a random period ranging between 50%
     * below and 50% above the retry interval).
     */
    private static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5;

    /** The default multiplier value (1.5 which is 50% increase per back off). */
    private static final double DEFAULT_MULTIPLIER = 1.5;

    /** The default maximum back off time in milliseconds (15 minutes). */
    private static final int DEFAULT_MAX_INTERVAL_MILLIS = 900000;

    /** The default maximum elapsed time in milliseconds (60 minutes). */
    private static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 3600000;

    /** The current retry interval in milliseconds. */
    private int currentIntervalMillis;

    /** The initial retry interval in milliseconds. */
    private final int initialIntervalMillis;

    /**
     * The randomization factor to use for creating a range around the retry interval.
     *
     * <p>
     * A randomization factor of 0.5 results in a random period ranging between 50% below and 50%
     * above the retry interval.
     * </p>
     */
    private final double randomizationFactor;

    /** The value to multiply the current interval with for each retry attempt. */
    private final double multiplier;

    /**
     * The maximum value of the back off period in milliseconds. Once the retry interval reaches this
     * value it stops increasing.
     */
    private final int maxIntervalMillis;

    /**
     * The system time in nanoseconds. It is calculated when an ExponentialBackOffPolicy instance is
     * created and is reset when {@link #reset()} is called.
     */
    private long startTimeNanos;

    /**
     * The maximum elapsed time after instantiating {@link ExponentialBackOff} or calling
     * {@link #reset()} after which {@link #nextBackOffMillis()} returns this value.
     */
    private final int maxElapsedTimeMillis;

    /**
     * Creates an instance of ExponentialBackOffPolicy using default values.
     *
     * <p>
     * To override the defaults use {@link Builder}.
     * </p>
     *
     * <ul>
     * <li>{@code initialIntervalMillis} defaults to {@link #DEFAULT_INITIAL_INTERVAL_MILLIS}</li>
     * <li>{@code randomizationFactor} defaults to {@link #DEFAULT_RANDOMIZATION_FACTOR}</li>
     * <li>{@code multiplier} defaults to {@link #DEFAULT_MULTIPLIER}</li>
     * <li>{@code maxIntervalMillis} defaults to {@link #DEFAULT_MAX_INTERVAL_MILLIS}</li>
     * <li>{@code maxElapsedTimeMillis} defaults in {@link #DEFAULT_MAX_ELAPSED_TIME_MILLIS}</li>
     * </ul>
     */
    ExponentialBackOff() {
        this(new Builder());
    }

    /**
     * @param builder builder
     */
    private ExponentialBackOff(Builder builder) {
        initialIntervalMillis = builder.initialIntervalMillis;
        randomizationFactor = builder.randomizationFactor;
        multiplier = builder.multiplier;
        maxIntervalMillis = builder.maxIntervalMillis;
        maxElapsedTimeMillis = builder.maxElapsedTimeMillis;
        if (initialIntervalMillis <= 0) {
            throw new IllegalArgumentException("InitialIntervalMillis must not be negative");
        }
        if (maxElapsedTimeMillis <= 0) {
            throw new IllegalArgumentException("MaxElapsedTimeMillis must not be negative");
        }
        if (multiplier < 1) {
            throw new IllegalArgumentException("Multiplier must be bigger than 0");
        }
        if (maxIntervalMillis < initialIntervalMillis) {
            throw new IllegalArgumentException("InitialIntervalMillis must be smaller or equal maxIntervalMillis");
        }
        if (randomizationFactor < 0 || randomizationFactor >= 1) {
            throw new IllegalArgumentException("RandomizationFactor must be between 0 and 1");
        }
        reset();
    }

    /** Sets the interval back to the initial retry interval and restarts the timer. */
    @Override
    public void reset() {
        currentIntervalMillis = initialIntervalMillis;
        startTimeNanos = nanoTime();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This method calculates the next back off interval using the formula: randomized_interval =
     * retry_interval +/- (randomization_factor * retry_interval)
     * </p>
     *
     * <p>
     * Subclasses may override if a different algorithm is required.
     * </p>
     */
    @Override
    public long nextBackOffMillis() {
        // Make sure we have not gone over the maximum elapsed time.
        if (getElapsedTimeMillis() > maxElapsedTimeMillis) {
            return maxElapsedTimeMillis;
        }
        int randomizedInterval =
                getRandomValueFromInterval(randomizationFactor, Math.random(), currentIntervalMillis);
        incrementCurrentInterval();
        return randomizedInterval;
    }

    /**
     * Returns a random value from the interval [randomizationFactor * currentInterval,
     * randomizationFactor * currentInterval].
     */
    private static int getRandomValueFromInterval(
            double randomizationFactor, double random, int currentIntervalMillis) {
        double delta = randomizationFactor * currentIntervalMillis;
        double minInterval = currentIntervalMillis - delta;
        double maxInterval = currentIntervalMillis + delta;
        // Get a random value from the range [minInterval, maxInterval].
        // The formula used below has a +1 because if the minInterval is 1 and the maxInterval is 3 then
        // we want a 33% chance for selecting either 1, 2 or 3.
        return (int) (minInterval + (random * (maxInterval - minInterval + 1)));
    }

    /**
     * Returns the elapsed time in milliseconds since an {@link ExponentialBackOff} instance is
     * created and is reset when {@link #reset()} is called.
     *
     * <p>
     * The elapsed time is computed using {@link System#nanoTime()}.
     * </p>
     */
    private long getElapsedTimeMillis() {
        return (nanoTime() - startTimeNanos) / 1000000;
    }

    /**
     * Increments the current interval by multiplying it with the multiplier.
     */
    private void incrementCurrentInterval() {
        // Check for overflow, if overflow is detected set the current interval to the max interval.
        if (currentIntervalMillis >= maxIntervalMillis / multiplier) {
            currentIntervalMillis = maxIntervalMillis;
        } else {
            currentIntervalMillis *= multiplier;
        }
    }

    /**
     * Nano time using  {@link System#nanoTime()}
     * @return time in nanoseconds
     */
    private long nanoTime() {
        return System.nanoTime();
    }

    /**
     * Builder for {@link ExponentialBackOff}.
     *
     * <p>
     * Implementation is not thread-safe.
     * </p>
     */
    public static class Builder {

        /** The initial retry interval in milliseconds. */
        int initialIntervalMillis = DEFAULT_INITIAL_INTERVAL_MILLIS;

        /**
         * The randomization factor to use for creating a range around the retry interval.
         *
         * <p>
         * A randomization factor of 0.5 results in a random period ranging between 50% below and 50%
         * above the retry interval.
         * </p>
         */
        double randomizationFactor = DEFAULT_RANDOMIZATION_FACTOR;

        /** The value to multiply the current interval with for each retry attempt. */
        double multiplier = DEFAULT_MULTIPLIER;

        /**
         * The maximum value of the back off period in milliseconds. Once the retry interval reaches
         * this value it stops increasing.
         */
        int maxIntervalMillis = DEFAULT_MAX_INTERVAL_MILLIS;

        /**
         * The maximum elapsed time in milliseconds after instantiating {@link ExponentialBackOff} or
         * calling {@link #reset()} after which {@link #nextBackOffMillis()} returns
         * this value.
         */
        int maxElapsedTimeMillis = DEFAULT_MAX_ELAPSED_TIME_MILLIS;

        public Builder() {
        }

        public Builder setInitialIntervalMillis(int initialIntervalMillis) {
            this.initialIntervalMillis = initialIntervalMillis;
            return this;
        }

        public Builder setRandomizationFactor(double randomizationFactor) {
            this.randomizationFactor = randomizationFactor;
            return this;
        }

        public Builder setMultiplier(double multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public Builder setMaxIntervalMillis(int maxIntervalMillis) {
            this.maxIntervalMillis = maxIntervalMillis;
            return this;
        }

        public Builder setMaxElapsedTimeMillis(int maxElapsedTimeMillis) {
            this.maxElapsedTimeMillis = maxElapsedTimeMillis;
            return this;
        }

        /** Builds a new instance of {@link ExponentialBackOff}. */
        public ExponentialBackOff build() {
            return new ExponentialBackOff(this);
        }
    }
}

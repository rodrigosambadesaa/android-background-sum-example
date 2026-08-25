package com.example.tesdai.myapplication;

import java.util.Objects;

/** Pure Java worker used by the Android example. */
public final class ProgressiveSummer {

    @FunctionalInterface
    public interface ProgressListener {
        void onProgress(int completed, int total);
    }

    private ProgressiveSummer() {
        // Utility class.
    }

    public static long sum(
            int[] values,
            long delayMillis,
            ProgressListener progressListener
    ) throws InterruptedException {
        Objects.requireNonNull(values, "values");
        if (delayMillis < 0) {
            throw new IllegalArgumentException("delayMillis must be non-negative");
        }

        long total = 0L;
        for (int index = 0; index < values.length; index++) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("sum cancelled");
            }

            if (delayMillis > 0) {
                Thread.sleep(delayMillis);
            }

            total = Math.addExact(total, values[index]);
            if (progressListener != null) {
                progressListener.onProgress(index + 1, values.length);
            }
        }
        return total;
    }
}

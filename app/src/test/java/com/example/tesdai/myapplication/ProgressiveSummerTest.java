package com.example.tesdai.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProgressiveSummerTest {

    @Test
    public void sumsOriginalExampleValues() throws InterruptedException {
        assertEquals(32L, ProgressiveSummer.sum(new int[]{10, 7, 8, 2, 4, 1}, 0, null));
    }

    @Test
    public void reportsEveryProgressStep() throws InterruptedException {
        List<Integer> completedSteps = new ArrayList<>();
        long result = ProgressiveSummer.sum(
                new int[]{4, 5, 6},
                0,
                (completed, total) -> {
                    assertEquals(3, total);
                    completedSteps.add(completed);
                }
        );

        assertEquals(15L, result);
        assertEquals(List.of(1, 2, 3), completedSteps);
    }

    @Test
    public void rejectsNegativeDelay() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProgressiveSummer.sum(new int[]{1}, -1, null)
        );
    }
}

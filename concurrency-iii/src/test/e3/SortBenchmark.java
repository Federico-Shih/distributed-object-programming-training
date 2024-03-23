package src.test.e3;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Benchmar to compare between {@link Arrays#parallelSort(int[])} and
 * {@link Arrays#sort(int[])}
 */
public class SortBenchmark {
    @Test
    public void benchmark_all() {
        final int multiplier = 1000000;
        final Random random = new Random();

        int[] small = generateArray(10 * multiplier, random);
        int[] medium = generateArray(25 * multiplier, random);
        int[] large = generateArray(50 * multiplier, random);

        benchmark(Arrays.copyOf(small, small.length), (arr) -> {
            System.out.println("Small parallel");
            Arrays.parallelSort(arr);
        });
        benchmark(Arrays.copyOf(small, small.length),  (arr) -> {
            System.out.println("Small");
            Arrays.sort(arr);
        });
        benchmark(Arrays.copyOf(medium, medium.length),  (arr) -> {
            System.out.println("Medium parallel");
            Arrays.parallelSort(arr);
        });
        benchmark(Arrays.copyOf(medium, medium.length),  (arr) -> {
            System.out.println("Medium");
            Arrays.sort(arr);
        });
        benchmark(Arrays.copyOf(large, large.length),  (arr) -> {
            System.out.println("Large parallel");
            Arrays.parallelSort(arr);
        });
        benchmark(Arrays.copyOf(large, large.length),  (arr) -> {
            System.out.println("Large");
            Arrays.sort(arr);
        });
    }

    public void benchmark(int[] array,  Consumer<int[]> function) {
        long startTime = System.currentTimeMillis();
        function.accept(array);
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
    }

    int[] generateArray(int size, Random random) {
        return random.ints(size).toArray();
    }
}

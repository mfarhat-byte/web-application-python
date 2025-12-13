package benchmarks;

import jmlcore.SimpleItem;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * JMH Microbenchmark for SimpleItem performance testing.
 * Tests the performance of SimpleItem creation and string comparison operations.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SimpleItemBenchmark {

    private String shortString1;
    private String shortString2;
    private String longString1;
    private String longString2;

    @Setup
    public void setup() {
        shortString1 = "Open";
        shortString2 = "Open";
        longString1 = "This is a very long string that contains many characters and words";
        longString2 = "This is a very long string that contains many characters and words";
    }

    @Benchmark
    public void benchmarkSimpleItemCreation(Blackhole blackhole) {
        SimpleItem item = new SimpleItem("Title", "Description", "Open", "Bug");
        blackhole.consume(item);
    }

    @Benchmark
    public void benchmarkSimpleItemWithLongStrings(Blackhole blackhole) {
        SimpleItem item = new SimpleItem(
                "A very long title with many words that describes the task in detail",
                "A comprehensive description that provides extensive information about " +
                        "what needs to be accomplished, including all the necessary details " +
                        "and requirements for successful completion",
                "In Progress",
                "Feature Request"
        );
        blackhole.consume(item);
    }

    @Benchmark
    public void benchmarkStrEqShortStrings(Blackhole blackhole) {
        boolean result = SimpleItem.strEq(shortString1, shortString2);
        blackhole.consume(result);
    }

    @Benchmark
    public void benchmarkStrEqLongStrings(Blackhole blackhole) {
        boolean result = SimpleItem.strEq(longString1, longString2);
        blackhole.consume(result);
    }

    @Benchmark
    public void benchmarkStrEqDifferentStrings(Blackhole blackhole) {
        boolean result = SimpleItem.strEq(shortString1, longString1);
        blackhole.consume(result);
    }

    @Benchmark
    public void benchmarkMultipleCreationsAndComparisons(Blackhole blackhole) {
        for (int i = 0; i < 5; i++) {
            SimpleItem item = new SimpleItem("Task" + i, "Description" + i, "Open", "Bug");
            boolean eq = SimpleItem.strEq(item.title, "Task" + i);
            blackhole.consume(item);
            blackhole.consume(eq);
        }
    }

    @Benchmark
    public void benchmarkBatchItemCreation(Blackhole blackhole) {
        SimpleItem[] items = new SimpleItem[10];
        for (int i = 0; i < 10; i++) {
            items[i] = new SimpleItem(
                    "Title " + i,
                    "Description " + i,
                    "Status " + (i % 3),
                    "Type " + (i % 4)
            );
        }
        blackhole.consume(items);
    }
}
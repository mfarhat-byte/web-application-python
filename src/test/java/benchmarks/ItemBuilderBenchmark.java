package benchmarks;

import jmlcore.ItemBuilder;
import jmlcore.SimpleItem;
import jmlcore.SimpleItemDTO;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * JMH Microbenchmark for ItemBuilder performance testing.
 * Tests the performance of building SimpleItem objects from DTOs.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ItemBuilderBenchmark {

    private SimpleItemDTO simpleDTO;
    private SimpleItemDTO complexDTO;

    @Setup
    public void setup() {
        // Simple DTO
        simpleDTO = new SimpleItemDTO("Task", "Simple task description");

        // Complex DTO with longer strings
        complexDTO = new SimpleItemDTO(
                "Complex Task with Very Long Title That Contains Many Words",
                "This is a very detailed description that contains multiple sentences " +
                        "and a lot of information about the task at hand. It includes details " +
                        "about what needs to be done, why it needs to be done, and how to do it."
        );
    }

    @Benchmark
    public void benchmarkBuildSimpleItem(Blackhole blackhole) {
        SimpleItem item = ItemBuilder.build(simpleDTO, "Open", "Bug");
        blackhole.consume(item);
    }

    @Benchmark
    public void benchmarkBuildComplexItem(Blackhole blackhole) {
        SimpleItem item = ItemBuilder.build(complexDTO, "In Progress", "Feature");
        blackhole.consume(item);
    }

    @Benchmark
    public void benchmarkBuildMultipleItems(Blackhole blackhole) {
        for (int i = 0; i < 10; i++) {
            SimpleItem item = ItemBuilder.build(simpleDTO, "Open", "Task");
            blackhole.consume(item);
        }
    }

    @Benchmark
    public void benchmarkBuildWithDifferentStatuses(Blackhole blackhole) {
        String[] statuses = {"Open", "In Progress", "Done", "Closed"};
        String[] types = {"Bug", "Feature", "Task", "Epic"};

        for (int i = 0; i < statuses.length; i++) {
            SimpleItem item = ItemBuilder.build(simpleDTO, statuses[i], types[i]);
            blackhole.consume(item);
        }
    }
}
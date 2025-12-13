package benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Main runner for JMH benchmarks.
 * Execute this class to run all benchmark tests.
 */
public class BenchmarkRunner {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ItemBuilderBenchmark.class.getSimpleName())
                .include(SimpleItemBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
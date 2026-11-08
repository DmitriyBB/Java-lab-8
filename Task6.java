import java.util.*;
import java.util.stream.*;
public class Task6 {
    record Stats(long count, long sum, int min, int max) {}
    static final class Acc {
        long count;
        long sum;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        void add(int v) {
            count++;
            sum += v;
            min = Math.min(min, v);
            max = Math.max(max, v);
        }
        Acc merge(Acc other) {
            count += other.count;
            sum += other.sum;
            min = Math.min(min, other.min);
            max = Math.max(max, other.max);
            return this;
        }
    }
    static Collector<Integer, Acc, Stats> statsCollector() {
        return Collector.of(
                Acc::new,
                Acc::add,
                Acc::merge,
                a -> a.count == 0
                        ? new Stats(0, 0, 0, 0)
                        : new Stats(a.count, a.sum, a.min, a.max));
    }
    public static void main(String[] args) {
        List<Integer> durations = List.of(120, 90, 180, 240, 180);
        Stats mine = durations.stream().collect(statsCollector());
        Stats parallel = durations.parallelStream().collect(statsCollector());
        IntSummaryStatistics std = durations.stream().collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println("Свой collector:     " + mine);
        System.out.println("Параллельно:        " + parallel);
        System.out.println("summarizingInt:     count=" + std.getCount() + ", sum=" + std.getSum()
                + ", min=" + std.getMin() + ", max=" + std.getMax());
    }
}

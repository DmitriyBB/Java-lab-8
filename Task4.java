import java.util.*;
import java.util.stream.*;
public class Task4 {
    enum Level { BEGINNER, INTERMEDIATE, ADVANCED }
    record Course(String code, Level level, int duration) {}
    record LevelStats(long count, double avgDuration) {}
    record Summary(Map<Level, LevelStats> byLevel) {}
    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course("JAVA-1", Level.BEGINNER, 120),
                new Course("SQL-1", Level.BEGINNER, 90),
                new Course("JAVA-2", Level.INTERMEDIATE, 180),
                new Course("ARCH-1", Level.ADVANCED, 240),
                new Course("ALGO-1", Level.ADVANCED, 180));
        Map<Level, LevelStats> byLevel = courses.stream().collect(Collectors.groupingBy(
                Course::level, TreeMap::new,
                Collectors.collectingAndThen(
                        Collectors.summarizingInt(Course::duration),
                        s -> new LevelStats(s.getCount(), s.getAverage()))));
        System.out.println(new Summary(byLevel));
    }
}

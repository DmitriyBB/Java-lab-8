import java.util.*;
public class Task1 {
    enum Level { BEGINNER, INTERMEDIATE, ADVANCED }
    record Course(String code, Level level, int duration) {}
    static final Comparator<Course> ORDER = Comparator.comparing(Course::level)
            .thenComparingInt(Course::duration)
            .thenComparing(Course::code);
    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course("JAVA-1", Level.BEGINNER, 120),
                new Course("SQL-1", Level.BEGINNER, 90),
                new Course("JAVA-2", Level.INTERMEDIATE, 180),
                new Course("ARCH-1", Level.ADVANCED, 240),
                new Course("ALGO-1", Level.ADVANCED, 180));
        List<Course> reversed = new ArrayList<>(courses);
        Collections.reverse(reversed);
        List<Course> a = courses.stream().sorted(ORDER).toList();
        List<Course> b = reversed.stream().sorted(ORDER).toList();
        a.forEach(System.out::println);
        System.out.println("Одинаково для другого входного порядка: " + a.equals(b));
    }
}

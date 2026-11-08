import java.util.*;
public class Task5 {
    record Course(String code, String title, int duration) {}
    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course("JAVA-1", "Java Basics", 120),
                new Course("ARCH-1", "Architecture", 240),
                new Course("ALGO-1", "Algorithms", 180));
        Optional<Course> longest = courses.stream()
                .max(Comparator.comparingInt(Course::duration).thenComparing(Course::code));
        String title = longest.map(Course::title).orElse("нет курсов");
        System.out.println("Самый длинный: " + title);
        String none = List.<Course>of().stream()
                .max(Comparator.comparingInt(Course::duration))
                .map(Course::title)
                .orElseGet(() -> "нет курсов");
        System.out.println("Пустой список: " + none);
    }
}

import java.util.*;
import java.util.stream.*;
public class Task2 {
    record Course(String code, List<String> modules) {}
    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course("JAVA-1", List.of("Variables", "Loops", " loops ")),
                new Course("SQL-1", List.of("Select", "Joins")),
                new Course("JAVA-2", List.of("Streams", "Lambdas", "Collectors", "loops")),
                new Course("ARCH-1", List.of("Patterns", " Joins ")));
        Map<String, List<String>> topicToCourses = courses.stream()
                .sorted(Comparator.comparing(Course::code))
                .flatMap(c -> c.modules().stream()
                        .map(m -> m.trim().toLowerCase())
                        .distinct()
                        .map(topic -> Map.entry(topic, c.code())))
                .collect(Collectors.groupingBy(Map.Entry::getKey, TreeMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        topicToCourses.forEach((topic, list) -> System.out.println(topic + " → " + list));
    }
}

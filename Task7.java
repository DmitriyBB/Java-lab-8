import java.util.*;
import java.util.stream.*;
public class Task7 {
    enum Level { BEGINNER, INTERMEDIATE, ADVANCED }
    enum Type { ENROLLED, COMPLETED }
    record Course(String code, Level level) {}
    record Event(String student, String courseCode, Type type) {}
    record Report(int uniqueStudents, Map<String, Double> completionRate,
                  List<String> top3, Map<Level, Integer> enrollmentsByLevel, List<String> anomalies) {}
    static Report report(Iterable<Event> events, Map<String, Course> catalog) {
        Set<String> students = new TreeSet<>();
        Set<String> enrolledKeys = new HashSet<>();
        Set<String> completedKeys = new HashSet<>();
        Map<String, Integer> enrolled = new TreeMap<>();
        Map<String, Integer> completed = new TreeMap<>();
        Map<Level, Integer> byLevel = new TreeMap<>();
        List<String> anomalies = new ArrayList<>();
        for (Event e : events) {
            Course course = catalog.get(e.courseCode());
            if (course == null) {
                anomalies.add("неизвестный курс: " + e.courseCode());
                continue;
            }
            students.add(e.student());
            String key = e.student() + "|" + e.courseCode();
            if (e.type() == Type.ENROLLED) {
                if (!enrolledKeys.add(key)) {
                    anomalies.add("повторная запись: " + key);
                    continue;
                }
                enrolled.merge(e.courseCode(), 1, Integer::sum);
                byLevel.merge(course.level(), 1, Integer::sum);
            } else {
                if (!enrolledKeys.contains(key)) {
                    anomalies.add("завершение без записи: " + key);
                    continue;
                }
                if (!completedKeys.add(key)) {
                    anomalies.add("повторное завершение: " + key);
                    continue;
                }
                completed.merge(e.courseCode(), 1, Integer::sum);
            }
        }
        Map<String, Double> rate = enrolled.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> completed.getOrDefault(e.getKey(), 0) / (double) e.getValue(),
                (a, b) -> a,
                TreeMap::new));
        List<String> top3 = completed.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.<String, Integer>comparingByKey()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
        return new Report(students.size(), rate, top3, byLevel, anomalies);
    }
    public static void main(String[] args) {
        Map<String, Course> catalog = Map.of(
                "JAVA-1", new Course("JAVA-1", Level.BEGINNER),
                "SQL-1", new Course("SQL-1", Level.BEGINNER),
                "ARCH-1", new Course("ARCH-1", Level.ADVANCED),
                "ALGO-1", new Course("ALGO-1", Level.ADVANCED));
        List<Event> events = List.of(
                new Event("ann", "JAVA-1", Type.ENROLLED),
                new Event("ann", "JAVA-1", Type.COMPLETED),
                new Event("bob", "JAVA-1", Type.ENROLLED),
                new Event("bob", "JAVA-1", Type.ENROLLED),
                new Event("cid", "SQL-1", Type.COMPLETED),
                new Event("dan", "XXX-9", Type.ENROLLED),
                new Event("ann", "ARCH-1", Type.ENROLLED),
                new Event("bob", "ARCH-1", Type.COMPLETED),
                new Event("eve", "SQL-1", Type.ENROLLED),
                new Event("eve", "SQL-1", Type.COMPLETED),
                new Event("ann", "ALGO-1", Type.ENROLLED),
                new Event("eve", "ALGO-1", Type.ENROLLED));
        System.out.println(report(events, catalog));
    }
}

import java.util.List;
import java.util.stream.IntStream;
public class Task3 {
    record Minutes(int value) {
        static final Minutes ZERO = new Minutes(0);
        Minutes plus(Minutes other) {
            return new Minutes(value + other.value);
        }
    }
    public static void main(String[] args) {
        List<Integer> durations = List.of(120, 90, 180, 240, 180);
        Minutes total = durations.stream()
                .map(Minutes::new)
                .reduce(Minutes.ZERO, Minutes::plus);
        Minutes wrong = durations.stream()
                .map(Minutes::new)
                .reduce(new Minutes(1000), Minutes::plus);
        System.out.println("Верно: " + total);
        System.out.println("Неверная identity: " + wrong);
        System.out.println("Вычитание последовательно: " + IntStream.of(1, 2, 3, 4).reduce(0, (a, b) -> a - b));
    }
}

import java.util.Scanner;
import java.lang.Math;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите два дробных числа:");
        System.out.print("Первое число: ");
        double a = scanner.nextDouble();
        System.out.print("Второе число: ");
        double b = scanner.nextDouble();
        System.out.println("\nВыберите метод умножения:");
        System.out.println("1. Через сложение (итеративно)");
        System.out.println("2. Через логарифмы");
        System.out.println("3. Через битовые операции (с масштабированием)");
        System.out.println("4. Через рекурсию");
        System.out.println("5. Через разложение на степени (приближённо)");
        System.out.println("6. Через разность квадратов");
        System.out.print("Введите номер метода (1-6): ");
        int choice = scanner.nextInt();
        double result = 0;
        switch (choice) {
            case 1:
                result = Slogenie(a, b);
                break;
            case 2:
                result = Logarithms(a, b);
                break;
            case 3:
                result = BitShifts(a, b);
                break;
            case 4:
                result = Recursion(a, b);
                break;
            case 5:
                result = PowerDecomposition(a, b);
                break;
            case 6:
                result = Squares(a, b);
                break;
            default:
                System.out.println("Неверный выбор!");
                scanner.close();
                return;
        }
        System.out.printf("Результат: %.6f\n", result);
        scanner.close();
    }
    // 1. Умножение через сложение (с аппроксимацией дробей)
    public static double Slogenie(double a, double b) {
        if (a == 0 || b == 0) return 0;
        // Определим знак результата
        boolean negative = (a < 0) ^ (b < 0);
        a = Math.abs(a);
        b = Math.abs(b);
        // Масштабируем до целых (например, x1000), чтобы использовать сложение
        final long SCALE = 1000L;
        long scaledA = Math.round(a * SCALE);
        long scaledB = Math.round(b * SCALE);
        long sum = 0;
        for (long i = 0; i < scaledB; i++) {
            sum += scaledA;
        }
        double result = (double) sum / (SCALE * SCALE);
        return negative ? -result : result;
    }
    // 2. Умножение через логарифмы: a * b = exp(ln|a| + ln|b|)
    public static double Logarithms(double a, double b) {
        if (a == 0 || b == 0) return 0;
        boolean negative = (a < 0) ^ (b < 0);
        a = Math.abs(a);
        b = Math.abs(b);
        double result = Math.exp(Math.log(a) + Math.log(b));
        return negative ? -result : result;
    }
    // 3. Умножение через битовые сдвиги (только для масштабированных целых)
    public static double BitShifts(double a, double b) {
        if (a == 0 || b == 0) return 0;
        boolean negative = (a < 0) ^ (b < 0);
        a = Math.abs(a);
        b = Math.abs(b);
        final long SCALE = 1000L;
        long scaledA = Math.round(a * SCALE);
        long scaledB = Math.round(b * SCALE);
        long result = 0;
        while (scaledB > 0) {
            if ((scaledB & 1) != 0) {
                result += scaledA;
            }
            scaledA <<= 1;  // Удвоение
            scaledB >>= 1;  // Деление на 2
        }
        double finalResult = (double) result / (SCALE * SCALE);
        return negative ? -finalResult : finalResult;
    }
    // 4. Умножение через рекурсию (с масштабированием)
    public static double Recursion(double a, double b) {
        if (a == 0 || b == 0) return 0;
        boolean negative = (a < 0) ^ (b < 0);
        a = Math.abs(a);
        b = Math.abs(b);
        final long SCALE = 1000L;
        long scaledA = Math.round(a * SCALE);
        long scaledB = Math.round(b * SCALE);
        long result = RecursiveHelper(scaledA, scaledB);
        double finalResult = (double) result / (SCALE * SCALE);
        return negative ? -finalResult : finalResult;
    }
    private static long RecursiveHelper(long a, long b) {
        if (b == 0) return 0;
        if (b == 1) return a;
        return a + RecursiveHelper(a, b - 1);
    }
    // 5. Приближённое умножение через разложение на степени (метод приближения)
    public static double PowerDecomposition(double a, double b) {
        if (a == 0 || b == 0) return 0;
        boolean negative = (a < 0) ^ (b < 0);
        a = Math.abs(a);
        b = Math.abs(b);
        // Использую разложение: a * b ≈ sum_{i} (a * bit_i) << i
        // Но для дробей — проще использовать логарифмы или сложение
        // Альтернатива: разложение через двоичные дроби (очень сложно)
        // Упрощённый подход: разбиваем b на целую и дробную часть
        long intPart = (long) b;
        double fracPart = b - intPart;
        double result = 0;
        // Целая часть — через сложение
        for (int i = 0; i < intPart; i++) {
            result += a;
        }
        // Дробная часть — приближаем через усреднение
        result += a * fracPart; // Здесь * не избегаем, но можно заменить на ряд
        // Замена a * fracPart на ряд Тейлора или аппроксимацию — сложно
        // Оставим как упрощение, но отметим, что это не чистый метод
        return negative ? -result : result;
    }
    // 6. Умножение через разность квадратов: a * b = ((a + b)^2 - a^2 - b^2)/2
    // Т.е.: a * b = [(a + b)^2 - (a - b)^2] / 4
    public static double Squares(double a, double b) {
        double sum = a + b;
        double diff = a - b;
        double result = (sum * sum - diff * diff) / 4.0;
        //Здесь * используется, но можно заменить на вызов другого метода
        //Заменим возведение в квадрат на вызов multiplyByAddition
        double sumSq = Slogenie(sum, sum);
        double diffSq = Slogenie(diff, diff);
        return (sumSq - diffSq) / 4.0;
    }
}

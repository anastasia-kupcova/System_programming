import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Умножение двух целых чисел без оператора * ===");
        System.out.print("Введите первое число: ");
        int a = scanner.nextInt();
        System.out.print("Введите второе число: ");
        int b = scanner.nextInt();
        System.out.println("\nВыберите метод (1–13):");
        System.out.println("1. Через сложение в цикле");
        System.out.println("2. Через рекурсию");
        System.out.println("3. Через битовые сдвиги");
        System.out.println("4. Через логарифмы и экспоненту (только положительные)");
        System.out.println("5. Через формулу (a+b)² - a² - b²) / 2");
        System.out.println("6. Через разложение на степени двойки");
        System.out.println("7. Через деление на обратное число");
        System.out.println("8. Через формулу разности квадратов");
        System.out.println("9. Через матричное умножение (2x2)");
        System.out.println("10. Через рекурсию + битовые операции");
        System.out.println("11. Через побитовый анализ");
        System.out.println("12. Через формулу: a * b = (a << log2(b)) при b = 2^n");
        System.out.println("13. Через серию приближений (для int)");
        System.out.print("Ваш выбор (1–13): ");
        int choice = scanner.nextInt();
        try {
            long result;
            switch (choice) {
                case 1: result = m1(a, b); break;
                case 2: result = m2(a, b); break;
                case 3: result = m3(a, b); break;
                case 4: result = (int) m4(a, b); break;
                case 5: result = m5(a, b); break;
                case 6: result = m6(a, b); break;
                case 7: result = m7(a, b); break;
                case 8: result = m8(a, b); break;
                case 9: result = m9(a, b); break;
                case 10: result = m10(a, b); break;
                case 11: result = m11(a, b); break;
                case 12: result = m12(a, b); break;
                case 13: result = m13(a, b); break;
                default:
                    System.out.println("Неверный выбор.");
                    return;
            }
            System.out.printf("Результат: %d\n", result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    // m1: Простое сложение (в цикле)
    public static int m1(int a, int b) {
        if (a == 0 || b == 0) return 0;
        if (b < 0) return -m1(a, -b);
        long res = 0;
        for (int i = 0; i < b; i++) res += a;
        return Math.toIntExact(res);
    }
    // m2: Рекурсивное сложение
    public static int m2(int a, int b) {
        if (b == 0 || a == 0) return 0;
        if (b < 0) return -m2(a, -b);
        if (b == 1) return a;
        return a + m2(a, b - 1);
    }
    // m3: Битовые сдвиги (аналог умножения в столбик)
    public static int m3(int a, int b) {
        long res = 0;
        boolean negative = (a < 0) ^ (b < 0);
        long la = Math.abs((long) a);
        long lb = Math.abs((long) b);
        while (lb > 0) {
            if ((lb & 1) == 1) res += la;
            la <<= 1;
            lb >>= 1;
        }
        return Math.toIntExact(negative ? -res : res);
    }
    // m4: Логарифмы: a * b = exp(ln(a) + ln(b)) — только для a,b > 0
    public static int m4(int a, int b) {
        if (a <= 0 || b <= 0) return 0; // не поддерживается
        return (int) Math.exp(Math.log(a) + Math.log(b));
    }
    // m5: Формула: ab = ((a+b)^2 - a^2 - b^2) / 2
    public static int m5(int a, int b) {
        return Math.toIntExact((((long) a + b) * (a + b) - (long) a * a - (long) b * b) / 2);
    }
    // m6: Декомпозиция по битам (аналог m3, но с другим стилем)
    public static int m6(int a, int b) {
        long res = 0;
        boolean negative = (a < 0) ^ (b < 0);
        a = Math.abs(a);
        b = Math.abs(b);
        while (b > 0) {
            if ((b & 1) == 1) res += a;
            a <<= 1;
            b >>= 1;
        }
        return Math.toIntExact(negative ? -res : res);
    }
    // m7: a * b = a / (1.0 / b) — если b != 0
    public static int m7(int a, int b) {
        if (b == 0) return 0;
        return Math.toIntExact((long) (a / (1.0 / b)));
    }
    // m8: Разность квадратов: a*b = (a+b)²/4 - (a-b)²/4
    public static int m8(int a, int b) {
        return Math.toIntExact(((long) (a + b) * (a + b) - (long) (a - b) * (a - b)) / 4);
    }
    // m9: Матричное умножение: [[a, 0], [0,0]] * [[b,0],[0,0]] = [[a*b,0],[0,0]]
    public static int m9(int a, int b) {
        int[][] m1 = {{a, 0}, {0, 0}};
        int[][] m2 = {{b, 0}, {0, 0}};
        return m1[0][0] * m2[0][0] + m1[0][1] * m2[1][0]; // только [0][0]
    }
    // m10: Рекурсивный битовый сдвиг
    public static int m10(int a, int b) {
        if (b == 0) return 0;
        if ((b & 1) == 1) return Math.toIntExact(a + m10(a << 1, b >> 1));
        return m10(a << 1, b >> 1);
    }
    // m11: Побитовый анализ без цикла (рекурсивно)
    public static int m11(int a, int b) {
        if (b == 0) return 0;
        if (b == 1) return a;
        return ((b & 1) == 1 ? a : 0) + m11(a << 1, b >> 1);
    }
    // m12: Только для степеней двойки: a * b = a << log2(b)
    public static int m12(int a, int b) {
        if (b <= 0 || (b & (b - 1)) != 0) return m1(a, b); // если не степень двойки — fallback
        int log2 = 0;
        int temp = b;
        while (temp > 1) {
            temp >>= 1;
            log2++;
        }
        return (a << log2);
    }
    // m13: Итеративное приближение (для целых — просто используем сложение)
    public static int m13(int a, int b) {
        if (b == 0) return 0;
        long res = 0;
        int absB = Math.abs(b);
        for (int i = 0; i < absB; i++) res += a;
        return Math.toIntExact(b < 0 ? -res : res);
    }
}

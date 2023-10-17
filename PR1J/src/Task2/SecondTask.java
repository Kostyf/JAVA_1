package Task2;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class SecondTask {
    public static void main(String[] args) {
        System.out.println("Введите числа через строку для подсчета квадрата (0 для выхода):");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int number = scanner.nextInt();
            if (number == 0) {
                break;
            }

            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> processRequest(number));

            future.thenAccept(result -> {
                System.out.println("Результат для числа " + number + ": " + result);
            }).exceptionally(e -> {
                e.printStackTrace();
                return null;
            });
        }

        scanner.close();
    }

    public static int processRequest(int number) {
        try {
            int delay = (1 + (int) (Math.random() * 5)) * 1000;
            System.out.println("Время задержки для числа " + number + ": " + delay + " миллисекунд");
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number * number;
    }
}
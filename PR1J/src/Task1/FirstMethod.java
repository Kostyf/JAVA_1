package Task1;

import java.util.concurrent.ThreadLocalRandom;

public class FirstMethod {
    public static void main(String[] args) {
        int[] arr = new int[10000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(10000);
        }

        long startTime = System.currentTimeMillis();
        int maxElement = findMax(arr, 0, arr.length - 1);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        long memoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.println("Время выполнения: " + executionTime + " милисекунды");
        System.out.println("Объем памяти: " + memoryUsage / (1024 * 1024) + " МБ");
        System.out.println("Максимальный элемент: " + maxElement);
    }

    public static int findMax(int[] arr, int low, int high) {
        if (low == high) {
            return arr[low];
        }

        int mid = (low + high) / 2;
        int leftMax = findMax(arr, low, mid);
        int rightMax = findMax(arr, mid + 1, high);

        return Math.max(leftMax, rightMax);
    }
}

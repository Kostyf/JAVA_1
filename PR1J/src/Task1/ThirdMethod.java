package Task1;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;


public class ThirdMethod {
    public static void main(String[] args) {
        int[] array = new int[10000];

        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10000);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MaxFinder task = new MaxFinder(array, 0, array.length);

        long startTime = System.nanoTime();
        int result = forkJoinPool.invoke(task);
        long endTime = System.nanoTime();
        long memoryUsage = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        System.out.println("Объем памяти: " + memoryUsage / (1024 * 1024) + " МБ");
        System.out.println("Максимальный элемент: " + result);
        System.out.println("Время выполнения: " + (endTime - startTime) + " микросекунд");
    }
}
class MaxFinder extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 1000;
    private final int[] array;
    private final int start;
    private final int end;

    public MaxFinder(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            return findMaxSequentially();
        } else {
            int mid = (start + end) / 2;
            MaxFinder leftTask = new MaxFinder(array, start, mid);
            MaxFinder rightTask = new MaxFinder(array, mid, end);

            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            return Math.max(leftResult, rightResult);
        }
    }

    private int findMaxSequentially() {
        int max = Integer.MIN_VALUE;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = start; i < end; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
}
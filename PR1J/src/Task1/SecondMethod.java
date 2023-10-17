package Task1;

public class SecondMethod {
    public static void main(String[] args) {
        int[] array = new int[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 100000);
        }
        int numThreads = 4;
        int chunkSize = array.length / numThreads;
        MaxFinderThread[] threads = new MaxFinderThread[numThreads];

        long startTime = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? array.length : (i + 1) * chunkSize;
            threads[i] = new MaxFinderThread(array, start, end);

            // Запускаем каждый поток
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
                int threadMax = threads[i].getMax();
                if (threadMax > max) {
                    max = threadMax;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        long memoryUsage = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        System.out.println("Объем памяти: " + memoryUsage / (1024 * 1024) + " МБ");
        System.out.println("Время выполнения: " + executionTime + " милисекунд");
        System.out.println("Максимальный элемент: " + max);
    }
}

class MaxFinderThread extends Thread {
    private final int[] array;
    private final int start;
    private final int end;
    private int max = Integer.MIN_VALUE;

    public MaxFinderThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public void run() {
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
        }
    public int getMax() {
        return max;
    }
}


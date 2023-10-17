package TASK3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

record File(String type, int size) {
}

class FileGeneratorThread implements Runnable {
    private final BlockingQueue<File> queue;
    private final Random random;

    public FileGeneratorThread(BlockingQueue<File> queue) {
        this.queue = queue;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String[] fileTypes = { "XML", "JSON", "XLS" };
                int fileSize = random.nextInt(91) + 10; // 10 to 100

                File file = new File(fileTypes[random.nextInt(3)], fileSize);
                queue.put(file);

                System.out.println("Добавлен файл в очередь: " + file.type() + ", " + file.size() + "кб");

                Thread.sleep(random.nextInt(901) + 100); // 100 to 1000 ms
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class FileProcessorThread implements Runnable {
    private final BlockingQueue<File> queue;
    private final String supportedFileType;

    public FileProcessorThread(BlockingQueue<File> queue, String supportedFileType) {
        this.queue = queue;
        this.supportedFileType = supportedFileType;
    }

    @Override
    public void run() {
        try {
            while (true) {
                File file = queue.take();

                System.out.println("Удален файл из очереди: " + file.type() + ", " + file.size() + "кб");

                if (file.type().equals(supportedFileType)) {
                    int processingTime = file.size() * 7;
                    Thread.sleep(processingTime);
                    System.out.println("Файл ОБРАБОТАН: " + file.type() + ", " + file.size() + "кб, Time: " + processingTime + "мс");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BlockingQueue<File> queue = new ArrayBlockingQueue<>(5);

        Thread generatorThread = new Thread(new FileGeneratorThread(queue));
        Thread processorThread1 = new Thread(new FileProcessorThread(queue, "XML"));
        Thread processorThread2 = new Thread(new FileProcessorThread(queue, "JSON"));

        generatorThread.start();
        processorThread1.start();
        processorThread2.start();
    }
}

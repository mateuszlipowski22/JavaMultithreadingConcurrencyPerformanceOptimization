package multithreading.section_10.thread_per_task;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IoBoundApplication {
    public static final int NUMBER_OF_THREADS = 10_000;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Press enter to start");
        s.nextLine();

        System.out.printf("Running %d tasks\n", NUMBER_OF_THREADS);

        long start = System.currentTimeMillis();
        performTask();
        System.out.printf("Tasks took %dms to compete\n", System.currentTimeMillis() - start);
    }

    private static void performTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        blockingIoOperation();
                    }
                }
            });
        }
    }

    public static void blockingIoOperation() {
        System.out.println("Execution a blocking task: " + Thread.currentThread());
        try {
            Thread.sleep(10);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException();
        }
    }
}

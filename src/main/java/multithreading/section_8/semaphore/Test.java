package multithreading.section_8.semaphore;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String [] args) throws InterruptedException {
        int numberOfThreads = 3; //or any number you'd like

        List<Thread> threads = new ArrayList<>();

        Barrier barrier = new Barrier(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
        }

        for(Thread thread: threads) {
            thread.start();
        }
    }
}

package multithreading.section_11.virtual_threads;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadDemo {
    private static final int NUMBER_OD_VIRTUAL_THREAD = 1000;
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> System.out.println("Inside thread: "+Thread.currentThread());

        List<Thread> virtualThreads = new ArrayList<>();

        for (int i = 0; i < NUMBER_OD_VIRTUAL_THREAD; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(runnable);
            virtualThreads.add(virtualThread);
        }

        virtualThreads.forEach(Thread::start);
        virtualThreads.forEach(virtualThread -> {
            try {
                virtualThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}

package multithreading.section_11.virtual_threads;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadDemoUpdate {
    private static final int NUMBER_OD_VIRTUAL_THREAD = 100;
    public static void main(String[] args) throws InterruptedException {

        List<Thread> virtualThreads = new ArrayList<>();

        for (int i = 0; i < NUMBER_OD_VIRTUAL_THREAD; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(new BlockingTask());
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

    private static class BlockingTask implements Runnable{

        @Override
        public void run() {
            System.out.println("Inside thread: "+Thread.currentThread()+" before blocking call");
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException();
            }
            System.out.println("Inside thread: "+Thread.currentThread()+" after blocking call");

        }
    }
}

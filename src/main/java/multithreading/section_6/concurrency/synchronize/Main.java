package multithreading.section_6.concurrency.synchronize;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        InventoryCounter inventoryCounter = new InventoryCounter();

        List<Thread> threds = new ArrayList<>();

        IncementingThread incementingThread= new IncementingThread(inventoryCounter);
        DecementingThread decementingThread= new DecementingThread(inventoryCounter);

        threds.add(incementingThread);
        threds.add(decementingThread);

        threds.forEach(Thread::start);

        threds.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        incementingThread.start();
//        incementingThread.join();
//
//        decementingThread.start();
//        decementingThread.join();

        System.out.println(String.format("We currently have %d items", inventoryCounter.getItems()));

    }

    public static class IncementingThread extends Thread{
        private InventoryCounter inventoryCounter;

        public IncementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i=0; i<10000; i++){
                inventoryCounter.increment();
            }
        }
    }

    public static class DecementingThread extends Thread{
        private InventoryCounter inventoryCounter;

        public DecementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i=0; i<10000; i++){
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter{
        private int items = 0;

        final Object lock = new Object();
        public void increment(){
            synchronized (this.lock){
                items++;
            }
        }

        public void decrement(){
            synchronized (this.lock){
                items--;
            }
        }

        public int getItems() {
            synchronized (this.lock){
                return items;
            }
        }
    }
}

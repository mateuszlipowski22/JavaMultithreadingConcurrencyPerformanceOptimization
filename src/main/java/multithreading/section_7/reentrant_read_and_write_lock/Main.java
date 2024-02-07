package multithreading.section_7.reentrant_read_and_write_lock;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static final int HIGHEST_PRICE=1000;
    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();
        Random random = new Random();

        for(int i=0; i<10000;i++){
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(()->{
           while (true){
               inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
               inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));

               try{
                   Thread.sleep(10);
               }catch (InterruptedException exception){
               }
           }
        });

        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readersThread = new ArrayList<>();

        for(int i = 0 ; i<numberOfReaderThreads;i++){
            Thread thread = new Thread(()->{
                for(int j=0; j<10000;j++){
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice>0 ? random.nextInt(upperBoundPrice) : 0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice,upperBoundPrice);
                }
            });
            thread.setDaemon(true);
            readersThread.add(thread);
        }

        long startReading = System.currentTimeMillis();
        readersThread.forEach(Thread::start);

        for(Thread thread : readersThread){
            thread.join();
        }
        long endReading = System.currentTimeMillis();

        System.out.println(String.format("Reading took %d ms", endReading-startReading));
    }

    public static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private ReentrantLock reentrantLock = new ReentrantLock();
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = reentrantReadWriteLock.readLock();
        private Lock writeLock = reentrantReadWriteLock.writeLock();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            readLock.lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);

                Integer toKey = priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> rangeOfPrice = priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;

                for (int numberOfItemsForPrice : rangeOfPrice.values()) {
                    sum += numberOfItemsForPrice;
                }
                return sum;
            } finally {
                readLock.unlock();
            }
        }

        public void addItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                priceToCountMap.put(price, numberOfItemsForPrice == null ? 1 : numberOfItemsForPrice + 1);
            } finally {
                writeLock.unlock();
            }
        }

        public void removeItem(int price) {
            readLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(price);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }
            } finally {
                readLock.unlock();
            }
        }
    }
}

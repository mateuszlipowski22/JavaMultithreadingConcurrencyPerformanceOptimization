package multithreading.section_2.threading_fundamentals;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are now in thread "+Thread.currentThread().getName());
                System.out.println("Current thread priority is : "+Thread.currentThread().getPriority());
                throw new RuntimeException("Intentional exception");
            }
        });

        thread.setName("New worker thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread: "+Thread.currentThread().getName()+" before starting new thread");
        thread.start();
        System.out.println("We are in thread: "+Thread.currentThread().getName()+" after starting new thread");
        Thread.sleep(1000);

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread "+t.getName()+" the error is "+e.getMessage());
            }
        });
    }
}
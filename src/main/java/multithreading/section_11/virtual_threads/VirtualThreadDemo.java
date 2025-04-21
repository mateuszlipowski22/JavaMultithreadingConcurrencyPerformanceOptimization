package multithreading.section_11.virtual_threads;

public class VirtualThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> System.out.println("Inside thread: "+Thread.currentThread());

        Thread threadPlatform = new Thread(runnable);
        threadPlatform.start();
        threadPlatform.join();
    }
}

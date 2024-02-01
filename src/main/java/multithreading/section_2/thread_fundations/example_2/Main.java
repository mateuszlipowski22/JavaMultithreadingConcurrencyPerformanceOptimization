package multithreading.section_2.thread_fundations.example_2;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from "+Thread.currentThread().getName());
            }
        });

        thread.start();

        Thread thread1 = new NewThread();
        thread1.start();
    }

    private static class NewThread extends Thread{
        @Override
        public void run() {
            System.out.println("Hello from "+this.currentThread().getName());
        }
    }
}

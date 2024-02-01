package multithreading.section_2.example;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from "+Thread.currentThread().getName());
            }
        });
        thread.start();
    }
    public static class NewThread extends Thread{
        @Override
        public void run() {
            super.run();
            System.out.println("Hello from "+Thread.currentThread().getName());
        }
    }
}

package multithreading.section_6.concurrency.locking_strategies;

public class MainTest {
    public static void main(String[] args) {
        Test test = new Test();
        ThreadFiveSecondsTest threadFiveSecondsTest = new ThreadFiveSecondsTest(test);
        ThreadThreeSecondsTest threadThreeSecondsTest = new ThreadThreeSecondsTest(test);

        threadFiveSecondsTest.start();
        threadThreeSecondsTest.start();

    }

    public static class ThreadThreeSecondsTest extends Thread {
        Test test;

        public ThreadThreeSecondsTest(Test test) {
            this.test = test;
        }

        @Override
        public void run() {
            test.waitThreeeSeconds();
        }
    }

    public static class ThreadFiveSecondsTest extends Thread {
        Test test;

        public ThreadFiveSecondsTest(Test test) {
            this.test = test;
        }

        @Override
        public void run() {
            test.waitFiveSeconds();
        }
    }

    public static class Test {

        public synchronized void waitFiveSeconds() {
            try {
                Thread.sleep(5000);
                System.out.println("Mineło 5 sekund " + Thread.currentThread().getName());
            } catch (InterruptedException e) {

            }
        }

        public void waitThreeeSeconds() {
            try {
                Thread.sleep(3000);
                System.out.println("Mineło 3 sekund " + Thread.currentThread().getName());
            } catch (InterruptedException e) {

            }
        }

    }
}

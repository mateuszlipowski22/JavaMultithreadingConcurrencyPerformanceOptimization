package multithreading.section_3.thread_termination.long_computation;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new LongComputation(new BigInteger("200000"), new BigInteger("10000000")));

        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }

    private static class LongComputation implements Runnable{
        private BigInteger power;
        private BigInteger base;

        public LongComputation(BigInteger base,BigInteger power) {
            this.base = base;
            this.power = power;
        }

        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO ; i.compareTo(power)!=0; i=i.add(BigInteger.ONE)){
//                if(Thread.currentThread().isInterrupted()){
//                    System.out.println("Prematurely interupted computatuin");
//                    return BigInteger.ZERO;
//                }
                result=result.multiply(base);
            }

            return result;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+" = "+pow(base,power));
        }
    }


}

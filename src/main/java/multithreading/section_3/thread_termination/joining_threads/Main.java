package multithreading.section_3.thread_termination.joining_threads;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<Long> inputNumber = Arrays.asList(0L,3435L, 35435L, 2324L, 4656L, 23L, 2345L, 5566L);

        List<FactorialThread> threads = new ArrayList<>();

        inputNumber.forEach(number->threads.add(new FactorialThread(number)));

        threads.forEach(factorialThread1 -> {
            factorialThread1.setDaemon(true);
            factorialThread1.start();
        });

        threads.forEach(thread -> {
            try {
                thread.join(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        for(int i=0;i<inputNumber.size();i++){
            FactorialThread factorialThread = threads.get(i);

            if(factorialThread.isFinished()){
                System.out.println(String.format("Factorial of %s is %s %n",inputNumber.get(i), factorialThread.getResult()));
            }else {
                System.out.println(String.format("The calculation for %s is still in progress %n",inputNumber.get(i)));
            }
        }

    }

    private static class FactorialThread extends Thread{
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished=false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        private BigInteger factorial(long n){
            BigInteger tempResult = BigInteger.ONE;

            for(long i = n ; i>0; i--){
                tempResult=tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }

        @Override
        public void run() {
            this.result=factorial(inputNumber);
            this.isFinished=true;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}

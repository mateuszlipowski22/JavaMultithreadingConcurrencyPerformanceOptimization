package multithreading.section_9.atomic_references;

import multithreading.section_9.atomic_references.lock_free_stack.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class Stack {
    public static void main(String[] args) throws InterruptedException {
//        StandardStack<Integer> stack = new StandardStack<>();
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        Random random = new Random(1000);

        for(int i =0; i<10000; i++){
            stack.push(random.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        int pushingThreads = 2;
        int poppingThreads = 2;

        for (int i = 0; i < pushingThreads ; i++){
            Thread thread = new Thread(()->{
                while (true){
                    stack.push(random.nextInt());
                }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        for (int i = 0; i < poppingThreads ; i++){
            Thread thread = new Thread(()->{
                while (true){
                    stack.pop();
                }
            });

            thread.setDaemon(true);
            threads.add(thread);
        }

        threads.forEach(Thread::start);

        Thread.sleep(10000);

        System.out.println(String.format("%,d operations were performed in 10 seconds ", stack.getCounter()));
    }

    public static class LockFreeStack<T>{
        private AtomicReference<Main.StackNode<T>> head = new AtomicReference<>();
        private AtomicInteger counter = new AtomicInteger(0);

        public synchronized void push (T value){
            Main.StackNode<T> newHeadNode = new Main.StackNode<>(value);

            while(true){
                Main.StackNode<T> currentHeadNode = head.get();
                newHeadNode.next=currentHeadNode;
                if(head.compareAndSet(currentHeadNode,newHeadNode)){
                    break;
                }else {
                    LockSupport.parkNanos(1);
                }
            }

            counter.incrementAndGet();
        }

        public synchronized T pop (){
            Main.StackNode<T> currentHeadNode = head.get();
            Main.StackNode<T> newHeadNode;

            while (currentHeadNode.next!=null){
                newHeadNode=currentHeadNode.next;
                if(head.compareAndSet(currentHeadNode,newHeadNode)){
                    break;
                }else {
                    LockSupport.parkNanos(1);
                    currentHeadNode = head.get();
                }
            }

            counter.incrementAndGet();
            return currentHeadNode !=null ? currentHeadNode.value : null;
        }

        public int getCounter(){
            return counter.get();
        }
    }
    public static class StandardStack<T>{
        private StackNode<T> head;
        private int counter = 0;

        public synchronized void push (T value){
            StackNode<T> newHead = new StackNode<>(value);
            newHead.next=head;
            head=newHead;
            counter++;
        }

        public synchronized T pop (){
            if(head==null){
                counter++;
                return null;
            }

            T value = head.value;
            head = head.next;
            counter++;
            return value;
        }

        public int getCounter(){
            return counter;
        }
    }

    public static class StackNode<T>{
        public T value;
        public StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
            this.next = next;
        }
    }
}

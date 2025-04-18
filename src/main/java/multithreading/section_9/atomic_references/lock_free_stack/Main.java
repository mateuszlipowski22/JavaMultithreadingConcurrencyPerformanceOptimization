package multithreading.section_9.atomic_references.lock_free_stack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class Main {
    public static void main(String[] args) {
      
    }

    public static class LockFreeStack<T>{
        private AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private AtomicInteger counter = new AtomicInteger(0);

        public synchronized void push (T value){
            StackNode<T> newHeadNode = new StackNode<>(value);

            while(true){
                StackNode<T> currentHeadNode = head.get();
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
            StackNode<T> currentHeadNode = head.get();
            StackNode<T> newHeadNode;

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

    public static class StackNode<T>{
        public T value;
        public StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
            this.next = next;
        }
    }
}

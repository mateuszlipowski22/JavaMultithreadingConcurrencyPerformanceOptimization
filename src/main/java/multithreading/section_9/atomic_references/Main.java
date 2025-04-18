package multithreading.section_9.atomic_references;

import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        String oldName = "old name";
        String newName = "new name";

        AtomicReference<String> atomicReference = new AtomicReference<>(oldName);

        if(atomicReference.compareAndSet(oldName,newName)){
            System.out.println("New value is "+atomicReference.get());
        }else {
            System.out.println("Nothing change");
        }
    }
}

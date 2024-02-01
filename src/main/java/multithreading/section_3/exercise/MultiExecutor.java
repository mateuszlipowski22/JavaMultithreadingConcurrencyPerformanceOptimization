package multithreading.section_3.exercise;

import java.util.List;

public class MultiExecutor {

    // Add any necessary member variables here
    private List<Runnable> tasks;
    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        this.tasks=tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        tasks.forEach(task->{
            Thread thread = new Thread(task);
            thread.start();
        });
    }
}
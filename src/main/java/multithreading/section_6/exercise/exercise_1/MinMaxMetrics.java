package multithreading.section_6.exercise.exercise_1;

public class MinMaxMetrics {
    private volatile long min;
    private volatile long max;

    public MinMaxMetrics() {
        min=Long.MAX_VALUE;
        max=Long.MIN_VALUE;
    }

    public void addSample(long newSample) {
        synchronized (this){
            this.min = Math.min(min, newSample);
            this.max = Math.max(max, newSample);
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}

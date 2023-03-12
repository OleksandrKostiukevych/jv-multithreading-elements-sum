package core.basesyntax;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int endPoint;

    public MyTask(int startPoint, int endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    protected Long compute() {
        long result = 0;
        if (endPoint - startPoint > 10) {
            List<RecursiveTask<Long>> subTasks = createSubTasks();
            for (RecursiveTask<Long> subTask : subTasks) {
                subTask.fork();
                result += subTask.join();
            }
            return result;
        } else {
            return LongStream.range(startPoint, endPoint).sum();
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        int nextPoint = startPoint + (endPoint - startPoint) / 2;
        return List.of(new MyTask(startPoint, nextPoint),
                new MyTask(nextPoint, endPoint));
    }
}

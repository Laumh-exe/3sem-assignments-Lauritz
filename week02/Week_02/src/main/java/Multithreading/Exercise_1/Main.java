package Multithreading.Exercise_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        //CREATE CHAR ARRAY FOR TASK
        char[] chars = {'A', 'B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

        //CREATE THREADPOOL
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Task> taskList = new ArrayList<>();

        // CREATE TASKS
        for(int i = 0; i < chars.length; i++) {
            taskList.add(new Task(i,chars));
        }

        //EXECUTE TASKS AND SAVE FUTURES
        List<Future<String>> futures = null;
        try {
            futures = executor.invokeAll(taskList);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // PRINT OUT RESULT
        try {
            for(Future<String> future : futures) {
                System.out.println(future.get());
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}

//CREATE TASK
class Task implements Callable<String> {
    int i;
    char[] chars;

    public Task(int i, char[] chars) {
        this.i = i;
        this.chars = chars;
    }

    @Override
    public String call() {
        String string = String.valueOf(chars[i]);
        return string+string+string;
    }
}

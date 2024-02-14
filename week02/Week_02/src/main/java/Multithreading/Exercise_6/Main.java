package Multithreading.Exercise_6;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Collection<Callable<String>> callables = new ArrayList<>();
        callables.add(new TaskDadJoke(1));
        callables.add(new TaskChuckNorris(2));
        callables.add(new TaskKanye(3));
        callables.add(new TaskTrump(4));
        callables.add(new TaskSpaceX(5));

        List<Future<String>> futures;

        try {
            futures = service.invokeAll(callables);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Future future : futures) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        service.shutdown();

    }
}

class TaskDadJoke implements Callable {
    int count;

    public TaskDadJoke(int count) {
        this.count=count;
    }

    @Override
    public String call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(1000,2000));
        System.out.println("in thread" + count);
        return ApiMapper.getDadJoke();
    }
}
class TaskChuckNorris implements Callable {
    int count;

    public TaskChuckNorris(int count) {
        this.count=count;
    }

    @Override
    public String call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(200,2000));
        System.out.println("in thread" + count);
        return ApiMapper.getChuck();
    }
}

class TaskKanye implements Callable {
    int count;

    public TaskKanye(int count) {
        this.count=count;
    }


    @Override
    public String call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(200,2000));
        System.out.println("in thread " + count);
        return ApiMapper.getKanye();
    }
}

class TaskTrump implements Callable {
    int count;

    public TaskTrump(int count) {
        this.count=count;
    }


    @Override
    public String call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(200,2000));
        System.out.println("in thread " + count);
        return ApiMapper.getTrump();
    }
}
class TaskSpaceX implements Callable {
    int count;

    public TaskSpaceX(int count) {
        this.count=count;
    }


    @Override
    public String call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(200,2000));
        System.out.println("in thread " + count);
        return ApiMapper.getSpaceX();
    }
}

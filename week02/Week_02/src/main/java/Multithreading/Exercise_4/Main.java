package Multithreading.Exercise_4;

import okhttp3.Call;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        // I have 16 cores- previous execise uses 17, so all cores were used. This one uses all of them too.
        ExecutorService executor = Executors.newFixedThreadPool(16);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            futures.add(executor.submit(new CallableTask(i)));
        }
        for (Future<String> future: futures) {
            try {
                System.out.println(future.get());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

    }

    public static class CallableTask implements Callable {
        private int count;

        public CallableTask(int count) {
            this.count=count;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "All work and no play makes Jack a dull boy"+" : "+"Task: "+count;
        }
    }
}



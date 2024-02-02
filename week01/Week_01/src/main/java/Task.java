import java.util.Random;

public class Task {
    void run() {
        Random rnd = new Random();
        System.out.println(rnd.nextDouble(1,100)* rnd.nextDouble(10,20)/2);
        try {
            Thread.sleep(1000); // Simulate 1 second of work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

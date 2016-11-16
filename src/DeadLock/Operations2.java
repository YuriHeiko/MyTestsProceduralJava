package DeadLock;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Operations2 {
    static Random rnd = new Random();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(1);

        final Account a = new Account(1000);
        final Account b = new Account(2000);
        List<Future<Boolean>> list = new LinkedList<>();

        ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

        scheduledService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("\t\t Fails A: " + a.getFailCounter());
                System.out.println("\t\t Fails B: " + b.getFailCounter());
            }
        }, 3, 3,TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {
            list.add(service.submit(
                    new Transfer(a, b, rnd.nextInt(500), latch))
            );
            list.add(service.submit(
                    new Transfer(b, a, rnd.nextInt(500), latch))
            );
        }
        Thread.sleep(10000);
        latch.countDown();
        service.shutdown();
        service.awaitTermination(100000, TimeUnit.SECONDS);
        scheduledService.shutdownNow();

        System.out.println();
        for (Future<Boolean> f : list) {
            try {
                System.out.print(f + " result is ");
                Thread.sleep(100);
                System.err.println(f.get());
                Thread.sleep(100);
                System.out.println("******************************************");
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        Thread.sleep(100);
        System.out.print("Account A: ");
        Thread.sleep(100);
        System.err.println(a.getBalance() + " [" + a.getFailCounter() + "]");
        Thread.sleep(100);
        System.out.print("Account B: ");
        Thread.sleep(100);
        System.err.println(b.getBalance() + " [" + a.getFailCounter() + "]");
    }
}

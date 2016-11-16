import java.util.concurrent.atomic.AtomicInteger;

public class testConcur {
    public static void main(String[] args) {
        final int limitCnt = 200000000; // Пороговое значение
        SynchronizedCounter counter1 = new SynchronizedCounter(); // тестируемый образец 1
        AtomicInteger counter2 = new AtomicInteger(1);  // тестируемый образец 2
        long startTime = System.currentTimeMillis();    // Время начала

        Runnable myTaskIncSynchr = () -> {
            for (int i = 0; i < limitCnt; i++) counter1.increment();
            System.out.println("stop synchr inc, time=" + (System.currentTimeMillis() - startTime) + " ms." );
        };

        Runnable myTaskDecSynchr = () -> {
            for (int i = 0; i < limitCnt; i++) counter1.decrement();
            System.out.println("stop synchr dec, time=" + (System.currentTimeMillis() - startTime) + " ms." );
        };

        Runnable myTaskIncAtomic = () -> {
            for (int i = 0; i < limitCnt; i++) counter2.getAndIncrement();
            System.out.println("stop atomic inc, time=" + (System.currentTimeMillis() - startTime) + " ms." );
        };

        Runnable myTaskDecAtomic = () -> {
            for (int i = 0; i < limitCnt; i++) counter2.getAndDecrement();
            System.out.println("stop atomic dec, time=" + (System.currentTimeMillis() - startTime) + " ms." );
        };

        new Thread(myTaskIncSynchr).start();
        new Thread(myTaskDecSynchr).start();
        new Thread(myTaskIncAtomic).start();
        new Thread(myTaskDecAtomic).start();
    }
}

class SynchronizedCounter {
    private int c = 0;
    synchronized void increment() { c++; }
    synchronized void decrement() { c--; }
    synchronized int value() { return c; }
}
package SynchroVsCopyAndWrite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Solution {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> sList = Collections.synchronizedList(new ArrayList<Integer>());
        List<Integer> nsList = new CopyOnWriteArrayList<>();
        {
            for (int i = 0; i < 100; i++) {
                sList.add(i);
                nsList.add(i);
            }
        }

        System.out.println("List synchronized: ");
        checkList(sList);

        System.out.println("Copy on write array list: ");
        checkList(nsList);
    }

    public static void checkList(List<Integer> list) throws ExecutionException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        ExecutorService ex = Executors.newFixedThreadPool(2);
        Future<Long> f1 = ex.submit(new ListRunner(0, 50, list, latch));
        Future<Long> f2 = ex.submit(new ListRunner(50, 100, list, latch));

        latch.countDown();
        System.out.println("Thread 1: " + f1.get() / 1000);
        System.out.println("Thread 1: " + f2.get() / 1000);
        ex.shutdownNow();
    }

    private static class ListRunner implements Callable<Long> {
        int start, end;
        CountDownLatch latch;
        List<Integer> list;

        public Long call() throws Exception {
            latch.await();

            long starTime = System.nanoTime();
            for (int i = start; i < end; i++) {
                if (i%10 == 0)
                    list.add(1000);
                list.get(i);
            }

            return System.nanoTime() - starTime;
        }

        public ListRunner(int start, int end, List<Integer> list, CountDownLatch latch) {
            this.start = start;
            this.end = end;
            this.list = list;
            this.latch = latch;
        }
    }
}
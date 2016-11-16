package ThreadStates;

import java.util.HashMap;
import java.util.Map;

/* Мониторинг состояния нити
В отдельном классе создать нить ThreadStates.LoggingStateThread,
которая будет выводить в консоль все состояния (State) переданной в конструктор нити.
Нить ThreadStates.LoggingStateThread должна сама завершаться после остановки переданной в конструктор нити.
Метод main не участвует в тестировании.
*/
public class Solution {
    static HashMap<Thread.State, Long> map = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1_000; i++) {
            Thread target = new Thread() {
                @Override
                public void run() {
                    Object o = new Object();
                    while (true) {
                        try {
                            synchronized (o) {
                                o.wait(100);
                            }
                            sleep(100);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            LoggingStateThread loggingStateThread = new LoggingStateThread(target);
//          loggingStateThread.setDaemon(true);
            loggingStateThread.start();
            Thread.sleep(10);
            target.start();  //NEW
            Thread.sleep(200); //RUNNABLE
            target.interrupted(); //TERMINATED
            Thread.sleep(100); //RUNNABLE
            target.interrupt();
//            target.join();
//            Thread.sleep(10);
//            Thread.sleep(10);
        }

        for (Map.Entry<Thread.State, Long> m: map.entrySet()) {
            System.out.println(m.getKey() + ": " + m.getValue());
        }
    }
}

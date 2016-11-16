package DeadLock;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Transfer implements Callable<Boolean> {
    private Account accountFrom, accountTo;
    int amount;
    static int idCounter = 0;
    private final int ID;
    private CountDownLatch latch;

    public Transfer(Account accountFrom, Account accountTo, int amount, CountDownLatch latch) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.latch = latch;
        ID = ++idCounter;
    }

    @Override
    public Boolean call() throws Exception {
        final long WAIT_SEC = new Random().nextInt(10);

        System.out.println("WAITING FOR START !!!");
        latch.await();

        if (accountFrom.getL().tryLock(WAIT_SEC, TimeUnit.SECONDS)){
            try {
                if (accountFrom.getBalance() < amount)
                    throw new InsufficientFundsException();

                System.out.println("Account 1 is locked by " + Thread.currentThread().getName());

                if (accountTo.getL().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Account 2 is locked by " + Thread.currentThread().getName());
                        accountFrom.withdraw(amount);
                        accountTo.deposit(amount);
                        Thread.sleep(new Random().nextInt(5000));
                    } finally {
                        accountTo.getL().unlock();
                    }

                } else {
                    accountTo.incFailedTransferCount();
                    System.err.println("Account 2 can't be locked by " + Thread.currentThread().getName() + "!");
                    return false;
                }

            } finally {
                accountFrom.getL().unlock();
            }

        } else {
            accountFrom.incFailedTransferCount();
            System.err.println("Account 1 can't be locked by " + Thread.currentThread().getName() + "!");
            return false;
        }
        Thread.sleep(10);
        System.out.println("---");
        System.out.println("Transfer successful by " + Thread.currentThread().getName());
        System.out.println("---");
        return true;
    }
}

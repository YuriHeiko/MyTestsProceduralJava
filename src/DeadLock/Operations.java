package DeadLock;

import java.util.concurrent.TimeUnit;

public class Operations {
    public static void main(String[] args) throws InsufficientFundsException, InterruptedException {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    transfer(a, b, 500);
                }
                catch (InsufficientFundsException e) {}
                catch (InterruptedException t) {}
            }
        }).start();

        transfer(b, a, 300);
    }

    private static void transfer(Account acc1, Account acc2, int amount) throws InsufficientFundsException, InterruptedException {
/*        final long WAIT_SEC = 1;

        if (acc1.getBalance() < amount)
            throw new InsufficientFundsException();

        if (acc1.getL().tryLock(WAIT_SEC, TimeUnit.SECONDS)){
            try {
                System.out.println("Account 1 is locked by " + Thread.currentThread().getName());
                Thread.sleep(1000);
                if (acc2.getL().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Account 2 is locked by " + Thread.currentThread().getName());
                        acc1.withdraw(amount);
                        acc2.deposit(amount);
                    } finally {
                        acc2.getL().unlock();
                    }
                } else {
                    acc2.incFailedTransferCount();
                }
            } finally {
                acc1.getL().unlock();
            }
        } else {
            acc1.incFailedTransferCount();
            System.err.println("Account 1 can't be locked!");
            return;
        }
        System.out.println("Transfer successful");*/
    }
}

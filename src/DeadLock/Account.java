package DeadLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int balance;
    private Lock l = new ReentrantLock();
    private AtomicInteger failCounter = new AtomicInteger(0);

    public Lock getL() {
        return l;
    }

    public int getBalance() {
        return balance;
    }

    public Account(int initialBalance) {
        this.balance = initialBalance;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public AtomicInteger getFailCounter() {
        return failCounter;
    }

    public void incFailedTransferCount() {
        failCounter.addAndGet(1);
    }
}

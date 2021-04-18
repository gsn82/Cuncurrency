package lesson90;

public class Account {

    private int amount1;
    private int amount2;

    private final Object monitor1 = new Object();
    private final Object monitor2 = new Object();


    public Account(int amount1, int amount2) {
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    public void transferFrom1To2(int amount) {
        synchronized (monitor1) {
            // задерка
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // проверяем количество денег на 1 счете с суммой , которой надо перевести
            if (amount <= amount1) {
                System.out.println("amount <= amount1");
                // добавляем деньги на второй счет
                synchronized (monitor2) {
                    // задерка
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    amount1 -= amount;
                    amount2 += amount;
                }
            } else {
                System.out.println("Insufficient funds");
            }
        }
    }

    public void transferFrom2To1(int amount) {
        synchronized (monitor2) {
            // задерка
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // проверяем количество денег на 2 счете с суммой , которой надо перевести
            if (amount <= amount2) {
                System.out.println("amount <= amount2");
                // добавляем деньги на второй счет
                synchronized (monitor1) {
                    // задерка
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    amount2 -= amount;
                    amount1 += amount;
                }
            } else {
                System.out.println("Insufficient funds");
            }
        }
    }


}

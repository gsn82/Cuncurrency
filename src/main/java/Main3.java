import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main3 {
    public static void main(String[] args) {
        // счетчик на 3 потока
        CountDownLatch countDownLatch = new CountDownLatch(3);
      //  ExecutorService executorService = Executors.newSingleThreadExecutor();
       ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                // сумма всех четных чисел до 1000000
                long sum = 0;
                for (int i = 0; i < 1000000; i++) {
                    if (i % 2 == 0) {
                        sum += i;
                    }
                }
                System.out.println("Task1: " + sum);
                countDownLatch.countDown();
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                // сумма всех  чисел которые кратны 7 до 1000000
                long sum = 0;
                for (int i = 0; i < 1000000; i++) {
                    if (i % 7 == 0) {
                        sum += i;
                    }
                }
                System.out.println("Task2: " + sum);
                countDownLatch.countDown();
            }
        };
        Runnable task3 = new Runnable() {
            @Override
            public void run() {
                //создаем коллекция и считаем количество  четных чисел

                List<Integer> numbers = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    numbers.add((int) (Math.random() * 1000));
                }
                int count = 0;
                for (int i : numbers) {
                    if (i % 2 == 0) {
                        count++;
                    }
                }
                System.out.println("Task3: " + count);
                countDownLatch.countDown();
            }
        };
        long before = System.currentTimeMillis();
        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task3);
        executorService.shutdown();
        try {
            countDownLatch.await();
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }
}

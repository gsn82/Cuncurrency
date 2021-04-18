package lesson92;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(100);

                            numbers.add(i);

                    }
                    // по завершения метода , уменьшаем счетчика
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 100; i < 200; i++) {
                        Thread.sleep(100);


                            numbers.add(i);

                    }
                    // по завершения метода , уменьшаем счетчика
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // ждем завершения обоих потоков
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // выводим размер коллекции
        System.out.println(numbers.size());

        for (int number : numbers) {
            System.out.print(number+" ");
        }
    }
}

package Lesson96;

import java.util.concurrent.CyclicBarrier;

public class Main2 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // случайным образаом задачем , время задержки
                    long millis = (long)(Math.random() * 5000 + 1000);
                    // имя потока
                    String name = Thread.currentThread().getName();
                    System.out.println(name + ": Data is being prepared");
                    try {
                        Thread.sleep(millis);
                        System.out.println(name + ": Data is ready");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        cyclicBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + ": Continue work");
                }
            }).start();
        }
    }
}

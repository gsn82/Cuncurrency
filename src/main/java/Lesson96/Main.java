package Lesson96;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(3);
        for (int i=0; i<10; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    System.out.println(name + " started working.");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        // уменьшаем счетчик
                        semaphore.acquire();
                        workWithFileSystem();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // увеличиваеем счетчик
                        semaphore.release();
                    }


                    System.out.println(name + " finished working.");
                }
            });

        }

        executorService.shutdown();
    }

    private static void workWithFileSystem(){
        String name = Thread.currentThread().getName();
        System.out.println(name + " start working with file system.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " finish working with file system.");
    }


}

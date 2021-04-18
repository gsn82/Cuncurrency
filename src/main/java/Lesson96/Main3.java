package Lesson96;

import java.util.Map;
import java.util.concurrent.*;

public class Main3{
     // сколько машин еду в тоннели
    private static final int CARS_COUNT_IN_TUNNEL = 3;
    // сколько всего машин
    private static final int CARS_COUNT = 10;
    // констанкта для тунеля
    private static final Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT_IN_TUNNEL);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    // используем для подготовки машины
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);
    // используем для храниея времени проезды машины по дороге
    private static final Map<Integer, Long> score = new ConcurrentHashMap<>();
    private static final CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);

    private static int winnerIndex = -1;
    // заводим монитор, для
    private static final Object monitor = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < CARS_COUNT; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // начало подгатовки машины
                    prepare(index);

                    try {

                        // дожидаемся , пока все машины поготовяться
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    // запонмаем начальное время
                    long before = System.currentTimeMillis();
                   // запускаем дорогу
                    roadFirst(index);
                    // запускаем тоннель
                    tunnel(index);
                    // запускаем вторую дорогу
                    roadSecond(index);
                    // завели монитор для потоков
                    // в winnerIndex - сохраняем номер машины , которая первая пришла
                    synchronized (monitor) {
                        if (winnerIndex == -1) {
                            winnerIndex = index;
                        }
                    }
                    // запонимаем конечное время
                    long after = System.currentTimeMillis();
                    // записываем в коллекцию : номер машины и время
                    score.put(index, after - before);
                    // уменьшаем количесвто потоков
                    countDownLatch.countDown();
                }
            });
        }
        // дожидаемся завершения всех потоков , мы сейчас в главном потоке
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // выводим результаты в консоль (пробегаемся по нашей коллекции )
        for (int key : score.keySet()) {
            System.out.println(key + " " + score.get(key));
        }

        // вывод победителя
        System.out.println("Winner: " + winnerIndex + " Time: " + score.get(winnerIndex));
    }
// усыпление на произвольное время
    private static void sleepRandomTime() {
        long millis = (long) (Math.random() * 5000 + 1000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // начало подготовки машины
    private static void prepare(int index) {
        System.out.println(index + " started preparing");
        sleepRandomTime();
        System.out.println(index + " finished preparing");
    }

    private static void roadFirst(int index) {
        System.out.println(index + " started roadFirst");
        sleepRandomTime();
        System.out.println(index + " finished roadFirst");
    }

    private static void roadSecond(int index) {
        System.out.println(index + " started roadSecond");
        sleepRandomTime();
        System.out.println(index + " finished roadSecond");
    }

    private static void tunnel(int index) {
        try {
            // уменьшаем счетчик
            // т.к машина едит в туннели
            tunnelSemaphore.acquire();
            System.out.println(index + " started tunnel");
            sleepRandomTime();
            System.out.println(index + " finished tunnel");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // увеличиваем счетчит
            // т.к машина выехала из туннели
            tunnelSemaphore.release();
        }
    }
}

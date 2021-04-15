package lesson87.HomeWork;

public class Main {

    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";

    private static final Object MONITOR = new Object();
    private static String nextLetter = A;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    for (int i = 0; i < 5; i++) {
                        // если мы должны печатать не строку A, то данный поток засыпает
                        try {
                            while(!nextLetter.equals(A)){
                                MONITOR.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // выводим строку
                        System.out.print(A);
                        // указываем какая следуящая строка
                        nextLetter = B;
                        // будем все потоки
                        MONITOR.notifyAll();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    for (int i = 0; i < 5; i++) {
                        // если мы должны печатать не строку B, то данный поток засыпает
                        try {
                            while(!nextLetter.equals(B)){
                                MONITOR.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // выводим строку
                        System.out.print(B);
                        // указываем какая следуящая строка
                        nextLetter = C;
                        // будем все потоки
                        MONITOR.notifyAll();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    for (int i = 0; i < 5; i++) {
                        // если мы должны печатать не строку C, то данный поток засыпает
                        try {
                            while(!nextLetter.equals(C)){
                                MONITOR.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // выводим строку
                        System.out.print(C);
                        // указываем какая следуящая строка
                        nextLetter = A;
                        // будем все потоки
                        MONITOR.notifyAll();
                    }
                }
            }
        }).start();
    }
}

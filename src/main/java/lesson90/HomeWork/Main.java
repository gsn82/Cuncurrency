package lesson90.HomeWork;

import lesson90.Account;

public class Main {
    public static void main(String[] args) {
        MFP mfp = new MFP();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfp.scan(4);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfp.print(12);
            }
        }).start();


    }
}

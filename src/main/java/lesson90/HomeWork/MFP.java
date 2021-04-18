package lesson90.HomeWork;

public class MFP {

    private final Object printMonitor = new Object();
    private final Object scanMonitor = new Object();

    public void scan(int count) {
        synchronized (scanMonitor) {
            try {
                for (int i = 0; i < count; i++) {
                    Thread.sleep(100);
                    System.out.println("Scan " + (i + 1) + " pages");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void print(int count) {
        synchronized (printMonitor) {
            try {
                for (int i = 0; i < count; i++) {
                    Thread.sleep(100);

                    System.out.println("Print " + (i + 1) + " pages");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

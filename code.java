public class PrintEvenOddTester {

    public static void main(String... args) {
        Printer print = new Printer();
        Thread t1 = new Thread(new TaskThread(print, 100, false));
        Thread t2 = new Thread(new TaskThread(print, 100, true));
        t1.start();
        t2.start();
    }

}

class TaskThread implements Runnable {

    private int max;
    private Printer print;
    private boolean isEvenNumber;

    TaskThread(Printer print, int max, boolean isEvenNumber) {
        this.print = print;
        this.max = max;
        this.isEvenNumber = isEvenNumber;
    }

    @Override
    public void run() {

        int number = isEvenNumber == true ? 2 : 1;
        while (number <= max) {

            if (isEvenNumber) {
                print.thread2(number);
            } else {
                print.thread1(number);
            }
            number += 2;
        }

    }

}

class Printer {

    boolean isOdd = false;

    synchronized void thread2(int number) {

        while (isOdd == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread 2:" + number);
        isOdd = false;
        notifyAll();
    }

    synchronized void thread1(int number) {
        while (isOdd == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread 1:" + number);
        isOdd = true;
        notifyAll();
    }

}
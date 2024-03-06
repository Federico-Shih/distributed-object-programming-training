package ar.edu.itba.pod.concurrency.exercises.e1;

import java.util.concurrent.Executors;

public class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from thread");
    }

    public static void main(String[] args) {
        Thread thread = new HelloThread();
        System.out.println("hola");
        thread.start();
        System.out.println("chau");
    }
}

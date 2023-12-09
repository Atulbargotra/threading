package com.atul;

import java.util.concurrent.*;

public class ExecutorServiceTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                0L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        long curr = System.currentTimeMillis();
        for(int i=0;i<100;i++) {
            service.execute(new Task());
        }
        service.shutdown();
        long after = System.currentTimeMillis();
        System.out.println("Finished Calling all Threads in "+ (after - curr));
        try {
            if (service.awaitTermination(20, TimeUnit.SECONDS)) {
                long exitTime = System.currentTimeMillis();
                System.out.println("All tasks completed. Exiting..."+ (exitTime - curr));
                System.exit(0);
            } else {
                System.out.println("Timeout waiting for tasks to complete. Exiting...");
                System.exit(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("IN Thread : "+Thread.currentThread().getName());
            Thread.sleep(10000);
            System.out.println("Out of Thread : "+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

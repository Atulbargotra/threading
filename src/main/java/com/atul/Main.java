package com.atul;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
                throw new RuntimeException("A");
            }
        });

        thread.setUncaughtExceptionHandler((Thread t, Throwable th) -> {
            System.out.println("IN");
            System.out.println(t.getName() + th.getMessage());

        });

        thread.start();

//        Executor executor = Executors.newFixedThreadPool(4);
//        executor.execute(thread);





    }
}
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


public class ThreadPool {
    // 计算斐波那契数列的函数
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) throws InterruptedException {
        // 设置不同的线程池大小进行实验
        int[] poolSizes = {1, 2, 4, 8,16,32,64,128}; // 测试不同的线程池大小
        int tasksCount = 10; // 任务数量，计算10次，计算密集型任务
        int fibonacciNumber = 35; // 计算斐波那契数列的第35项

        for (int poolSize : poolSizes) {
            ExecutorService executor = Executors.newFixedThreadPool(poolSize); //开设线程池
            long startTime = System.currentTimeMillis(); //记录开始时间

            // 提交多个任务到线程池，对于当前线程池大小，开设10个任务，每个任务均为计算斐波那契数列第35位
            for (int i = 0; i < tasksCount; i++) {
                executor.submit(() -> {
                    int result = fibonacci(fibonacciNumber);
                    System.out.println("斐波那契数列的第" + fibonacciNumber + "项是：" + result);
                });
            }

            executor.shutdown();//关闭线程池，不再接收新的任务
            //注意，务必要求线程池中所有任务执行完毕再继续后续代码，否则截止时间可能会在任务完成之前。
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // 等待所有任务完成

            long endTime = System.currentTimeMillis();
            System.out.println("线程池大小：" + poolSize + "，执行时间：" + (endTime - startTime) + " 毫秒");
        }
    }
}

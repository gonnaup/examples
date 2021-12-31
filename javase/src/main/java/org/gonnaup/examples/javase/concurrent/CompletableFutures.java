package org.gonnaup.examples.javase.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.gonnaup.examples.javase.CommonUtil.currentThreadName;
import static org.gonnaup.examples.javase.CommonUtil.sleep;

/**
 * @author gonnaup
 * @version created at 2021/12/31 12:21
 */
@Slf4j
public class CompletableFutures implements AutoCloseable {

    private final ThreadPoolExecutor threadPool;

    private CompletableFutures() {
        threadPool = new ThreadPoolExecutor(4, 8, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(20), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static CompletableFutures begin() {
        return new CompletableFutures();
    }

    @Override
    public void close() {
        threadPool.shutdown();
    }


    /**
     * ---------------- methods -----------------
     **/

    public CompletableFutures commonMethods() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> log.info("{} runAsync...", currentThreadName()), threadPool)
                /**
                 *  "Async"指使用另一个线程执行代码，并不是两段同时执行使用指定线程池线程，否则使用默认的ForkjoinPool
                 *  同时执行使用
                 *  {@link CompletableFuture#thenCompose(Function)}: 将前一个任务的返回结果作为下一个任务的参数，存在着先后顺序
                 *  {@link CompletableFuture#thenCombine(CompletionStage, BiFunction)}: 两个任务都执行完成后将结果合并。两个任务并行执行，五先后顺序
                 **/
                .thenApplyAsync(unused -> {
                    log.info("{} 获取文件内容...", currentThreadName());
                    sleep(2, TimeUnit.SECONDS);
                    return "hello world";
                }, threadPool)
                .thenCompose(s -> {
                    log.info("{} 修饰文件内容...", currentThreadName());
                    return CompletableFuture.completedFuture(s + '!');
                })
                .thenCombine(CompletableFuture.supplyAsync(() -> {
                    log.info("{} 获取另一个文件内容", currentThreadName());
                    sleep(2, TimeUnit.SECONDS);
                    return "你好";
                }, threadPool), (s, s2) -> s + " => " + s2)
                //使用上个任务的线程执行
                .thenAccept(s -> {
                    s = s.toUpperCase(Locale.ROOT);
                    log.info("{} 处理文件内容后结果 {}", currentThreadName(), s);
                });
        log.info("使用 CompletableFuture处理文件...");
        future.get();
        log.info("文件处理完毕...");
        return this;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutures.begin().commonMethods().close();
    }
}

/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;

/**
 * An object that executes submitted {@link Runnable} tasks. This
 一个执行提交的任务（实现了Runable接口）的类
 * interface provides a way of decoupling task submission from the
 * mechanics of how each task will be run, including details of thread
 * use, scheduling, etc.  An <tt>Executor</tt> is normally used
 提供了线程使用，定时执行等功能。
 * instead of explicitly creating threads. For example, rather than
 * invoking <tt>new Thread(new(RunnableTask())).start()</tt> for each
 * of a set of tasks, you might use:
 可以不用每次创建线程来执行任务：如：new Thread(new(RunnableTask())).start()

 *
 * <pre>
 * Executor executor = <em>anExecutor</em>;
 * executor.execute(new RunnableTask1());
 * executor.execute(new RunnableTask2());
 * ...
 * </pre>
 *
 * However, the <tt>Executor</tt> interface does not strictly
 * require that execution be asynchronous. In the simplest case, an
 不严格要求执行必须是异步的。
 * executor can run the submitted task immediately in the caller's
 * thread:
 *
 * <pre>
 * class DirectExecutor implements Executor {
 *     public void execute(Runnable r) {
 *         r.run();
 *     }
 * }</pre>
 *简单例子中，一个executor可以直接在调用者的线程里执行提交的任务：
 * More typically, tasks are executed in some thread other
 * than the caller's thread.  The executor below spawns a new thread
 * for each task.
 *更典型的使用是任务可以在其他线程而不是调用者线程中执行。
 * <pre>
 * class ThreadPerTaskExecutor implements Executor {
 *     public void execute(Runnable r) {
 *         new Thread(r).start();
 *     }
 * }</pre>
 *
 * Many <tt>Executor</tt> implementations impose some sort of
 * limitation on how and when tasks are scheduled.  The executor below
 * serializes the submission of tasks to a second executor,
 * illustrating a composite executor.
 *许多Executor实例添加了一些如何和何时调度任务的限制，下面的将任务提交给第二个executor
 *  <pre> {@code
 * class SerialExecutor implements Executor {
 *   final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
 *   final Executor executor;
 *   Runnable active;
 *
 *   SerialExecutor(Executor executor) {
 *     this.executor = executor;
 *   }
 *
 *   public synchronized void execute(final Runnable r) {
 *     tasks.offer(new Runnable() {
 *       public void run() {
 *         try {
 *           r.run();
 *         } finally {
 *           scheduleNext();
 *         }
 *       }
 *     });
 *     if (active == null) {
 *       scheduleNext();
 *     }
 *   }
 *
 *   protected synchronized void scheduleNext() {
 *     if ((active = tasks.poll()) != null) {
 *       executor.execute(active);
 *     }
 *   }
 * }}</pre>
 *
 * The <tt>Executor</tt> implementations provided in this package
 * implement {@link ExecutorService}, which is a more extensive
 ExecutorService实现了Executor，更灵活
 * interface.  The {@link ThreadPoolExecutor} class provides an
 ThreadPoolExecutort提供了可扩展的现场池实现。
 * extensible thread pool implementation. The {@link Executors} class
 * provides convenient factory methods for these Executors.
 *Executors类提供了这些Executor实现的方便的工厂方法
 * <p>Memory consistency effects: Actions in a thread prior to
 内存一致性效应
 * submitting a {@code Runnable} object to an {@code Executor}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * its execution begins, perhaps in another thread.
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface Executor {

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the <tt>Executor</tt> implementation.
     *在未来执行给定的任务，任务可能在一个新thread，一个线程池或在调用者线程中执行。由实现者自行决定。
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution.
     * @throws NullPointerException if command is null
     */
    void execute(Runnable command);
}

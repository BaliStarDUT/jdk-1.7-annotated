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
 * A service that decouples the production of new asynchronous tasks 
 * from the consumption of the results of completed tasks.  Producers
 * 一个用于解耦的服务，该服务解耦，消费已经完成任务的结果的异步任务的产生。
 * <tt>submit</tt> tasks for execution. Consumers <tt>take</tt>
 * completed tasks and process their results in the order they
 * complete.  A <tt>CompletionService</tt> can for example be used to
 * Producers提交任务等待完成，Consumers提取已经完成的任务，并以他们提交的顺序处理他们的结果。
 * manage asynchronous IO, in which tasks that perform reads are
 * submitted in one part of a program or system, and then acted upon
 * in a different part of the program when the reads complete,
 * possibly in a different order than they were requested.
 * 一个CompletionService可以用于管理异步IO，其中读取任务在一个程序或系统中提交，读取动作完成在另一个程序或系统中处理。
 * <p>Typically, a <tt>CompletionService</tt> relies on a separate
 * {@link Executor} to actually execute the tasks, in which case the
 * <tt>CompletionService</tt> only manages an internal completion
 * queue. The {@link ExecutorCompletionService} class provides an
 * implementation of this approach.
 * 通常，一个CompletionService依赖一个单独的Executor来实际运行任务，这中间，CompletionService只管理内部的完成队列。
 * ExecutorCompletionService 类 提供了这个实现。
 * <p>Memory consistency effects: Actions in a thread prior to
 * submitting a task to a {@code CompletionService}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions taken by that task, which in turn <i>happen-before</i>
 * actions following a successful return from the corresponding {@code take()}.
 * 内存一致性效果：一个线程里提交任务到CompletionService前的动作。。。。。。
 */
public interface CompletionService<V> {
    /**
     * Submits a value-returning task for execution and returns a Future
     * representing the pending results of the task.  Upon completion,
     * this task may be taken or polled.
     * 提交一个有返回值的任务来执行，并返回一个Future 来代表任务结果。完成后，这个任务可能被取出。
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution 如果任务不能调度运行则会拒绝异常。
     * @throws NullPointerException if the task is null
     */
    Future<V> submit(Callable<V> task);

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task.  Upon completion, this task may be
     * taken or polled.
     * 提交一个任务，并返回一个Future代表任务结果。
     * @param task the task to submit
     * @param result the result to return upon successful completion 成功完成后返回存放结果。
     * @return a Future representing pending completion of the task,
     *         and whose <tt>get()</tt> method will return the given
     *         result value upon completion
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    Future<V> submit(Runnable task, V result);

    /**
     * Retrieves and removes the Future representing the next
     * completed task, waiting if none are yet present.
     * 检索和取出 下一个完成任务的Future结果，如果没有一个完成则等待。
     * @return the Future representing the next completed task
     * @throws InterruptedException if interrupted while waiting
     */
    Future<V> take() throws InterruptedException;


    /**
     * Retrieves and removes the Future representing the next
     * completed task or <tt>null</tt> if none are present.
     *
     * @return the Future representing the next completed task, or
     *         <tt>null</tt> if none are present
     */
    Future<V> poll();

    /**
     * Retrieves and removes the Future representing the next
     * completed task, waiting if necessary up to the specified wait
     * time if none are yet present.
     * 等待一定时间来获取。
     * @param timeout how long to wait before giving up, in units of
     *        <tt>unit</tt> 等待多长时间直到放弃。
     * @param unit a <tt>TimeUnit</tt> determining how to interpret the
     *        <tt>timeout</tt> parameter
     * @return the Future representing the next completed task or
     *         <tt>null</tt> if the specified waiting time elapses
     *         before one is present
     * @throws InterruptedException if interrupted while waiting
     */
    Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException;
}

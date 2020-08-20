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
 * A task that returns a result and may throw an exception.
 * Implementors define a single method with no arguments called
 * <tt>call</tt>.
 * 一个task可以返回一个结果，或者抛出异常。
 * 实现这个接口的类，需要定义一个没有参数的call方法。
 *
 * <p>The <tt>Callable</tt> interface is similar to {@link
 * java.lang.Runnable}, in that both are designed for classes whose
 * instances are potentially executed by another thread.  A
 * <tt>Runnable</tt>, however, does not return a result and cannot
 * throw a checked exception.
 *
 * <p> The {@link Executors} class contains utility methods to
 * convert from other common forms to <tt>Callable</tt> classes.
 *  Executors类提供了一些有用的方法可以将其他形式的转变为Callable的类。
 * 
 * @see Executor
 * @since 1.5
 * @author Doug Lea
 * @param <V> the result type of method <tt>call</tt>
 */
public interface Callable<V> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result  返回的结果
     * @throws Exception if unable to compute a result
     */
    V call() throws Exception;
}

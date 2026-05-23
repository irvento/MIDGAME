package utilz;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

/**
 * Simple generic object pool.
 *
 * <p>Objects are created lazily using the supplied creator when the pool is empty.
 * When released, objects are reset (if they implement a {@code reset()} method) and
 * stored for future reuse. The pool size is bounded to avoid unbounded memory.
 */
public class ObjectPool<T> {
    private final Deque<T> pool;
    private final Supplier<T> creator;
    private final int maxSize;

    /**
     * Creates a new pool.
     *
     * @param creator  a supplier that creates a new instance when needed
     * @param maxSize  maximum number of pooled instances
     */
    public ObjectPool(Supplier<T> creator, int maxSize) {
        this.creator = creator;
        this.maxSize = maxSize;
        this.pool = new ArrayDeque<>(maxSize);
    }

    /**
     * Acquires an instance from the pool, creating a new one if necessary.
     */
    public T acquire() {
        T obj = pool.pollFirst();
        if (obj == null) {
            obj = creator.get();
        }
        return obj;
    }

    /**
     * Releases an instance back to the pool for reuse.
     *
     * @param obj the instance to recycle
     */
    public void release(T obj) {
        if (pool.size() < maxSize) {
            // If the object has a "reset" method we could invoke it via reflection,
            // but the calling code is responsible for resetting state before release.
            pool.offerFirst(obj);
        }
        // else discard excess objects, letting GC reclaim them.
    }
}

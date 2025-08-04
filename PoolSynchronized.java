import java.io.Serializable;

/**
 * Créer le : lundi 04 août 2025
 * Auteur : Yoann Meclot (DevMyBits)
 * E-mail : devmybits@gmail.com
 */
public class PoolSynchronized<Item> extends PoolArray<Item> implements Serializable
{
    private static final long serialVersionUID = 589100828766498819L;

    private final Object mMonitor;

    PoolSynchronized(int capacity, Object lock)
    {
        super(capacity);
        mMonitor = lock;
    }

    @Override
    public Item acquire()
    {
        synchronized (mMonitor) {
            return super.acquire();
        }
    }

    @Override
    public Item acquireFirst()
    {
        synchronized (mMonitor) {
            return super.acquireFirst();
        }
    }

    @Override
    public boolean release(Item item)
    {
        synchronized (mMonitor) {
            return super.release(item);
        }
    }

    @Override
    public boolean contains(Item item)
    {
        synchronized (mMonitor) {
            return super.contains(item);
        }
    }

    @Override
    public int length()
    {
        synchronized (mMonitor) {
            return super.length();
        }
    }

    @Override
    public boolean isEmpty()
    {
        synchronized (mMonitor) {
            return super.isEmpty();
        }
    }

    @Override
    public Item[] toArray(Class<Item> type)
    {
        synchronized (mMonitor) {
            return super.toArray(type);
        }
    }

    @Override
    public void clear()
    {
        synchronized (mMonitor) {
            super.clear();
        }
    }

    @Override
    public Pool<Item> clone()
    {
        synchronized (mMonitor) {
            return super.clone();
        }
    }

    @Override
    public Iterator<Item> iterator()
    {
        synchronized (mMonitor) {
            return super.iterator();
        }
    }
}

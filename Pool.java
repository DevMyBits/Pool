import java.io.Serializable;

/**
 * Created on : 24/04/2021
 * Author     : Yoann Meclot (DevMyBits)
 * Email      : devmybits@gmail.com
 */
public interface Pool<Item> extends Serializable
{
    static <Item> Pool<Item> asPool(Item... values)
    {
        Pool<Item> result = new PoolArray<>();
        for (Item value : values) result.release(value);

        return result;
    }

    static <Item> Pool<Item> asSynchronized(Item... values)
    {
        Pool<Item> result = new PoolSynchronized<>();
        for (Item value : values) result.release(value);

        return result;
    }

    Item acquire();

    Item acquireFirst();

    boolean release(Item item);

    boolean contains(Item item);

    boolean isEmpty();

    int length();

    Item[] toArray(Class<Item> type);

    void clear();

    Pool<Item> clone();

    Iterator<Item> iterator();

    interface Iterator<Item> extends Serializable
    {
        boolean hasNext();

        boolean hasPrevious();

        Item next();

        Item previous();

        int previousIndex();

        int nextIndex();

        int hashCode();
    }
}

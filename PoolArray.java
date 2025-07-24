import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Created on : 24/04/2021
 * Author     : Yoann Meclot (DevMyBits)
 * Email      : yoannmeclot@hotmail.com
 */
public class PoolArray<Item> implements Pool<Item>, Serializable
{
    @Serial
    private static final long serialVersionUID = -6727562064513716244L;

    private Object[] mItems;
    private int mLength = 0;

    private static final Pool<Object> NULL = new PoolArray<>();

    private final InternalIterator mIterator = new InternalIterator();

    @Override
    public Item acquire()
    {
        if (mLength > 0)
        {
            final int i = mLength - 1;
            //noinspection unchecked
            Item item = (Item) mItems[i];
            mItems[i] = null;
            mLength--;
            return item;
        }
        return null;
    }

    @Override
    public Item acquireFirst()
    {
        //noinspection unchecked
        final Item value = (Item)mItems[0];
        if (mLength >= 0) System.arraycopy(mItems, 1, mItems, 0, mLength);

        mItems[--mLength] = null;
        return value;
    }

    @Override
    public boolean release(Item item)
    {
        if (mLength == mItems.length) mItems = Items.ensureCapacity(mItems, (mLength == 0 ? 5 : mLength) + 4);

        mItems[mLength] = item;
        mLength++;
        return true;
    }

    @Override
    public boolean contains(Item item)
    {
        for (int i = 0; i < mLength; i++) if (mItems[i].equals(item)) return true;
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return mLength == 0;
    }

    @Override
    public int length()
    {
        return mLength;
    }

    public Item[] toArray(Class<Item> type)
    {
        //noinspection unchecked
        Item[] array = (Item[]) Array.newInstance(type, mLength);
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(mItems, 0, array, 0, mLength);

        return array;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < mLength; i++) mItems[i] = null;
        mLength = 0;
    }

    @Override
    public Pool<Item> clone()
    {
        if (mLength == 0)//noinspection unchecked
            return (Pool<Item>)NULL;

        Pool<Item> clone = new PoolArray<>(mLength);
        for (int i = 0; i < mLength; i++)//noinspection unchecked
            clone.release((Item)mItems[i]);
        return clone;
    }

    @Override
    public Iterator<Item> iterator()
    {
        mIterator.mIndex = 0;
        return mIterator;
    }

    public PoolArray()
    {
        mLength = 0;
        mItems = new Object[0];
    }

    public PoolArray(int capacity)
    {
        if (capacity <= 0) throw new IllegalArgumentException("Pools: The pool length mut be bigger than 0.");

        mItems = new Object[capacity];
    }

    private final class InternalIterator implements Iterator<Item>
    {
        @Serial
        private static final long serialVersionUID = 6997693420965445612L;

        int mIndex = 0;

        @Override
        public boolean hasNext()
        {
            return mIndex < mLength;
        }

        @Override
        public boolean hasPrevious()
        {
            return mIndex >= 1;
        }

        @Override
        public Item next()
        {
            //noinspection unchecked
            return (Item)mItems[mIndex++];
        }

        @Override
        public Item previous()
        {
            //noinspection unchecked
            return (Item)mItems[--mIndex];
        }

        @Override
        public int previousIndex()
        {
            return mIndex - 1;
        }

        @Override
        public int nextIndex()
        {
            return mIndex;
        }

        @Override
        public int hashCode()
        {
            return Objects.hashCode(mItems[mIndex]);
        }
    }
}

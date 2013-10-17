import java.util.ArrayList;
import java.util.Iterator;

public class CombinedIterable<T> implements Iterable<T> {
    private class CombinedIterator implements Iterator<T>
    {
        public CombinedIterator(ArrayList<Iterable<T>> iterators)
        {
            this.iterators = iterators;
            this.current = iterators.get(0).iterator();
        }

        public boolean hasNext()
        {
            return current.hasNext() || (index+1 < iterables.size() && iterables.get(index+1).iterator().hasNext());
        }

        public T next()
        {
            if (current == null || !current.hasNext())
            {
                current = iterables.get(++index).iterator();
            }
            return current.next();
        }

        public void remove()
        {
            current.remove();
        }

        ArrayList<Iterable<T>> iterators;
        Iterator<T> current;
        int index;
    }


    public void add(Iterable<T> iter)
    {
        iterables.add(iter);
    }

    @Override
    public Iterator<T> iterator() {
        return new CombinedIterator(iterables);
    }

    ArrayList<Iterable<T>> iterables = new ArrayList<Iterable<T>>();
}

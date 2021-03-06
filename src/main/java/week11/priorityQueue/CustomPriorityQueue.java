package week11.priorityQueue;

import week07.Task_LinkedList.CustomLinkedList;

import java.util.Comparator;
import java.util.Iterator;

public class CustomPriorityQueue<E> extends CustomLinkedList<E> {

    //Передаю ЛЮБОЙ объект E
    //Если тип объекта наследован от Comparable, используется наш стандартный компаратор, сводящий compare() к compareTo()
    //Если же передается Comparator - используется квази переданный метод compare() от переданного компаратора.

    private static class PQComparator<E extends Comparable> implements Comparator<E> {
        @Override
        public int compare(E o1, E o2) {
            return o1.compareTo(o2);
        }
    }

    private static int DEFAULT_INITIAL_CAPACITY = 5;
    private Comparator<? super E> pqComparator = new PQComparator();
    private int comparatorCounter = 0;

    public CustomPriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    public CustomPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    public CustomPriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    public CustomPriorityQueue(int initialCapacity,
                               Comparator<? super E> comparator) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
        if (comparator != null) {
            this.pqComparator = comparator;
            this.comparatorCounter = 1;
        }
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (this.size() > 0 && pqComparator.compare(e, this.peek()) <= 0) {
            super.add(0, e);
        } else {
            super.add(e);
        }
        return true;
    }

    @Override
    public E remove() {
        E result = this.peek();
        this.remove(0);
        E hiPriority = this.peek();
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            E elem = iterator.next();
            if (pqComparator.compare(elem, hiPriority) < 0) {
                iterator.remove();
                super.add(0, elem);
                hiPriority = elem;
            }
        }
        return result;
    }

    @Override
    public E poll() {
        return this.remove();
    }

    public Comparator<? super E> comparator() {
        if (comparatorCounter != 0) {
            return pqComparator;
        }
        return null;
    }
}

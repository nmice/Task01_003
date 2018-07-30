package week07.Task_LinkedList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Implement your own LinkedListOwn (should implement List<E>), so that it would be testable with the unit
 * and performance tests from the previous task.
 */

public final class LinkedListOwn<E> implements List<E> {

    private int size = 0;
    private Node<E> first;
    private Node<E> last;


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node current = first; current != null; current = current.next) {
            if (current.item.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {//TODO
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node current = first; current != null; current = current.next) {
            array[i++] = current.item;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {//TODO optional
        return null;
    }

    @Override
    public boolean add(E e) {
        Node<E> node = new Node<>();
        node.item = e;
        if (size == 0) {
            first = node;
        } else {
            last.next = node;
            node.prev = last;
        }
        size++;
        last = node;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (Node current = first; current != null; current = current.next) {
            if (current.item.equals(o)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {//TODO
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {//TODO
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Oops");
    }

    @Override
    public boolean removeAll(Collection<?> c) {//TODO
        return false;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Oops");
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new UnsupportedOperationException("Incorrect index of the search element");
        }
        if (index == 0) {
            return first.item;
        }
        if (index == size - 1) {
            return last.item;
        }
        int i = 0;
        Node current = first;
        while (current != null) {
            if (i == index) {
                return (E) current.item;
            } else {
                i++;
                current = current.next;
            }
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index > size - 1) {
            throw new UnsupportedOperationException("Incorrect index of the search element");
        }
        if (index == 0) {
            E old = first.item;
            first.item = element;
            return old;
        }
        if (index == size - 1) {
            E old = last.item;
            last.item = element;
            return old;
        }
        int i = 0;
        Node current = first;
        while (current != null) {
            if (i == index) {
                E old = (E) current.item;
                current.item = element;
                return old;
            } else {
                i++;
                current = current.next;
            }
        }
        return null;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0 && size ==0) {
            Node<E> newNode = new Node<>();
            newNode.item = element;
            last = newNode;
            first = newNode;
            size++;
            return;
        }
        if (index == 0) {
            Node<E> newNode = new Node<>();
            newNode.item = element;
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
            size++;
            return;
        }
        if (index == size) {
            Node<E> newNode = new Node<>();
            newNode.item = element;
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
            size++;
            return;
        }
        if (index < 0 || index > size) {
            throw new UnsupportedOperationException("Incorrect index of the element being added");
        }
        int i = 0;
        for (Node current = first; current != null; current = current.next) {
            if (i == index) {
                Node<E> newNode = new Node<>();
                newNode.item = element;
                current.prev.next = newNode;
                newNode.prev = current.prev;
                current.prev = newNode;
                newNode.next = current;
                size++;
                return;
            } else {
                i++;
            }
        }
        return;
    }

    @Override
    public E remove(int index) {
        int i = 0;
        for (Node current = first; current != null; current = current.next) {
            if (i == index) {
                E old = (E) current.item;
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return old;
            } else {
                i++;
            }
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        for (Node current = first; current != null; current = current.next) {
            if (current.item.equals(o)) {
                return i;
            } else {
                i++;
            }
        }
        throw new UnsupportedOperationException("This element not live in this LinkedListOwn");
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = size-1;
        for (Node current = last; current != null; current = current.prev) {
            if (current.item.equals(o)) {
                return i;
            } else {
                i--;
            }
        }
        throw new UnsupportedOperationException("This element not live in this LinkedListOwn");
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Oops");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Oops");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Oops");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Node current = first; current != null; current = current.next) {
            result.append(result.length() == 0 ? "" : ", ");
            result.append(current.item);
        }
        return "[" + result.toString() + "]";
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;
    }
}


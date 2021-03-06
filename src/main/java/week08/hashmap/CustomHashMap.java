package week08.hashmap;

import java.util.*;

/**
 * Implement your own HashMap (put, remove, clear, size, containsKey)
 */

public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 17;
    private static final int MAXIMUM_CAPACITY = 1_073_741_824;
    int threshold;

    public CustomHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public CustomHashMap(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + capacity);
        }
        if (capacity > MAXIMUM_CAPACITY) {
            capacity = MAXIMUM_CAPACITY;
        }
        array = new Object[capacity];
        this.threshold = array.length * 3 / 4;
    }

    public CustomHashMap(int capacity, float loadFactor) {
        this(capacity);
    }


    private Object[] array;
    private int size = 0;

    public int getArrayIndex(Object key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % array.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (array[getArrayIndex(key)] != null) {
            List<Node<K, V>> list = (List) array[getArrayIndex(key)];
            for (Node<K, V> node : list) {
                if (node.key.equals(key)) return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int index = 0; index < array.length; index++) {
            if (array[index] != null) {
                List<Node<K, V>> list = (List) array[index];
                for (Node<K, V> node : list) {
                    if (node.value.equals(value)) return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = getArrayIndex(key);
        if (array[index] != null) {
            List<Node<K, V>> nodeList = (List) array[index];
            for (Node<K, V> node : nodeList) {
                if (key == null && node.key == null || key.equals(node.key)) {
                    return node.value;
                }
            }
        }
        return null;
    }//TODO


    private void copyElementsInNewHashmapown(int newArrayLength) {
        Object[] oldArray = array;
        array = new Object[newArrayLength];
        threshold = array.length * 3 / 4;
        for (int indexOfOldArray = 0; indexOfOldArray < oldArray.length; indexOfOldArray++) {
            List<Node<K, V>> nodeListInOldArray = (List) oldArray[indexOfOldArray];
            if (nodeListInOldArray != null) {
                for (Node<K, V> nodeFromOldList : nodeListInOldArray) {
                    int index = getArrayIndex(nodeFromOldList.key);
                    if (array[index] == null) {
                        List<Node<K, V>> nodeListInNewArray = new LinkedList();
                        array[index] = nodeListInNewArray;
                        nodeListInNewArray.add(nodeFromOldList);
                    } else {
                        List<Node<K, V>> nodeListInNewArray = (List) array[index];
                        for (Node<K, V> nodeFromNewList : nodeListInNewArray) {
                            if (nodeFromNewList.key == null && nodeFromOldList.key == null || nodeFromNewList.key.equals(nodeFromOldList.key)) {
                                nodeFromNewList.value = nodeFromOldList.value;
                                break;
                            }
                        }
                        nodeListInNewArray.add(nodeFromOldList);
                    }
                }
            }
        }
    }


    @Override
    public V put(K key, V value) {
        if (size >= threshold && array.length <= MAXIMUM_CAPACITY) {
            copyElementsInNewHashmapown(array.length * 2);
        }
        //1) Создать эл-т Node<K,V> c ключом и значением
        Node<K, V> newNode = new Node(key, value);
        //2) Найти индекс ячейки для помещения Node
        int index = getArrayIndex(key);
        //2.1) Если ячейка пуста, Создать новый связный список,
        if (array[index] == null) {
            List nodeList = new LinkedList();
            //2.1.1) Поместить список в ячейку с найденным индексом
            array[index] = nodeList;
            //2.1.2) Поместить эл-т Node в связный список
            nodeList.add(newNode);
            //2.2) Если ячейка не пуста
        } else {
            //2.2.1) если оба Key==null или совпадают Key, заменить в эл-те Node с ключом key значение value на переданное в метод значение value
            List<Node<K, V>> nodeList = (List) array[index];
            for (Node<K, V> node : nodeList) {
                if (key == null && node.key == null || key.equals(node.key)) {
                    V oldValue = node.value;
                    node.value = value;
                    return oldValue;
                }
            }
            //2.2.2) если не совпадают Key, добавить эл-т Node в имеющийся связный список,
            nodeList.add(newNode);
        }
        //3) Увеличить счетчик записей
        size++;
        return null;
    }//TODO


    @Override
    public V remove(Object key) {
        if (size * 2 < threshold && array.length / 2 >= DEFAULT_CAPACITY) {
            copyElementsInNewHashmapown(array.length / 2);
        }
        int index = getArrayIndex(key);
        if (array[index] != null) {
            List<Node<K, V>> nodeList = (List) array[index];
            for (Node<K, V> node : nodeList) {
                if (key == null && node.key == null || key.equals(node.key)) {
                    V oldValue = node.value;
                    nodeList.remove(node);
                    size--;
                    return oldValue;
                }
            }
        }
        return null;
    }//TODO

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] != null) {
                for (Node<K, V> node : (List<Node<K, V>>) array[i]) {
//                    if (node != null) {
                    result.append(result.length() == 0 ? "" : ", ");
                    result.append(node.toString());
//                    }
                }
            }
        }
        return "{" + result.toString() + "}";
    }


    private class MyIterator<K> implements Iterator {

        private int indexInArray = 0;
        private LinkedList<Node<K, V>> listInArray;
        private int indexInList = 0;
        private Node<K, V> nodeInList;

        private MyIterator() {
        }

        public boolean hasNext() {
            while (indexInArray < array.length) {
                if (array[indexInArray] != null) {
                    listInArray = (LinkedList<Node<K, V>>) array[indexInArray];
                    int sizeOfList = listInArray.size();
                    while (indexInList < sizeOfList) {
                        nodeInList = listInArray.get(indexInList);
                        return true;
                    }
                }
                indexInArray++;
                indexInList = 0;
            }
            return false;
        }

        public K next() {
            indexInList++;
            return nodeInList.getKey();
        }

    }

    public Iterator<K> keyIterator() {
        return new MyIterator();
    }

/*
            public boolean hasNext() {
                //ищем первый элемент
                for (int i = array.length - 1; i >= 0; i--) {
                    if (array[i] != null) {
                        current = ((LinkedList<Node<K, V>>) array[i]).get(0);
                    }
                    if ()
                }

                //Нашли первый элемент,
                //Запускаем цикл по след ячейкам массива от данной до последней. Если в ячейке есть лист, возвращаем true.

                //Новых эл-в нет, возвращаем false.
                return false;
            }

            @Override
            public K next() {
                //Если elem.hasNext = true, то:
                //Если наш elem не последний в списке, возвращаем следующий.
                //Запускаем цикл по след ячейкам массива от данной до последней,
                //Если в ячейке есть лист, присваиваем нашему элементу значение нулевого элемента из списка.
                //
                return current.getKey();
*/


    static class Node<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final K setKey(K newKey) {
            K oldKey = key;
            key = newKey;
            return oldKey;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }
}

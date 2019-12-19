public class Heap<T extends Comparable<T>> {

    private byte[] dir;
    private Comparable<T>[] heap;
    private Order order;

    int openIndex = 0;

    public Heap(T[] arr, Order order) {
        this.order = order;

        heap = new Comparable[arr.length];
        dir = new byte[arr.length];

        for (T obj : arr) {
            addToHeap(openIndex, obj);
            openIndex++;
        }
    }

    public T get() {
        return (T) heap[0];
    }

    public T take() {
        T first = get();
        removeFirst();
        return first;
    }

    private void addToHeap(int index, Comparable<T> obj) {
        heap[index] = obj;

        int parent = getParent(index);
        if (isBefore(obj, heap[parent])) {
            heap[index] = heap[parent];
            addToHeap(parent, obj);
        }
    }

    private void removeFirst() {

    }

    private int getParent(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChild(int index) {
        return index * 2 + 1;
    }

    private int getRightChild(int index) {
        return index * 2 + 2;
    }

    private boolean isBefore(int i, int j) {
        return isBefore(heap[i], heap[j]);
    }

    private boolean isBefore(Comparable a, Comparable b) {
        int compared = a.compareTo(b);
        switch (order) {
        case ASC:
            return compared < 0;
        case DESC:
            return compared > 0;
        default:
            return false;
        }
    }

}
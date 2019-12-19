public class Heap<T extends Comparable<T>> {

    private T[] values;
    private int[] heap;

    int openIndex = 0;
    private Order order;

    public Heap(T[] arr, Order order) {
        this.order = order;

        values = arr;
        heap = new int[arr.length];

        for (int i = 0; i < values.length; i++) {
            addToHeap(openIndex, i);
            openIndex++;
        }
    }

    public T get() {
        return get(0);
    }

    public T take() {
        T first = get();
        remove(0);
        return first;
    }

    private void addToHeap(int index, int valueIndex) {
        heap[index] = valueIndex;

        int parent = getParent(index);
        if (isBefore(index, parent)) {
            heap[index] = heap[parent];
            addToHeap(parent, valueIndex);
        }
    }

    private void remove(int index) {
        int l = getLeftChild(index);
        int r = getRightChild(index);

        if (get(l) != null && isBefore(l, r)) {
            heap[index] = heap[l];
            remove(l);
        } else if (get(r) != null) {
            heap[index] = heap[r];
            remove(r);
        } else {
            heap[index] = -1;
        }
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

    private T get(int index) {
        if (index >= heap.length || heap[index] == -1)
            return null;
        return values[heap[index]];
    }

    private boolean isBefore(int i, int j) {
        T a = get(i);
        T b = get(j);

        if (b == null)
            return true;

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
public class Heap<T extends Comparable<T>> {

    private Order order;
    private Node rootNode;

    public Heap(Order order) {
        this.order = order;
        rootNode = new Node();
    }

    public void add(Iterable<T> list) {
        for (T obj : list)
            add(obj);
    }

    public void add(T[] arr) {
        for (T obj : arr)
            add(obj);
    }
    
    public void add(T obj) {
        rootNode.add(obj);
    }

    public T get() {
        return rootNode.get();
    }

    public T take() {
        return rootNode.take();
    }

    private class Node {
        private T val = null;
        private Node left, right;
        int addDir = -1;

        public T get() {
            return val;
        }

        public T take() {
            if (val == null) return null;

            T temp = val;
            val = null;

            if (isBefore(left.get(), right.get()))
                val = left.take();
            else
                val = right.take();

            return temp;
        }

        public void add(T obj) {
            if (val == null) {
                val = obj;
                left = new Node();
                right = new Node();
                return;
            }

            if (isBefore(obj, val)) {
                addToChild(val);
                val = obj;
            } else {
                addToChild(obj);
            }
        }

        private void addToChild(T obj) {
            if (addDir < 0)
                left.add(obj);
            else
                right.add(obj);

            addDir = -addDir;
        }
    }

    private boolean isBefore(Comparable a, Comparable b) {
        if (a == null && b != null)
            return false;
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
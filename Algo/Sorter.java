import java.util.Arrays;
import java.util.List;

public class Sorter<T extends Comparable<T>> {

    private Comparable[] arr;
    private List<T> list;
    private Order order;
    private Algorithm algorithm;

    public Sorter(List<T> list, Algorithm algorithm, Order order) {
        arr = (T[]) new Comparable[list.size()];
        arr = list.toArray(arr);
        this.algorithm = algorithm;
        this.order = order;
    }

    public Sorter(List<T> list, Algorithm algorithm) {
        this(list, algorithm, Order.ASC);
    }

    public Sorter(List<T> list, Order order) {
        this(list, Algorithm.QUICK_SORT, order);
    }
    
    public Sorter(List<T> list) {
        this(list, Order.ASC);
    }

    public List<T> getList() {
        if (list == null) {
            list = new ArrayList<>();
            for (Comparable obj : arr)
                list.add((T) obj);
        }

        return list;
    }

    public boolean isSorted() {
        for (int i = 1; i < arr.length; i++)
            if (!isBefore(i - 1, i))
                return false;
        return true;
    }

    public void sort() {
        switch(algorithm) {
            case SELECTION_SORT: selectionSort();
            case BUBBLE_SORT: bubbleSort();
            case INSERTION_SORT: insertionSort();
            case MERGE_SORT: mergeSort();
            case QUICK_SORT: quickSort();
        }
    }

    public enum Order {
        ASC, DESC;
    }

    public enum Algorithm {
        SELECTION_SORT, BUBBLE_SORT, INSERTION_SORT, MERGE_SORT, QUICK_SORT;
    }

    private void selectionSort() {
        for (int i = 0; i < arr.length - 1; i++) {
            int smallestIndex = i;

            for (int j = i + 1; j < arr.length; j++)
                if (isBefore(j, smallestIndex))
                    smallestIndex = j;

            swap(i, smallestIndex);
        }
    }

    private void bubbleSort() {
        for (int max = arr.length; max > 0; max--)
            for (int i = 1; i < max; i++)
                if (isBefore(i, i - 1))
                    swap(i, i - 1);
    }

    private void insertionSort() {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (isBefore(j + 1, j))
                    swap(j + 1, j);
                else
                    break;
            }
        }
    }

    private void mergeSort() {
        mergeSort(0, arr.length - 1);
    }

    private void mergeSort(int l, int r) {
        if (r <= l)
            return;

        int m = (l + r) / 2;
        mergeSort(l, m);
        mergeSort(m + 1, r);
        merge(l, m, r);
    }

    private void merge(int l, int m, int r) {
        Comparable[] left = subArray(l, m);
        Comparable[] right = subArray(m + 1, r);

        int leftI = 0;
        int rightI = 0;

        for (int i = l; i <= r; i++) {
            if (leftI != left.length && (rightI == right.length || isBefore(left[leftI], right[rightI])))
                arr[i] = left[leftI++];
            else
                arr[i] = right[rightI++];
        }
    }

    private void quickSort() {
        quickSort(0, arr.length - 1);
    }

    private void quickSort(int low, int high) {
        if (low >= high)
            return;

        int pivotIndex = partition(low, high);
        quickSort(low, pivotIndex - 1);
        quickSort(pivotIndex + 1, high);
    }

    private int partition(int low, int high) {
        Comparable pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (isBefore(arr[j], pivot)) {
                i++;
                swap(i, j);
            }
        }

        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        if (i < 0 || j < 0 || i >= arr.length || j >= arr.length)
            throw new IllegalArgumentException("Invalid indeces [" + i + "," + j + "] for list of " + arr.length);

        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private boolean isBefore(int i, int j) {
        if (i < 0 || j < 0 || i >= arr.length || j >= arr.length)
            throw new IllegalArgumentException("Invalid indeces [" + i + "," + j + "] for list of " + arr.length);

        return isBefore(arr[i], arr[j]);
    }

    private boolean isBefore(Comparable a, Comparable b) {
        int compared = a.compareTo(b);
        switch (order) {
            case ASC: return compared < 0;
            case DESC: return compared > 0;
            default: return false;
        }
    }

    private Comparable[] subArray(int from, int to) {
        if (from < 0 || to < 0 || from >= arr.length || to >= arr.length)
            throw new IllegalArgumentException("Invalid indeces [" + from + "," + to + "] for list of " + arr.length);

        Comparable[] slice = new Comparable[to - from + 1];
        for (int i = 0; i < slice.length; i++)
            slice[i] = arr[from + i];

        return slice;
    }
}
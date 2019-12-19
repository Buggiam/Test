import java.util.ArrayList;
import java.util.List;

public class Sorter<T extends Comparable<T>> {

    private T[] arr;
    private List<T> list;
    private Order order;
    private Algorithm algorithm;

    public Sorter(T[] arr, Algorithm algorithm, Order order) {
        this.arr = arr;
        this.algorithm = algorithm;
        this.order = order;
    }

    public Sorter(T[] arr, Algorithm algorithm) {
        this(arr, algorithm, Order.ASC);
    }

    public Sorter(T[] arr, Order order) {
        this(arr, Algorithm.QUICK_SORT, order);
    }

    public Sorter(T[] arr) {
        this(arr, Order.ASC);
    }

    public Sorter(List<T> list, Algorithm algorithm, Order order) {
        this(list.toArray((T[]) new Comparable[list.size()]), algorithm, order);
    }

    public Sorter(List<T> list, Algorithm algorithm) {
        this(list.toArray((T[]) new Comparable[list.size()]), algorithm, Order.ASC);
    }

    public Sorter(List<T> list, Order order) {
        this(list.toArray((T[]) new Comparable[list.size()]), Algorithm.QUICK_SORT, order);
    }

    public Sorter(List<T> list) {
        this(list.toArray((T[]) new Comparable[list.size()]), Order.ASC);
    }

    public List<T> getList() {
        if (list == null) {
            list = new ArrayList<>();
            for (T obj : arr)
                list.add(obj);
        }

        return list;
    }

    public boolean isSorted() {
        for (int i = 1; i < arr.length; i++)
            if (!isBefore(i - 1, i) && !areEqual(i - 1, i))
                return false;
        return true;
    }

    public void sort() {
        if (algorithm == Algorithm.SELECTION_SORT)
            selectionSort();
        else if (algorithm == Algorithm.BUBBLE_SORT)
            bubbleSort();
        else if (algorithm == Algorithm.INSERTION_SORT)
            insertionSort(0, arr.length - 1);
        else if (algorithm == Algorithm.MERGE_SORT)
            mergeSort(0, arr.length - 1);
        else if (algorithm == Algorithm.QUICK_SORT)
            quickSort(0, arr.length - 1);
        else if (algorithm == Algorithm.TIM_SORT)
            timSort(0, arr.length - 1, 32);
        else if (algorithm == Algorithm.HEAP_SORT)
            heapSort();    
    }

    public enum Algorithm {
        SELECTION_SORT, BUBBLE_SORT, INSERTION_SORT, MERGE_SORT, QUICK_SORT, TIM_SORT, HEAP_SORT;
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

    private void insertionSort(int from, int to) {
        for (int i = from + 1; i <= to; i++) {
            for (int j = i - 1; j >= from; j--) {
                if (isBefore(j + 1, j))
                    swap(j + 1, j);
                else
                    break;
            }
        }
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
        T[] left = subArray(l, m);
        T[] right = subArray(m + 1, r);

        int leftI = 0;
        int rightI = 0;

        for (int i = l; i <= r; i++)
            if (leftI != left.length && (rightI == right.length || isBefore(left[leftI], right[rightI])))
                arr[i] = left[leftI++];
            else
                arr[i] = right[rightI++];
    }

    private void quickSort(int low, int high) {
        if (low >= high)
            return;

        int pivotIndex = partition(low, high);
        quickSort(low, pivotIndex - 1);
        quickSort(pivotIndex + 1, high);
    }

    private int partition(int low, int high) {
        T pivot = arr[high];

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

    private void timSort(int l, int r, int run) {
        if (r <= l)
            return;

        if (r + 1 - l <= run) {
            insertionSort(l, r);
            return;
        }    

        int m = (l + r) / 2;
        timSort(l, m, run);
        timSort(m + 1, r, run);
        merge(l, m, r);
    }

    private void heapSort() {
        Heap<T> heap = new Heap<T>((T[]) arr, order);
        T[] newArr = createTypeArray(arr.length);

        for (int i = 0; i < arr.length; i++)
            newArr[i] = heap.take();

        arr = newArr;    
    }

    private void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private T[] subArray(int from, int to) {
        T[] slice = createTypeArray(to - from + 1);
        for (int i = 0; i < slice.length; i++)
            slice[i] = arr[from + i];

        return slice;
    }

    private boolean isBefore(int i, int j) {
        return isBefore(arr[i], arr[j]);
    }

    private boolean isBefore(T a, T b) {
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

    private boolean areEqual(int i, int j) {
        return areEqual(arr[i], arr[j]);
    }

    private boolean areEqual(T a, T b) {
        return a.compareTo(b) == 0;
    }

    private T[] createTypeArray(int length) {
        return (T[]) new Comparable[length];
    }
}
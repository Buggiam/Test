import java.util.Arrays;
import java.util.List;

public class Sorter<T extends Comparable<T>> {

    private Comparable[] arr;
    private List<T> list;

    public Sorter(T[] arr) {
        this.arr = arr;
    }

    public Sorter(List<T> list) {
        arr = (T[]) new Comparable[list.size()];
        arr = list.toArray(arr);
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
            if (!isLessThan(i - 1, i))
                return false;
        return true;
    }

    public void selectionSort() {
        for (int i = 0; i < arr.length - 1; i++) {
            int smallestIndex = i;

            for (int j = i + 1; j < arr.length; j++)
                if (isLessThan(j, smallestIndex))
                    smallestIndex = j;

            swap(i, smallestIndex);
        }
    }

    public void bubbleSort() {
        for (int max = arr.length; max > 0; max--)
            for (int i = 1; i < max; i++)
                if (isLessThan(i, i - 1))
                    swap(i, i - 1);
    }

    public void insertionSort() {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (isLessThan(j + 1, j))
                    swap(j + 1, j);
                else
                    break;
            }
        }
    }

    public void mergeSort() {
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
        Comparable[] left = new Comparable[m - l + 1];
        Comparable[] right = new Comparable[r - m];

        int index = 0;
        for (int i = l; i <= m; i++) {
            left[index] = arr[i];
            index++;
        }

        index = 0;
        for (int i = m + 1; i <= r; i++) {
            right[index] = arr[i];
            index++;
        }

        int leftI = 0;
        int rightI = 0;

        for (int i = l; i <= r; i++) {
            if (leftI != left.length && (rightI == right.length || isLessThan(left[leftI], right[rightI]))) {
                arr[i] = left[leftI];
                leftI++;
            } else {
                arr[i] = right[rightI];
                rightI++;
            }
        }
    }

    private void swap(int i, int j) {
        if (i < 0 || j < 0 || i >= arr.length || j >= arr.length)
            throw new IllegalArgumentException("Invalid swap indexes [" + i + "," + j + "] for list of " + arr.length);

        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private boolean isLessThan(int i, int j) {
        if (i < 0 || j < 0 || i >= arr.length || j >= arr.length)
            throw new IllegalArgumentException("Invalid swap indexes [" + i + "," + j + "] for list of " + arr.length);

        return isLessThan(arr[i], arr[j]);
    }

    private static boolean isLessThan(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }
}
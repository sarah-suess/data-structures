import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author sarah suess
 * @version 1.0
 * @userid ssuess3
 * @GTID 903687080
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException("The array or comparator is null.");
        }
        for (int i = 1; i < arr.length; i++) {
            int index = i;
            while (index > 0 && comparator.compare(arr[index - 1], arr[index]) > 0) {
                T hold = arr[index - 1];
                arr[index - 1] = arr[index];
                arr[index] = hold;
                index--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException("Array and/or comparator is null.");
        }
        boolean swap = true;
        int init = 0;
        int end = (arr.length - 1);
        while (end > init && swap) {
            int swapped = init;
            swap = false;
            for (int i = init; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T hold = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = hold;
                    swapped = i;
                    swap = true;
                }
            }
            end = swapped;
            if (swap) {
                swap = false;
                for (int i = end; i > init; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        T hold = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = hold;
                        swapped = i;
                        swap = true;
                    }
                }
                init = swapped;
            }
        }

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException("The array or comparator is null");
        }
        if ((arr.length == 0) || (arr.length == 1)) {
            return;
        }
        int mid = arr.length / 2;
        T[] right = (T[]) new Object[arr.length - mid];
        T[] left = (T[]) new Object[mid];
        for (int i = 0; i < arr.length; i++) {
            if (i < mid) {
                left[i] = arr[i];
            } else {
                right[i - mid] = arr[i];
            }
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int i = 0;
        int j = 0;
        while (j < right.length && i < left.length) {
            if (comparator.compare(left[i], right[j]) > 0) {
                arr[i + j] = right[j];
                j++;
            } else {
                arr[i + j] = left[i];
                i++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }

    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotInd = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotInd = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null || k < 1 || k > arr.length) {
            throw new IllegalArgumentException("One of your input parameters is incorrect or null.");
        }

        return kthSelect(arr, 0, arr.length - 1, k, comparator, rand);
    }

    /**
     * Helper method for kthSelect.
     * @param arr the array that should be modified after the method
     *                   is finished executing as needed
     * @param start start of array
     * @param end end of array
     * @param k kth index
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     * @param <T> data type
     */
    private static <T> T kthSelect(T[] arr, int start, int end, int k, Comparator<T> comparator, Random rand) {
        int pivotInd = rand.nextInt(end - start + 1) + start;
        T pivot = arr[pivotInd];
        arr[pivotInd] = arr[start];
        arr[start] = pivot;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        T temp = arr[j];
        arr[j] = arr[start];
        arr[start] = temp;
        if (j == (k - 1)) {
            return arr[j];
        }
        if (j > k - 1) {
            return kthSelect(arr, start, j - 1, k, comparator, rand);
        } else {
            return kthSelect(arr, j + 1, end, k, comparator, rand);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array can not be null");
        }
        if (arr.length > 0) {
            int iter = 1;
            int max = Math.abs(arr[0]);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == Integer.MIN_VALUE) {
                    max = Integer.MAX_VALUE;
                } else if (Math.abs(arr[i]) > max) {
                    max = Math.abs(arr[i]);
                }
            }
            while (max >= 10) {
                iter++;
                max = max / 10;
            }
            ArrayList<Integer>[] buckets = new ArrayList[19];
            for (int i = 0; i < 19; i++) {
                buckets[i] = new ArrayList<Integer>();
            }
            int div = 1;
            for (int i = 0; i < iter; i++) {
                for (int h = 0; h < arr.length; h++) {
                    int spot = (arr[h] / div) % 10;
                    buckets[spot + 9].add(arr[h]);
                }
                int index = 0;
                for (int j = 0; j < 19; j++) {
                    while (!buckets[j].isEmpty()) {
                        arr[index] = buckets[j].remove(0);
                        index++;
                    }
                }
                div *= 10;
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can not be null");
        }
        Queue<Integer> queue = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = queue.remove();
        }
        return arr;
    }
}

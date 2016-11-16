package ArraysSort;

import java.util.Arrays;

/**
 * Created by Yura on 9/5/2016.
 */
public class ArraysSortsAndMergeAndInvert {
    public static void main(String[] args) {

//        int[] testArray = {9, 0, 2, 4, 1, 10, 8, 7, 3};
//
//        testArray = MergeSort(testArray);
//
//        System.out.println("Result: " + Arrays.toString(testArray));
//
//                int[][][] data = {
//                {{}, {}, {}},
//                {{}, {0}, {4}},
//                {{0}, {}},
//                {{0}, {0}},
//                {{0, 2}, {1, 3}},
//                {{0, 2, 7, 9, 123}, {1, 3, 4, 5, 6}},
//                {{10}, {1, 3, 4, 5, 6, 19, 20}},
//        };
//
//        for(int[][] origin : data) {
//
//            int[] left = origin[0];
//            int[] right = origin[1];
//            int[] merged = MergeSortedArrays(left, right);
//
//            System.out.println(
//                    Arrays.toString(left) +
//                            " + " +
//                            Arrays.toString(right) +
//                            " -> " +
//                            Arrays.toString(merged)
//            );
//
//        }

        int[] testArr1 = new int[1_000_000];
        int[] testArr2 = new int[1_000_000];
        int[] result = new int[testArr1.length + testArr2.length];

        for (int i = 0; i < testArr1.length; i++) {
            testArr1[i] = (int)(Math.random()*1_000_000);
            testArr2[i] = (int)(Math.random()*1_000_000);
        }

        long StartTime = System.nanoTime();
        InsertionSort(testArr1);
        System.out.println("Insertion: " + (System.nanoTime() - StartTime)/1_000_000);

        StartTime = System.nanoTime();
        testArr2 = MergeSort(testArr2);
        System.out.println("Merge: " + (System.nanoTime() - StartTime)/1_000_000);
//
//        StartTime = System.nanoTime();
//        SelectionSort(testArr2);
//        System.out.println("Selection: " + (System.nanoTime() - StartTime)/1_000_000);
//
        StartTime = System.nanoTime();
        result = MergeSortedArrays(testArr1,testArr2);
        System.out.println("First: " + (System.nanoTime() - StartTime)/1_000_000);

        StartTime = System.nanoTime();
        result = MergeSortedArrays_2(testArr1,testArr2);
        System.out.println("Second: " + (System.nanoTime() - StartTime)/1_000_000);
    }

    public static int[] MergeSortedArrays(int[] arF, int[] arS) {
        int[] result = new int[arF.length + arS.length];
        int firstIndex = 0;
        int secondIndex = 0;

        while (firstIndex + secondIndex != result.length) {
            if (firstIndex == arF.length) {
                System.arraycopy(arS, secondIndex, result, firstIndex + secondIndex, arS.length - secondIndex);
                break;
            } else if (secondIndex == arS.length) {
                System.arraycopy(arF, firstIndex, result, firstIndex + secondIndex, arF.length - firstIndex);
                break;
            }

            if (arF[firstIndex] < arS[secondIndex]) {
                result[firstIndex + secondIndex] = arF[firstIndex++];
            } else {
                result[firstIndex + secondIndex] = arS[secondIndex++];
            }
        }

        return result;
    }

    public static int[] MergeSortedArrays_2(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex + rightIndex < result.length) {
            if (rightIndex == right.length || leftIndex != left.length
                    && left[leftIndex] < right[rightIndex]) {
                result[leftIndex + rightIndex] = left[leftIndex++];
            } else {
                result[leftIndex + rightIndex] = right[rightIndex++];
            }
        }

        return result;
    }

    public static int[] MergeSort(int[] ar) {
        return SplitArrayMergeSort(ar, 0, ar.length);
    }

    private static int[] SplitArrayMergeSort(int[] ar, int start, int end) {
        if ((end - start) < 2) {
            return ar;
        }


        int middle = (end - start) / 2;
        int[] arLeft = new int[middle];
        int[] arRight = new int[end - middle];

        System.arraycopy(ar, 0, arLeft, 0, middle);
        System.arraycopy(ar, middle, arRight, 0, end - middle);

        arLeft = SplitArrayMergeSort(arLeft, 0, arLeft.length);
        arRight = SplitArrayMergeSort(arRight, 0, arRight.length);

        ar = MergeSortedArrays(arLeft, arRight);
        return ar;
    }

    public static void BubleSort(int[] ar) {
        for (int i = 0; i < ar.length - 1; i++)
            for (int j = 0; j < ar.length - i - 1; j++)
                if (ar[j] > ar[j + 1])
                    swapElements(ar, j, j + 1);
    }

    public static void BubleSortDecrimental(int[] ar) {
        for (int i = ar.length - 1; i >= 0; i--)
            for (int j = ar.length - 1; j > ar.length - i - 1; j--)
                if (ar[j] > ar[j - 1])
                    swapElements(ar, j, j - 1);
    }

    public static void SelectionSort(int[] ar) {
        for (int barrier = ar.length - 1; barrier >= 0; barrier--)
            for (int j = 0; j < barrier; j++)
                if (ar[barrier] > ar[j])
                    swapElements(ar, j, barrier);
    }

    private static void swapElements(int[] ar, int j, int i) {
        int tmp = ar[j];
        ar[j] = ar[i];
        ar[i] = tmp;
    }

    public static void InsertionSort(int[] ar) {
        for (int barrier = 1; barrier < ar.length; barrier++) {
            int newElement = ar[barrier];
            int count = barrier - 1;

            while (count >= 0 && ar[count] > newElement) {
                ar[count + 1] = ar[count];
                count--;
            }

            ar[count + 1] = newElement;
        }
    }
}

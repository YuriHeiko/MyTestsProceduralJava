package ArraysSort;

import java.util.Arrays;

class Tests {

    public static void main(String args[]) {

        char c = 'C';

        if (c =='C')
            System.out.println(true);

//        int[] ar = {12, 3, 5, 8, 2, 1, 14, 22, 99, 0, 0, 1, 3, 5, 7, 7, 12, 13};
        int[] arL = {1, 10};
        int[] arR = {2, 3, 4, 6, 8};

        int[] ar = MergeSortedArrays(arL, arR);

        System.out.println(Arrays.toString(ar));
    }

    public static int[] MergeSortedArrays(int[] arL, int[] arR) {
        int leftIndex = 0;
        int rightIndex = 0;
        int[] result = new int[arL.length + arR.length];

        while (leftIndex < arL.length && rightIndex < arR.length) {
            if (arL[leftIndex] > arR[rightIndex])
                result[leftIndex + rightIndex] = arR[rightIndex++];
            else
                result[leftIndex + rightIndex] = arL[leftIndex++];
        }

        if (arL.length > leftIndex)
            System.arraycopy(arL, leftIndex, result, leftIndex + rightIndex, arL.length - leftIndex);
        else if (arR.length > rightIndex)
            System.arraycopy(arR, rightIndex, result, leftIndex + rightIndex, arR.length - rightIndex);

        return result;
    }
}
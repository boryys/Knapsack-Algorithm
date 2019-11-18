import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Knapsack {

    public int L;
    public int W;
    public int area;

    public Knapsack(int l, int w)
    {
        L =l;
        W = w;
        area = L*W;
    }


    public boolean fillKnapsack(List<Rectangle> rectangles, ContainerArray containerArray, boolean[] arr)
    {
        boolean paintedK;
        int sum = 0;
        int recL;
        int recW;
        for(Rectangle rectangle : rectangles)
        {
            paintedK = false;

            if(arr[rectangles.indexOf(rectangle)]) {
                recL = rectangle.w;
                recW = rectangle.l;
            }
            else {
                recL = rectangle.l;
                recW = rectangle.w;
            }

            for(int i = 0; i < L; i++)
            {
                for (int j = 0; j < W; j++)
                {
                    if((recL - 1) <= i && (recW - 1) <= j)
                    {
                        if(containerArray.canPutRectangle(recL, recW, i, j))
                        {
                            containerArray.putRectangle(rectangle, recL, recW, i, j);
                            sum += rectangle.p;
                            paintedK = true;
                            break;
                        }
                    }
                }
                if(paintedK) break;
            }
            if(!paintedK) return false;
        }

        System.out.println(sum);
        Logger.addToLog(Integer.toString(sum));
        containerArray.printArray();
        return true;
    }

    //Helper for generating combinations
    private static void CombinationsHelper(List<int[]> combinations, int data[], int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            CombinationsHelper(combinations, data, start + 1, end, index + 1);
            CombinationsHelper(combinations, data, start + 1, end, index);
        }
    }

    public List<int[]> generateCombinations(int n, int r) {
        List<int[]> combinations = new ArrayList<>();

        CombinationsHelper(combinations, new int[r], 0, n-1, 0);

        return combinations;
    }

    public List<List<Integer>> generatePermutations(List<Integer> original) {
        if (original.size() == 0) {
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            result.add(new ArrayList<Integer>());
            return result;
        }
        int firstElement = original.remove(0);
        List<List<Integer>> returnValue = new ArrayList<List<Integer>>();
        List<List<Integer>> permutations = generatePermutations(original);
        for (List<Integer> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<Integer> temp = new ArrayList<Integer>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    static void addToList(ArrayList<boolean[]> list, boolean arr[], int n)
    {
        boolean[] copy = new boolean[n];
        System.arraycopy(arr, 0, copy, 0, n);
        list.add(copy);
    }

    static void generateAllBinaryStringsHelper(ArrayList<boolean[]> list, int n, boolean arr[], int i)
    {
        if (i == n)
        {
            addToList(list, arr, n);
            return;
        }
        arr[i] = false;
        generateAllBinaryStringsHelper(list, n, arr, i + 1);
        arr[i] = true;
        generateAllBinaryStringsHelper(list, n, arr, i + 1);
    }

    static ArrayList<boolean[]> generateAllBinarySequences(int n)
    {
        boolean[] arr = new boolean[n];

        ArrayList<boolean[]> list = new ArrayList<>();
        generateAllBinaryStringsHelper(list, n, arr, 0);

        return list;
    }
}

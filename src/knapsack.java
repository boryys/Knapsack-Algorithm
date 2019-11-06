import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class knapsack {

    private static class rectangle{
        int id;
        int l;
        int w;
        int area;
        int p;

        rectangle(int _id, int _l, int _w, int _p){
            this.id = _id;
            this.l = _l;
            this.w = _w;
            this.area = _l * _w;
            this.p = _p;
        }
    }

    static void printArray(int[][] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    static void putRectangle(int[][] F, rectangle rec, int i, int j)
    {
        for(int l = 0; l < rec.l; l++)
        {
            for(int w = 0; w < rec.w; w++)
            {
                F[i - l][j - w] = rec.id;
            }
        }
    }

    static boolean canPutRectangle(int[][] F, rectangle rec, int i, int j)
    {
        for(int l = 0; l < rec.l; l++)
        {
            for(int w = 0; w < rec.w; w++)
            {
                if(F[i - l][j - w] != 0) return false;
            }
        }
        return true;
    }

    static void zeroArray(int[][] F)
    {
        for (int i = 0; i < F.length; i++) {
            for (int j = 0; j < F[i].length; j++) {
                F[i][j] = 0;
            }
        }
    }

    static boolean fillKnapsack(List<rectangle> rectangles, int L, int W, int[][] F)
    {
        boolean paintedK;
        int sum = 0;
        for(int k = 0; k < rectangles.size(); k++)
        {
            paintedK = false;
            for(int i = 0; i < L; i++)
            {
                for (int j = 0; j < W; j++)
                {
                    if((rectangles.get(k).l - 1) <= i && (rectangles.get(k).w - 1) <= j)
                    {
                        if(canPutRectangle(F, rectangles.get(k), i, j))
                        {
                            putRectangle(F, rectangles.get(k), i, j);
                            sum += rectangles.get(k).p;
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
        printArray(F);
        return true;
    }

    private static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }

    public static List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();

        helper(combinations, new int[r], 0, n-1, 0);

        return combinations;
    }

    public static List<List<Integer>> generatePerm(List<Integer> original) {
        if (original.size() == 0) {
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            result.add(new ArrayList<Integer>());
            return result;
        }
        int firstElement = original.remove(0);
        List<List<Integer>> returnValue = new ArrayList<List<Integer>>();
        List<List<Integer>> permutations = generatePerm(original);
        for (List<Integer> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<Integer> temp = new ArrayList<Integer>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    public static void main(String[] args) {
        int L = 7;
        int W = 7;
        int areaF = L*W;

        System.out.println("Area F = " + areaF);
        int[][] maxF = new int[L][W];
        zeroArray(maxF);
        printArray(maxF);
        int max = 0;

        List<rectangle> rectangles = new ArrayList<rectangle>();
        List<rectangle> tmp = new ArrayList<rectangle>();

        rectangle rec6 = new rectangle(6,2, 2, 3);
        tmp.add(rec6);
        rectangle rec7 = new rectangle(7, 2, 2, 1);
        tmp.add(rec7);
        rectangle rec8 = new rectangle(8, 2, 2, 1);
        tmp.add(rec8);
        rectangle rec9 = new rectangle(9, 3, 3, 2);
        tmp.add(rec9);
        rectangle rec10 = new rectangle(10, 1, 8, 4);
        tmp.add(rec10);
        rectangle rec1 = new rectangle(1,4, 3, 5);
        tmp.add(rec1);
        rectangle rec2 = new rectangle(2, 3, 3, 3);
        tmp.add(rec2);
        rectangle rec3 = new rectangle(3, 5, 2, 3);
        tmp.add(rec3);
        rectangle rec4 = new rectangle(4, 1, 4, 3);
        tmp.add(rec4);
        rectangle rec5 = new rectangle(5, 6, 6, 25);
        tmp.add(rec5);

        for(rectangle rec : tmp)
        {
            if(rec.l <= L && rec.w <= W)
            {
                rectangles.add(rec);
            }
        }

        for(rectangle rec : rectangles)
        {
            System.out.println("id: " + rec.id + ", l: " + rec.l + ", w: " + rec.w + ", p: " + rec.p + ", area: " + rec.area);
        }

        for(int R = rectangles.size(); R >= 1; R--) {
            List<int[]> combinations = generate(rectangles.size(), R);

            for (int[] combination : combinations) {
                int combinationValue = 0;
                int combinationArea = 0;
                for(int n : combination)
                {
                    combinationValue += rectangles.get(n).p;
                    combinationArea += rectangles.get(n).area;
                }

                if(combinationValue > max)
                {
                    if(combinationArea <= areaF)
                    {
                        System.out.println(Arrays.toString(combination) + " Value = " + combinationValue + " Area = " + combinationArea);

                        List<Integer> list = new ArrayList<Integer>();
                        for (int i : combination) {
                            list.add(i);
                        }

                        List<List<Integer>> permutations = generatePerm(list);
                        int[][] F = new int[L][W];

                        for (List<Integer> perm : permutations) {
                            List<rectangle> recs = new ArrayList<rectangle>();
                            zeroArray(F);
                            for (int i : perm) {
                                recs.add(rectangles.get(i));
                            }
                            if(fillKnapsack(recs, L, W, F))
                            {
                                maxF = F;
                                max = combinationValue;

                                printArray(maxF);
                                System.out.println("Value = " + max);

                                break;
                            }
                        }

                    }
                }
            }
            System.out.println("");
        }

        System.out.println("Max ");
        printArray(maxF);
        System.out.println("Value = " + max);
    }
}

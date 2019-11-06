import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Knapsack {


    static boolean fillKnapsack(List<Rectangle> rectangles, int L, int W, ContainerArray containerArray)
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
                        if(containerArray.canPutRectangle(rectangles.get(k), i, j))
                        {
                            containerArray.putRectangle(rectangles.get(k), i, j);
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

    public static List<int[]> generateCombinations(int n, int r) {
        List<int[]> combinations = new ArrayList<>();

        CombinationsHelper(combinations, new int[r], 0, n-1, 0);

        return combinations;
    }

    public static List<List<Integer>> generatePermutations(List<Integer> original) {
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

    public static void main(String[] args) {
        int L = 7;
        int W = 7;
        int areaF = L*W;

        System.out.println("Area F = " + areaF);
        ContainerArray maxContainerArray = new ContainerArray(L,W);
        maxContainerArray.printArray();
        int max = 0;

        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        List<Rectangle> tmp = new ArrayList<Rectangle>();

        Rectangle rec6 = new Rectangle(6,2, 2, 3);
        tmp.add(rec6);
        Rectangle rec7 = new Rectangle(7, 2, 2, 1);
        tmp.add(rec7);
        Rectangle rec8 = new Rectangle(8, 2, 2, 1);
        tmp.add(rec8);
        Rectangle rec9 = new Rectangle(9, 3, 3, 2);
        tmp.add(rec9);
        Rectangle rec10 = new Rectangle(10, 1, 8, 4);
        tmp.add(rec10);
        Rectangle rec1 = new Rectangle(1,4, 3, 5);
        tmp.add(rec1);
        Rectangle rec2 = new Rectangle(2, 3, 3, 3);
        tmp.add(rec2);
        Rectangle rec3 = new Rectangle(3, 5, 2, 3);
        tmp.add(rec3);
        Rectangle rec4 = new Rectangle(4, 1, 4, 3);
        tmp.add(rec4);
        Rectangle rec5 = new Rectangle(5, 6, 6, 25);
        tmp.add(rec5);

        for(Rectangle rec : tmp)
        {
            if(rec.l <= L && rec.w <= W)
            {
                rectangles.add(rec);
            }
        }

        for(Rectangle rec : rectangles)
        {
            System.out.println("id: " + rec.id + ", l: " + rec.l + ", w: " + rec.w + ", p: " + rec.p + ", area: " + rec.area);
        }

        for(int R = rectangles.size(); R >= 1; R--) {
            List<int[]> combinations = generateCombinations(rectangles.size(), R);

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

                        List<List<Integer>> permutations = generatePermutations(list);
                        ContainerArray currentArray = new ContainerArray(L,W);

                        for (List<Integer> perm : permutations) {
                            List<Rectangle> recs = new ArrayList<Rectangle>();
                            currentArray.ZeroArray();
                            for (int i : perm) {
                                recs.add(rectangles.get(i));
                            }
                            if(fillKnapsack(recs, L, W, currentArray))
                            {
                                maxContainerArray = currentArray;
                                max = combinationValue;

                                maxContainerArray.printArray();
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
        maxContainerArray.printArray();
        System.out.println("Value = " + max);
    }
}

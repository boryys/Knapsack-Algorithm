import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        int L = 7;
        int W = 7;
        int areaF = L*W;
        Knapsack knapsack = new Knapsack(L,W);
        System.out.println("Area F = " + areaF);
        ContainerArray maxContainerArray = new ContainerArray(knapsack.L,knapsack.W);
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
            if(rec.l <= knapsack.L && rec.w <= knapsack.W)
            {
                rectangles.add(rec);
            }
        }

        for(Rectangle rec : rectangles)
        {
            System.out.println("id: " + rec.id + ", l: " + rec.l + ", w: " + rec.w + ", p: " + rec.p + ", area: " + rec.area);
        }

        for(int R = rectangles.size(); R >= 1; R--) {
            List<int[]> combinations = knapsack.generateCombinations(rectangles.size(), R);

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
                    if(combinationArea <= knapsack.area)
                    {
                        System.out.println(Arrays.toString(combination) + " Value = " + combinationValue + " Area = " + combinationArea);

                        List<Integer> list = new ArrayList<Integer>();
                        for (int i : combination) {
                            list.add(i);
                        }

                        List<List<Integer>> permutations = knapsack.generatePermutations(list);
                        ContainerArray currentArray = new ContainerArray(knapsack.L,knapsack.W);

                        for (List<Integer> perm : permutations) {
                            List<Rectangle> recs = new ArrayList<Rectangle>();
                            currentArray.ZeroArray();
                            for (int i : perm) {
                                recs.add(rectangles.get(i));
                            }
                            if(knapsack.fillKnapsack(recs, currentArray))
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

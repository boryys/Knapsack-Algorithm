import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;


public class Main {


    public static void main(String[] args) {


        int L = 5;
        int W = 5;
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
        Rectangle rec2 = new Rectangle(2, 3, 3, 15);
        tmp.add(rec2);
        Rectangle rec3 = new Rectangle(3, 5, 2, 15);
        tmp.add(rec3);
        Rectangle rec4 = new Rectangle(4, 1, 4, 12);
        tmp.add(rec4);
        Rectangle rec5 = new Rectangle(5, 6, 6, 25);
        tmp.add(rec5);


        InitialDraw(L,W,tmp,10);
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
        finalDrawing(L,W,tmp,10,maxContainerArray);
    }



    public static void InitialDraw(int L, int W,  List<Rectangle> rectangles, int scale) {
        JFrame frame = new JFrame("Initials Rectangles");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Draw a rectangle using Rectangle2D class
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);

                int x = 100;
                int y = 100;

                int offsetX = 20;
                int offsetY = 20;

                // Draw the container
                g2.draw(new Rectangle2D.Double(x, y, scale * W, scale * L));
                //Draw rectangles next to a container
                drawListOfRectangles(g2,rectangles,x,y,offsetX,offsetY,scale,L,W);


            }
        }, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(new Dimension(1000, 1000));
        frame.setVisible(true);
    }


    private static void finalDrawing(int L, int W,  List<Rectangle> rectangles, int scale, ContainerArray containerArray)
    {
        JFrame frame = new JFrame("Final Rectangles");
        Colours colours = new Colours();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Draw a rectangle using Rectangle2D class
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                int x = 100;
                int y = 100;
                g2.draw(new Rectangle2D.Double(x, y, scale * W, scale * L));

                int offsetX = 20;
                int offsetY = 20;

                rectangles.sort(Comparator.comparing(Rectangle::getId));

                //Draw the container with rectangles
                for(int i =0; i<L; i++)
                {
                    for(int j =0; j<W;j++)
                    {
                        int id = containerArray.Area[i][j];

                        if(id>0 && id<rectangles.size())
                        {
                            Rectangle rec = rectangles.get(id-1);
                            g2.setColor(colours.colorList.get(rec.id%25));
                            g2.fill(new Rectangle2D.Double(x+ scale*j, y + scale*i,scale, scale));
                            g2.drawString(Integer.toString(containerArray.Area[i][j]),x+ scale*j, y + scale*i);
                        }

                    }
                }

                List<Rectangle> drawingList = new ArrayList<>(rectangles);
                for(int i =0; i<L; i++)
                {
                    for (int j = 0; j < W; j++)
                    {
                        int id = containerArray.Area[i][j];
                        drawingList.removeIf(obj -> obj.id == id);

                    }
                }

                //Draw rectangles next to a container
                drawListOfRectangles(g2,drawingList,x,y,offsetX,offsetY,scale,L,W);


            }
        }, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(new Dimension(1000, 1000));
        frame.setVisible(true);
    }

    private static void drawListOfRectangles(Graphics2D g2, List<Rectangle> rectangles, int x, int y, int offsetX, int offsetY, int scale, int L, int W)
    {

        Colours colours = new Colours();

        for (Rectangle rec: rectangles
        ) {
            g2.setColor(colours.colorList.get(rec.id%25));
            g2.draw(new Rectangle2D.Double(x+ offsetX + scale*W, y + offsetY,rec.w * scale, rec.l*scale));
            g2.fill(new Rectangle2D.Double(x+offsetX+ scale*W, y + offsetY,rec.w * scale, rec.l*scale));
            offsetY += rec.l*scale + 10;
            g2.drawString(Integer.toString(rec.id), x+offsetX+ scale*W + scale*rec.w,y + offsetY);
        }
    }

    private static float generateColorMultiplicator(List<Rectangle> rectangles)
    {
        int max = 0;
        int min = rectangles.get(0).p;
        for (Rectangle rec: rectangles
        ) {
            if(rec.p < min)
                min = rec.p;
            if(rec.p > max)
                max = rec.p;
        }
        return 255f/(float)(max-min);
    }

}

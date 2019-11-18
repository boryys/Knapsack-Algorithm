public class ContainerArray {

    public int Length;
    public int Width;
    public int[][] Area;

    public ContainerArray(int length, int width)
    {
     Length = length;
     Width = width;
     Area = new int[Length][Width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                Area[i][j] = 0;
            }
        }
    }

    public void ZeroArray()
    {

        for (int i = 0; i < Length; i++) {
            for (int j = 0; j < Width; j++) {
                Area[i][j] = 0;
            }
        }
    }

    public void putRectangle(Rectangle rec, int L, int W, int i, int j)
    {
        for(int l = 0; l < L; l++)
        {
            for(int w = 0; w < W; w++)
            {
                Area[i - l][j - w] = rec.id;
            }
        }
    }

    public boolean canPutRectangle(int L, int W, int i, int j)
    {
        for(int l = 0; l < L; l++)
        {
            for(int w = 0; w < W; w++)
            {
                if(Area[i - l][j - w] != 0) return false;
            }
        }
        return true;
    }

    public void printArray() {
        String table="";
        for (int i = Length - 1; i >= 0; i--) {
            for (int j = 0; j < Width; j++) {
                System.out.print(Area[i][j] + " ");
                table += Area[i][j] + " ";
            }
            System.out.println("");
            table += "\n";
        }
        System.out.println("");
        table += "\n";
        Logger.addToLog(table);
    }

}

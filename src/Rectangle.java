public class Rectangle {
    int id;
    int l;
    int w;
    int area;
    int p;

    public Rectangle(int _id, int _l, int _w, int _p){
        this.id = _id;
        this.l = _l;
        this.w = _w;
        this.area = _l * _w;
        this.p = _p;
    }

    public int getId()
    {
        return id;
    }
}

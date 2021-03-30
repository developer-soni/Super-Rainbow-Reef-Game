import java.awt.*;

public abstract class GameObj
{
    protected double x;
    protected double y;
    protected double h1;
    protected double w1;
    protected Image img;

    public GameObj(Image img, double x, double y)
    {
        this.img = img;
        this.x = x;
        this.y = y;
        this.h1 = img.getHeight(null);
        this.w1 = img.getWidth(null);
    }

    public double getW1()
    {
        return this.w1;
    }
    public double getH1()
    {
        return this.h1;
    }
    public double getX()
    {
        return this.x;
    }
    public double getY()
    {
        return this.y;
    }
}

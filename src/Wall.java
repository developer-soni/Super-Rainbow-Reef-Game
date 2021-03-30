import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Wall extends GameObj
{
    private double h, w;
    private double wallType;

    public Wall(Image img, double x, double y, double type)
    {
        super(img, x, y);
        this.wallType = type;
        this.w = img.getWidth(null);
        this.h = img.getHeight(null);
    }

    public double getType()
    {
        return this.wallType;
    }

    public Rectangle2D.Double dispRect() //to create walls for collision
    {
        return new Rectangle2D.Double(x, y, w, h);
    }

    public void draw(Graphics2D g) //for big legs and coral defense
    {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        g.drawImage(this.img, rotation, null);
    }
}

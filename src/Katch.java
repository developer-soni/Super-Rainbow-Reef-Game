import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Katch extends GameObj
{
    private double imgX;
    private double imgY;
    private double angle;
    private final double rad = 2.2;
    private BufferedImage img;
    private boolean rClick;
    private boolean lClick;

    public Katch (double x, double y, double imgX, double imgY, double angle, BufferedImage img)
    {
        super(img, x, y);
        this.imgX = imgX;
        this.imgY = imgY;
        this.img = img;
        this.angle = angle;
    }

    void toggleR()
    {
        this.rClick = true;
    }
    void toggleL()
    {
        this.lClick = true;
    }

    private void moveR() //to move catcher right
    {
        imgX = (rad * Math.cos(Math.toRadians(angle)));
        imgY = (rad * Math.sin(Math.toRadians(angle)));
        x -= imgX;
        y -= imgY;
        seeBorder();
    }
    private void moveL() //to move cathcer left
    {
        imgX = (rad * Math.cos(Math.toRadians(angle)));
        imgY = (rad * Math.sin(Math.toRadians(angle)));
        x += imgX;
        y += imgY;
        seeBorder();
    }

    void unToggleR()
    {
        this.rClick = false;
    }
    void unToggleL()
    {
        this.lClick = false;
    }

    public void update()
    {
        if (this.rClick)
        {
            this.moveL();
        }
        if (this.lClick)
        {
            this.moveR();
        }
    }

    public Rectangle2D.Double dispRect() //to create walls for collision
    {
        return new Rectangle2D.Double(x, y, w1, h1);
    }

    private void seeBorder() //to keep catcher in boundary
    {
        if (x < 40)
        {
            x = 40;
        }
        if (x >= Game.screenWidth - 120)
        {
            x = Game.screenWidth - 120;
        }
        if (y < 10)
        {
            y = 10;
        }
        if (y >= Game.screenHeight - 10)
        {
            y = Game.screenHeight - 10;
        }
    }

    public void draw(Graphics2D g) //to display catcher
    {
        AffineTransform rot = AffineTransform.getTranslateInstance(x, y);
        rot.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g.drawImage(this.img, rot, null);
    }
}

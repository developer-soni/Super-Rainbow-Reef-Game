import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Pop extends GameObj
{
    private double angle, moveX, moveY, pace, xDir = 1, yDir = -1;
    private double reloadX, reloadY, reloadAngle;

    public Pop (double x, double y, double pace, BufferedImage img)
    {
        super (img, x, y);
        this.pace = pace;
        this.angle = 270;
        reloadX = x;
        reloadY = y;
        reloadAngle = angle;
    }

    public void update()
    {
        moveX = pace * Math.cos(Math.toRadians(angle));
        moveY = pace * Math.sin(Math.toRadians(angle));
        x += xDir * moveX;
        y += yDir * moveY;
    }

    public void incSpeed()
    {
        this.pace += 0.01;
    }
    public double getSpeed ()
    {
        return pace;
    }

    public void collisionY() //when hits top of screen
    {
        revY();
    }
    public void collisionX() //when hits sides of screen
    {
        revX();
    }
    public void collisionK(double angle) //when ball bounces on catcher
    {
        yDir = 1;
        xDir = 1;
        this.angle = angle;
    }
    public void revX()
    {
        xDir = xDir * -1;
    }
    public void revY()
    {
        yDir = yDir * -1;
    }

    public void reload()
    {
        x = reloadX;
        y = reloadY;
        angle = reloadAngle;
    }

    public Rectangle2D.Double dispRect()
    {
        return new Rectangle2D.Double(x, y, w1, h1);
    }

    public void draw(Graphics2D g) //displays ball / pop
    {
        AffineTransform rot = AffineTransform.getTranslateInstance(x, y);
        g.drawImage(this.img, rot, null);
    }
}

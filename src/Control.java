import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Control implements KeyListener
{
    private Katch catcher;
    private final int rArrow;
    private final int lArrow;

    public Control(Katch catcher, int lArrow, int rArrow)
    {
        this.catcher = catcher;
        this.rArrow = rArrow;
        this.lArrow = lArrow;
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == lArrow)
        {
            this.catcher.toggleL();
        }
        if (keyPressed == rArrow)
        {
            this.catcher.toggleR();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == lArrow)
        {
            this.catcher.unToggleL();
        }
        if (keyReleased  == rArrow)
        {
            this.catcher.unToggleR();
        }
    }
}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import static javax.imageio.ImageIO.read;

public class Game extends JPanel
{
    private JFrame jf;
    public static final int screenWidth = 640, screenHeight = 500;
    private BufferedImage bg, area, katchImg, popImg, bigLeg;
    private Graphics2D g1, g2;
    private InputStream map;
    private BufferedReader br;
    private String in;
    private Image wallImg, blSolid, bl1, bl2, bl3, bl4, bl5, blL;
    private ArrayList<Wall> blocks = new ArrayList<>();
    private Katch catcher;
    private Control cntrl;
    private Pop pop;
    private double pace = 0;
    private Rectangle2D.Double popRec;c
    private Rectangle2D.Double blocksRec;
    private Rectangle2D.Double catchRec;
    private Rectangle borderL;
    private Rectangle borderR;
    private Rectangle borderT;
    private int points = 0, pointsT = 0, life = 3, lvl = 1, bigleg = 0;
    private boolean gameEnd = false, lvlNext = false;

    private void init ()
    {
        try
        {
            bg = ImageIO.read(new File("resources/Background1.bmp"));
            wallImg = ImageIO.read(new File("resources/wall.gif"));
            blSolid = read(new File("resources/Block_solid.gif"));
            bl1 = read(new File("resources/Block1.gif"));
            bl2 = read(new File("resources/Block6.gif"));
            bl3 = read(new File("resources/Block3.gif"));
            bl4 = read(new File("resources/Block4.gif"));
            bl5 = read(new File("resources/Block5.gif"));
            blL = read(new File("resources/Block_life.gif"));
            katchImg = read(new File("resources/Katch1.gif"));
            bigLeg = read(new File("resources/Bigleg_small.gif"));
            popImg = read(new File("resources/Pop1.gif"));
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        this.jf = new JFrame("Super Rainbow Reef");
        this.jf.setLocation(200, 200);
        this.area = new BufferedImage(Game.screenWidth, Game.screenHeight - 20, BufferedImage.TYPE_INT_RGB);
        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);
        this.jf.setSize(bg.getWidth(), bg.getHeight() + 30);
        this.jf.setResizable(false);
        this.jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

        loadObj();
    }

    private void loadObj() //to load all game objects for each level
    {
        drawMap();
        catcher = new Katch(320-40, 450, 0, 0, 0, katchImg);
        cntrl = new Control(catcher, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        this.jf.addKeyListener(cntrl);

        double increaseSpeed = 0;

        for (double i = 0; i < lvl; i++)
        {
            this.pace = 1.4 +(increaseSpeed * 0.2);
            increaseSpeed++;
        }
        pop = new Pop(320-16, 300, pace, popImg);
    }

    public void paintComponent(Graphics g) //to display stats
    {
        g2 = (Graphics2D) g;
        g2.drawImage(area, 0, 0, this);
        g1 = area.createGraphics();

        g2.setColor(Color.white);
        g2.setFont(new Font("", Font.PLAIN, 15));
        g2.drawString("LEVEL: "+ nextLevel(g2),screenWidth-630,screenHeight-485);
        g2.drawString("SCORE: "+ getPoints(),screenWidth-550,screenHeight-485);
        g2.drawString("LIFE: "+ getLives(g2),screenWidth-402,screenHeight-485);
        g2.drawString("SPEED: "+ pop.getSpeed(),screenWidth-230,screenHeight-485);

        drawBG(g1);
        drawBlock();

        catcher.draw(g1);
        pop.draw(g1);
    }

    public void drawBlock() //to draw blocks and walls
    {
        if (!blocks.isEmpty())
        {
            for (int i = 0; i <= blocks.size() - 1; i++)
            {
                blocks.get(i).draw(g1);
            }
        }
    }

    public void drawBG(Graphics2D buffer) //to draw background of game
    {
        int wd = bg.getWidth();
        int hg = bg.getHeight();
        buffer.drawImage(bg, 0, 0, wd, hg, this);
    }

    public int getPoints() //to get points
    {
        return points + pointsT;
    }

    public void drawMap() //to print blocks on screen according to map
    {
        try
        {
            if (lvl == 1)
            {
                map = new FileInputStream("resources/Map1.txt");
            }
            else if (lvl == 2)
            {
                map = new FileInputStream("resources/Map2.txt");
            }
            else if (lvl == 3)
            {
                map = new FileInputStream("resources/Map3.txt");
            }

            br = new BufferedReader(new InputStreamReader(map));
            in = br.readLine();
            int j = 0;

            while (in != null)
            {
                for (int i = 0; i < in.length(); i++)
                {
                    if (in.charAt(i) == 's')
                    {
                        blocks.add(new Wall(blSolid, i * blSolid.getWidth(null),
                                j * blSolid.getHeight(null), 10));
                    }
                    if (in.charAt(i) == '1')
                    {
                        blocks.add(new Wall(bl1, i * blSolid.getWidth(null),
                                j * blSolid.getHeight(null), 1));
                    }
                    if (in.charAt(i) == '2')
                    {
                        blocks.add(new Wall(bl2, i * bl2.getWidth(null),
                                j * bl2.getHeight(null), 2));
                    }
                    if (in.charAt(i) == '3')
                    {
                        blocks.add(new Wall(bl3, i * bl2.getWidth(null),
                                j * bl2.getHeight(null), 3));
                    }
                    if (in.charAt(i) == '4')
                    {
                        blocks.add(new Wall(bl4, i * bl2.getWidth(null),
                                j * bl2.getHeight(null), 4));
                    }
                    if (in.charAt(i) == '5')
                    {
                        blocks.add(new Wall(bl5, i * bl2.getWidth(null),
                                j * bl2.getHeight(null), 5));
                    }
                    if (in.charAt(i) == 'l')
                    {
                        blocks.add(new Wall(blL, i * bl2.getWidth(null),
                                j * bl2.getHeight(null), 7));
                    }
                    else if (in.charAt(i) == 'b')
                    {
                        blocks.add(new Wall(bigLeg, i * bigLeg.getWidth(null),
                                j * bl2.getHeight(null), 6));
                        bigleg++;
                    }
                }
                j++;
                in = br.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public int nextLevel(Graphics2D g2)
    {
        if ( bigleg == 0)
        {
            blocks.clear();
            lvl++;
            lvlNext = true;
        }
        if ((lvl == 3) && bigleg == 0)
        {
            g2.setColor(Color.black);
            g2.setFont(new Font("", Font.PLAIN, 60));
            g2.drawString("Yay.You Won!", screenWidth/2-125, screenHeight/2);
            g2.setColor(Color.white);
            g2.setFont(new Font("", Font.PLAIN, 60));
            g2.drawString("Yay.You Won!", screenWidth/2-123, screenHeight/2-2);

            g2.setColor(Color.black);
            g2.setFont(new Font("", Font.PLAIN, 40));
            g2.drawString("Total Score : "+ getPoints(), screenWidth/2-200, screenHeight/2+60);
            g2.setColor(Color.white);
            g2.drawString("Total Score : "+ getPoints(), screenWidth/2-198, screenHeight/2+58);
            g2.setFont(new Font("", Font.PLAIN, 20));

            gameEnd = true;
        }
        return lvl;
    }

    public boolean isGameEnd()
    {
        return gameEnd;
    }

    public int getLives (Graphics g2) //to update lives
    {
        if (pop.getY() > 1000)
        {
            life--;
            pop.reload();
        }
        if (life == 0)
        {
            g2.setColor(Color.black);
            g2.setFont(new Font("", Font.PLAIN, 60));
            g2.drawString("Game Over !", screenWidth/2-165, screenHeight/2);
            g2.setColor(Color.white);
            g2.setFont(new Font("", Font.PLAIN, 60));
            g2.drawString("Game Over !", screenWidth/2-163, screenHeight/2-2);

            g2.setColor(Color.black);
            g2.setFont(new Font("", Font.PLAIN, 40));
            g2.drawString("Total Score : "+ getPoints(), screenWidth/2-200, screenHeight/2+60);
            g2.setColor(Color.white);
            g2.drawString("Total Score : "+ getPoints(), screenWidth/2-198, screenHeight/2+58);
            g2.setFont(new Font("", Font.PLAIN, 20));

            gameEnd = true;
        }
        return life;
    }

    public void checkCollision() //to check collisions with blocks or bigleg or life block
    {
        popRec = pop.dispRect();
        catchRec = catcher.dispRect();
        borderL = getBorderL();
        borderR = getBorderR();
        borderT = getBorderT();

        if (popRec.intersects(catchRec))
        {
            pop.incSpeed();

            if ((pop.getY())<(catcher.getY()))
            {
                if (pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 - 31)
                {
                    pop.collisionK(205);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 - 31
                        && pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 - 22)
                {
                    pop.collisionK(225);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 - 22
                        && pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 - 13)
                {
                    pop.collisionK(240);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 - 13
                        && pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 - 4)
                {
                    pop.collisionK(255);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 - 4
                        && pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 + 4)
                {
                    pop.collisionK(270);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 + 4
                        && pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 + 13)
                {
                    pop.collisionK(285);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 + 13
                        && pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 + 22)
                {
                    pop.collisionK(300);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 + 22
                        && pop.getX() + pop.w1 /2 < catcher.getX() + catcher.w1 /2 + 31)
                {
                    pop.collisionK(315);
                }
                else if (pop.getX() + pop.w1 /2 > catcher.getX() + catcher.w1 /2 + 31)
                {
                    pop.collisionK(335);
                }
            }
            else
                pop.collisionX();
        }
        if (popRec.intersects(borderL) || popRec.intersects(borderR))
        {
            pop.collisionX();
        }
        if (popRec.intersects(borderT))
        {
            pop.collisionY();
        }
        for (int i = 0; i <= blocks.size() - 1; i++)
        {
            blocksRec = blocks.get(i).dispRect();

            if (popRec.intersects(blocksRec)&&!((blocks.get(i).getType())==10))
            {
                pop.collisionY();

                if (blocks.get(i).getType() == 6)
                {
                    bigleg--;
                    points += 2000;
                }
                if (blocks.get(i).getType() == 7)
                {
                    life++;
                    points += 1000;
                }
                if (blocks.get(i).getType() == 1)
                {
                    points += 100;
                }
                if (blocks.get(i).getType() == 2)
                {
                    points += 200;
                }
                if (blocks.get(i).getType() == 3)
                {
                    points += 300;
                }
                if (blocks.get(i).getType() == 4)
                {
                    points += 400;
                }
                if (blocks.get(i).getType() == 5) {
                    points += 1000;
                }
                if (!(blocks.get(i).getType() == 10))
                {
                    blocks.remove(i);
                }
            }
        }
    }

    public Rectangle getBorderL()
    {
        return new Rectangle(0, 0, 43, 500);
    }
    public Rectangle getBorderR()
    {
        return new Rectangle(screenWidth-43, 0, 43, 500);
    }
    public Rectangle getBorderT()
    {
        return new Rectangle(0, 0, 640, 24);
    }

    public static void main(String[] args)
    {
        Game SG = new Game();
        SG.init();

        try
        {
            while (!SG.isGameEnd())
            {
                SG.catcher.update();
                SG.pop.update();
                SG.checkCollision();
                SG.repaint();
                Thread.sleep(1000 / 144);

                if (SG.lvlNext == true)
                {
                    SG.loadObj();
                    SG.lvlNext = false;
                }
            }
        }
        catch (InterruptedException ignored) { }
    }
}
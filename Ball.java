import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
/**
 * This program creates the ball for the game.
 *
 * @author Zohaib Asif, Kyra Griffin, Ashely Abrams
 */
public class Ball {
    //dimensions of the ball
    public final int WIDTH = 10;
    public final int HEIGHT = 10;
    private final Color COLOR = Color.RED;

    public int x;
    public int y;
    public int speedX;
    public int speedY;
    private Random r;
    
    /**
     * Constructs a ball object at a given x y coordinate and gives it a random speed so it can start to move.
     *
     * @param x xlocation of ball
     * @param y ylocation of ball
     */
    public Ball(int x,int y) {
      this.x = x;
      this.y = y;
       
      r = new Random();
      setSpeed();
    }

    /**
     * Draws the ball on to the screen
     *
     * @param g graphics panel
     */
    public void draw(Graphics g) {
      g.setColor(COLOR);
      g.fillRect(x,y,WIDTH,HEIGHT);
    }

    /**
     * Sets the speed of the ball
     *
     */
    public void setSpeed() {
        int degree = r.nextInt(360);
        while(degree == 0 || degree == 180) {
            degree = r.nextInt(360);
        }
        int radians = (int) (degree * (Math.PI/180));
        speedX = (int) (Math.cos(radians) * 8);
        speedY = (int) (Math.sin(radians) * 8);
    }

    /**
     * Method used to calculate wether a point needs to be awarded or not.  Checks if the ball is still on the screen.
     * If not it gives the respective player a point.
     *
     * @param w width of the panel
     * @param h height of the panel
     * @param p1 the first paddle
     * @param p2 the second paddle
     */
    public void contain(int w,int h,Paddle p1,Paddle p2) {
        if(x < 0) {
            x = w/2 - WIDTH/2;
            y = h/2 - HEIGHT/2;
            setSpeed();
            p2.score++;
        }
        else if(x > w - WIDTH) {
            x =  w/2 - WIDTH/2;
            y = h/2 - HEIGHT/2;
            setSpeed();
            p1.score++;
        }
        if(y < 0) {
            y = 0;
            speedY *= -1;
        }
        else if(y > h - HEIGHT) {
            y = h - HEIGHT;
            speedY *= -1;
        }
    }

}

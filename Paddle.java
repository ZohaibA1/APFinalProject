import java.awt.Graphics;
import java.awt.Color;
/**
 * This prorgam creates the paddles for the game.  
 *
 * @author Zohaib Asif, Kyra Griffin, Ashely Abrams
 */
public class Paddle {
  //Dimensions of the paddle and speed
  public final int WIDTH = 10;
  public final int HEIGHT = 60;
  public final int MAX_SPEED = 10;
  private final Color COLOR = Color.WHITE;

  public int x;
  public int y;
  public int acc;
  public int speed;
  public int score;

  /**
   * Paddle Constructor
   *
   * @param x xlocation of the paddle
   * @param y ylocation of the paddle
   */
  public Paddle(int x,int y) {
    this.x = x;
    this.y = y;
    this.acc = 1;
    this.speed = 0;
    this.score = 0;
  }

  
  /**
   * Draws the paddle on to the panel
   *
   * @param g graphics panel
   */
  public void draw(Graphics g) {
    g.setColor(COLOR);
    g.fillRect(x,y,WIDTH,HEIGHT);
  }

  /**
   * Method checks to make sure that the paddle stays on the screen.  IF it is at the top or bottom of the screen the 
   * speed of the paddle is set to 0.
   *
   * @param h height of the panel
   */
  public void contain(int h) {
    if(y < 0) {
      y = 0;
      speed = 0;
    }
    else if(y > h - HEIGHT) {
      y = h - HEIGHT;
      speed = 0;
    }
  }

  /**
   * This method will display the score on to the panel.
   *
   * @param g graphics panel
   * @param x xlocation of display
   * @param y ylocation of dispaly
   */
  public void displayScore(Graphics g,int x,int y) {
    g.setColor(COLOR);
    g.drawString("Score: " + score,x,y);
  }

}

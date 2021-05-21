import java.awt.*;
/**
 * This prorgam creates the buttons for the game.  The start button, how to play button, and restart button
 *
 * @author Zohaib Asif, Kyra Griffin, Ashely Abrams
 */
public class Button {

    public String string;
    public int x;
    public int y;
    public Color color;
    public Font font;

    /**
     * Default constructor for Button 
     *
     * @param string the buttons purpose (start, how to play, restart)
     * @param x xlocation of the button when it is initiated or used
     * @param y ylocation of the button whtn it is initiatied or used
     */
    public Button(String string,int x, int y) {
        this.string = string;
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
        this.font = new Font("Times New Roman",Font.PLAIN,12);
    }

    /**
     * Non-default Button Constructor
     *
     * @param string the buttons purpose (start, how to play, restart)
     * @param x xlocation of the button when it is initiated or used
     * @param y ylocation of the button whtn it is initiatied or used
     * @param color the color of the button 
     * @param font  the font  of the button
     */
    public Button(String string,int x, int y,Color color,Font font) {
        this.string = string;
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
    }

    /**
     * Draws the button on to the panel
     *
     * @param g graphics panel
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(string,x,y);
    }

}

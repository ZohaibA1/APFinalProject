// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
The VanishingShape class is responsible for managing the life of
one line disapears from the screen, stopping when it reaches the 
bottom of the window.
We'll be adding some gravity effects.

@author Ammar Malik and Kyra Griffin
@version Spring 2021
 */
class VanishingCircle extends AnimatedShape {

    private static Image circlePic;
    // the filename that will be loaded into snowPic
    private static final String snowPicFilename = "circle.gif";

    private int R,G,B;

    /**
    Construct a new FallingGravityBall object.
    @param startTopCenter the initial point at which the top of the
    ball should be drawn
    @param container the Swing component in which this ball is being
    drawn to allow it to call that component's repaint method
     */
    public VanishingCircle(Point startPoint, JComponent container) {

        this.startPoint = new Point(startPoint);

        this.container = container;
    }

    /**
    Draw the ball at its current location.
    @param g the Graphics object on which the ball should be drawn
     */
    public void paint(Graphics g) {
        g.drawImage(circlePic, startPoint.x, startPoint.y, null);

        // g.setColor(new Color(R,G,B));
        // g.drawLine(startLine.x, startLine.y, endLine.x, endLine.y);
    }

    /**
    This object's run method, which manages the life of the ball as it
    moves down the screen.
     */
    @Override
    public void run() {

        try {
            sleep(1000);
        }
        catch (InterruptedException e) {
        }
        while (startPoint.y < bottom) {

            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            // every 30 ms or so, we move the coordinates of the ball down
            // by a pixel
            // startLine.translate(0, y_Speed);
            // endLine.translate(0, y_Speed);
            // y_Speed++;

            // if we want to see the ball move to its new position, we
            // need to schedule a paint event on this container
            container.repaint();
        }

        done = true;
        container.repaint();

    }

    /**
    Return whether the ball has completed its fall to the bottom.
    @return whether the ball has completed its fall to the bottom
     */
    public boolean done() {

        return done;
    }
}
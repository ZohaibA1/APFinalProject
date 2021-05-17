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

@author 
@version Spring 2021
 */
class VanishingCircle extends AnimatedShape {

    private static Image circlePic;
    // the filename that will be loaded into snowPic
    private static final String circlePicFilename = "circle.gif";

    private static final int circlePicHeight = 200;

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
        if(!done){
            g.drawImage(circlePic, startPoint.x - circlePicHeight, startPoint.y - circlePicHeight, null);

        }

    }

    /**
    This object's run method, which manages the life of the ball as it
    moves down the screen.
     */
    @Override
    public void run() {

        while (startPoint.y < bottom) {

            if(R < 255 && G < 255 && B < 255){
                R++;
                G++;
                B++;
            }else{
                done = true;
            }

            container.repaint();
        }

    }

    /**
    Return whether the ball has completed its fall to the bottom.
    @return whether the ball has completed its fall to the bottom
     */
    public boolean done() {

        return done;
    }

    /**
    Set the Image to be used by all FallingSnow objects, to be 
    called by the main method before the GUI gets set up
     */
    public static void loadCirclePic() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        VanishingCircle.circlePic = toolkit.getImage(circlePicFilename);
    }
}
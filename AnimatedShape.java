import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Abstract class AnimatedLine - write a description of the class here
 *
 * @author Ammar Malik and Kyra Griffin
 * @version Spring 2021
 */
public abstract class AnimatedShape extends Thread
{
   protected final int DELAY_TIME = 22;
   protected boolean done;
   protected Point startPoint;
   protected JComponent container;
   protected int bottom;
   
   public abstract void paint(Graphics g);
   
   public abstract void run();
   
   public boolean done(){
       return done;
    }
}

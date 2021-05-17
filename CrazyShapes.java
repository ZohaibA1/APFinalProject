import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Random;

/**
This program draws a series of "Scribbles".  When the mouse is pressed
then dragged, a series of shapes are drawn from the previous to the
the current mouse location.

@author 
@version Spring 2021
 */

public class CrazyShapes extends MouseAdapter implements Runnable {

    // a list of pairs of points to keep track of the start and end coordinates 
    // where we need to draw shapes
    private ArrayList<AnimatedShape> shapes = new ArrayList<>();

    // previous mouse point for the next shape to draw
    private Point lastMouse;

    private JPanel panel;
    private JPanel controlPanel;
    private JPanel drawingPanel;

    private JCheckBox clear;

    private int temp;

    // this method is called by the paintComponent method of
    // the anonymous extension of JPanel, to keep that method
    // from getting too long
    protected void redraw(Graphics g) {

        // draw all of the shapes in the list
        int i = 0;

        while(i < shapes.size()){
            AnimatedShape s = shapes.get(i);

            if(s.done()){
                shapes.remove(i);
            } else {
                s.paint(g);
                i++;
            }
        }
    }

    /**
    The run method to set up the graphical user interface
     */
    @Override
    public void run() {

        // set up the GUI "look and feel" which should match
        // the OS on which we are running
        JFrame.setDefaultLookAndFeelDecorated(true);

        // create a JFrame in which we will build our very
        // tiny GUI, and give the window a name
        JFrame frame = new JFrame("AnimatedScribbles");
        frame.setPreferredSize(new Dimension(1200, 1200));

        // tell the JFrame that when someone closes the
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controlPanel = new JPanel();
        clear = new JCheckBox("Clear");
        clear.setEnabled(true);

        panel = new JPanel(new BorderLayout());

        // JPanel with a paintComponent method
        drawingPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {

                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);

                // redraw our graphics items
                redraw(g);

            }
        };
        frame.add(panel);

        controlPanel.add(clear);

        panel.add(drawingPanel,BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        drawingPanel.addMouseListener(this);
        drawingPanel.addMouseMotionListener(this);

        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Random r = new Random();
        // temp = 0; r.nextInt(5);
        lastMouse = e.getPoint();

        Point p[] = new Point[1];
        p[0] = lastMouse;

        if(temp == 0){
            AnimatedShape shape = new VanishingCircle(p[0], drawingPanel);
            shapes.add(shape);
            shape.start();
        }

        drawingPanel.repaint();
    }

    // @Override
    // public void mouseDragged(MouseEvent e) {

    // Point p[] = new Point[2];
    // p[0] = lastMouse;
    // lastMouse = e.getPoint();
    // p[1] = lastMouse;

    // if(temp == 0){
    // AnimatedShape shape = new VanishingCircle(p[0], p[1], panel);
    // shapes.add(shape);
    // shape.start();
    // }

    // panel.repaint();
    // }


    @Override
    public void mouseExited(MouseEvent e) {
        if(clear.isSelected()){
            shapes.clear();
            drawingPanel.repaint();
        }
    }

    public static void main(String args[]) {

        // create the snowflake image that will be used by the
        // FallingSnow objects
        VanishingCircle.loadCirclePic();
        

        javax.swing.SwingUtilities.invokeLater(new CrazyShapes());
    }
}


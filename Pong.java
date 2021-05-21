import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
/**
 * This is the main program for the pong game.  It will create the panel and also call the other classes associated with
 * the pong game in order to start it.
 *
 * @author Zohaib Asif, Kyra Griffin, Ashely Abrams
 */
public class Pong implements KeyListener,MouseListener,MouseMotionListener,Runnable {

  private Pong pong  = this;

  private final int WINDOW_WIDTH = 640;
  private final int WINDOW_HEIGHT = 480;

  private Font smallFont;
  private Font largeFont;
  private Button pongButton;
  private Button startButton;
  private Button howToPlayButton;
  private boolean howToPlay;
  private Button restartButton;

  private boolean running;
  private Thread thread;
  private String gameState;

  private JPanel panel;
  private Paddle paddleOne;
  private Paddle paddleTwo;
  private Ball ball;
  private int[] keys;
  private boolean paddleOneWon;
  private boolean paddleTwoWon;


  /**
   * Runs an initialization function to construct the pong game.
   *
   */
  public Pong() {
    init();
  }

  /**
   * Initialization method used by constructor to construct the pong game.  This method utilizes threads.  The first 
   * thread initializes the pong game and its run method is used to make the panel (initThread). 
   * The other thread (thread) is used to start the game when the start button is pressed.
   *
   */
  public void init() {
    Thread initThread = new Thread(new Runnable() {
      @Override
      public void run() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameState = "Start";

        smallFont = new Font("Helvetica", Font.BOLD,24);
        largeFont = new Font("Helvetica", Font.BOLD,32);
        pongButton = new Button("PONG",WINDOW_WIDTH/2-70, 50,Color.WHITE,new Font("Helvetica", Font.BOLD,45));
        startButton = new Button("START",WINDOW_WIDTH/2-50, WINDOW_HEIGHT/2,Color.WHITE,smallFont);
        howToPlayButton = new Button("HOW TO PLAY",WINDOW_WIDTH/2-50, WINDOW_HEIGHT/2 + 50,Color.WHITE,smallFont);
        howToPlay = false;
        restartButton = new Button("RESTART",WINDOW_WIDTH/2-200+20, WINDOW_HEIGHT/2,Color.BLACK,smallFont);

        paddleOne = new Paddle(20,WINDOW_HEIGHT/2 - 30);
        paddleTwo = new Paddle(WINDOW_WIDTH - 30,WINDOW_HEIGHT/2 - 30);
        ball = new Ball(WINDOW_WIDTH/2 - 3,WINDOW_HEIGHT/2 - 3);
        thread = new Thread(pong,"Pong");
        keys = new int[2];
        paddleOneWon = false;
        paddleTwoWon = false;

        panel = new JPanel() {
          @Override
          public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(gameState.equals("Start")) {
              drawStartScreen(g);
            }
            else if(gameState.equals("playing") && !paddleOneWon && !paddleTwoWon) {
              drawGame(g);
            }
            else if(gameState.equals("paused")) {
              drawMenu(g);
            }
            else if(gameState.equals("playing") || paddleOneWon || paddleTwoWon) {
              drawWinScreen(g);
            }
          }
        };

        panel.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        frame.addKeyListener(pong);
        frame.addMouseListener(pong);
        frame.addMouseMotionListener(pong);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }
    });
    initThread.start();
    try {
      initThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    running = true;
  }

  /**
   * This run method is used to keep track of the state of the game.  It also utilizes a thread (pauseThread), which
   * is run when the player pauses the game.  
   * 
   * This method is also used while the game is unpaused and being played.  If the user presses the up or down key 
   * the paddle is moved and the speed is adjust with respect to how close it is to the top or bottom of the window.
   * 
   * This method also controls the speed and movement of the AI (computers) paddle.
   * 
   * This method is also used to detect collisions between paddles and the ball.
   * 
   * Check to see if anyone has won yet.  If any player has a score of 10 they win the game.
   *
   */
  @Override
  public void run() {
    while(running) {
      if(gameState.equals("paused")) {
        Thread pauseThread = new Thread(new Runnable() {
          @Override
          public void run() {
            while(gameState.equals("paused")) {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              System.out.println("paused");
            }
            System.out.println("unpaused");
          }
        });
        pauseThread.start();
        try {
          pauseThread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      try {
      Thread.sleep(33);
    }
    catch(InterruptedException e) {
      System.out.println("Error: " + e);
    }

      if(keys[0] == 38) {
        if(paddleOne.speed > -paddleOne.MAX_SPEED) {
          paddleOne.speed -= paddleOne.acc;
        }
      }
      if(keys[1] == 40) {
        if(paddleOne.speed < paddleOne.MAX_SPEED) {
          paddleOne.speed += paddleOne.acc;
        }
      }
      if(keys[0] != 38 && keys[1] != 40) {
        if(paddleOne.speed > 0) {
          paddleOne.speed -= paddleOne.acc;
        }
        else if(paddleOne.speed < 0) {
          paddleOne.speed += paddleOne.acc;
        }
      }

      if(ball.x > WINDOW_WIDTH/2 && ball.speedX > 0) {
        if (ball.y - ball.HEIGHT / 2 < paddleTwo.y) {
          paddleTwo.speed -= paddleTwo.acc;
        } else if (ball.y - ball.HEIGHT / 2 > paddleTwo.y - paddleTwo.HEIGHT) {
          paddleTwo.speed += paddleTwo.acc;
        }
      }
      else {
        if(paddleTwo.speed > 0) {
          paddleTwo.speed -= paddleTwo.acc;
        }
        else if(paddleTwo.speed < 0) {
          paddleTwo.speed += paddleTwo.acc;
        }
      }

      paddleOne.y += paddleOne.speed;
      paddleTwo.y += paddleTwo.speed;
      ball.x += ball.speedX;
      ball.y += ball.speedY;

      paddleOne.contain(WINDOW_HEIGHT);
      paddleTwo.contain(WINDOW_HEIGHT);
      ball.contain(WINDOW_WIDTH,WINDOW_HEIGHT,paddleOne,paddleTwo);

      if(Collision.detectCollision(paddleOne,ball)) {
        ball.speedX *= -1;
        if(paddleOne.speed != 0) {
          ball.speedY += paddleOne.speed / 2;
        }
        else {
          ball.speedX += 1;
        }
        ball.x = paddleOne.x + paddleOne.WIDTH;
      }
      else if(Collision.detectCollision(paddleTwo,ball)) {
        ball.speedX *= -1;
        if(paddleOne.speed != 0) {
          ball.speedY += paddleTwo.speed / 2;
        }
        else {
          ball.speedX -= 1;
        }
        ball.x = paddleTwo.x - ball.WIDTH;
      }

      checkWin();
      panel.repaint();
    }
  }

  /**
   * Used to map the keys (if there the up and down keys) to an array in order to maintain event conditions.
   *
   * @param e event
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == 38) {
      keys[0] = 38;
    }
    if(e.getKeyCode() == 40) {
      keys[1] = 40;
    }
  }

  /**
   * If the up or down keys are released there respective array indicies values are set to 0.
   * If the user press the p button the game is paused or unpaused.
   *
   * @param e event 
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if(e.getKeyCode() == 38) {
      keys[0] = 0;
    }
    if(e.getKeyCode() == 40) {
      keys[1] = 0;
    }
    if(e.getKeyCode() == 80) {
      if(gameState != "paused") {
        gameState = "paused";
      }
      else {
        gameState = "playing";
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {}
  @Override
  public void mouseClicked(MouseEvent e) {}

  /**
   * Used to start the game or pause the game.  If the user presses the start button the start thread ("thread" that was declarded in the 
   * init() method.  If the game is paused and the user has pressed the restart button the game is reset.
   *
   * @param e A parameter
   */
  @Override
  public void mousePressed(MouseEvent e) {
    if(gameState.equals("Start")) {
      if(e.getY() > startButton.y && e.getY() < startButton.y + 24) {
        gameState = "playing";
        thread.start();
      }
    }
    if(gameState.equals("paused") || paddleOneWon || paddleTwoWon) {
      if(e.getY() > restartButton.y && e.getY() < restartButton.y + 24) {
        restart();
        gameState = "playing";
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {}
  @Override
  public void mouseEntered(MouseEvent e) {}
  @Override
  public void mouseExited(MouseEvent e) {}
  @Override
  public void mouseDragged(MouseEvent e) {}

  /**
   * Used to give the buttons slight effects.  Makes the start, restart, and how to play buttons font increase when
   * the mouse moves over them.
   *
   * @param e event
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    if(gameState.equals("Start") || gameState.equals("paused") || paddleOneWon || paddleTwoWon) {
      if(e.getY() > startButton.y && e.getY() < startButton.y + 32) {
        startButton.font = largeFont;
      }
      else {
        startButton.font = smallFont;
      }
      if(e.getY() > howToPlayButton.y && e.getY() < howToPlayButton.y + 32) {
        howToPlayButton.font = largeFont;
        howToPlay = true;
      }
      else {
        howToPlayButton.font = smallFont;
        howToPlay = false;
      }
      if(e.getY() > restartButton.y && e.getY() < restartButton.y + 32) {
        restartButton.font = largeFont;
      }
      else {
        restartButton.font = smallFont;
      }
      panel.repaint();
    }
  }

  /**
   * Method used to check if the ai or playe has won.  If either of the paddles scores reaches 10 then that respective
   * paddle (player) has won, and the game is reset.
   *
   */
  public void checkWin() {
    if(paddleOne.score == 10) {
      ball.speedX = 0;
      ball.speedY = 0;
      paddleOne.score = 0;
      paddleTwo.score = 0;
      paddleOneWon = true;
    }
    else if(paddleTwo.score == 10) {
      ball.speedX = 0;
      ball.speedY = 0;
      paddleOne.score = 0;
      paddleTwo.score = 0;
      paddleTwoWon = true;
    }
  }

  /**
   * Method used to restart the game when the restart button has been pressed.
   *
   */
  public void restart() {
    ball.x = WINDOW_WIDTH/2 - 5;
    ball.y = WINDOW_HEIGHT/2 - 5;
    ball.speedX = 0;
    ball.speedY = 0;
    paddleOne.score = 0;
    paddleTwo.score = 0;
    ball.setSpeed();
    paddleOneWon = false;
    paddleTwoWon = false;
    paddleOne.x = 20;
    paddleOne.y = WINDOW_HEIGHT/2;
    paddleTwo.x = WINDOW_WIDTH - 30;
    paddleTwo.y = WINDOW_HEIGHT/2 - 30;
  }

  /**
   * This method is used to create the start screen. The display you see when you compile and run the game for the first
   * time.  Puts title, start and how to buttons on the screen.  if the user goes over the how to play another string is
   * displayed that states how to play the game.
   *
   * @param g graphics panel
   */
  public void drawStartScreen(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
    pongButton.draw(g);
    startButton.draw(g);
    howToPlayButton.draw(g);
    if(howToPlay) {
      g.setFont(new Font("Helvetica",Font.BOLD,14));
      g.drawString("USE UP AND DOWN ARROW KEYS TO MOVE AND P FOR PAUSING THE GAME",50,WINDOW_HEIGHT/2+200);
    }
  }

  /**
   * Method used to draw the game when it is restarted/started.  The paddles and ball are drawn to the screen.  The score
   * is also displayed using this method.
   *
   * @param g graphics panel
   */
  public void drawGame(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
    paddleOne.draw(g);
    paddleTwo.draw(g);
    ball.draw(g);
    paddleOne.displayScore(g,100,50);
    paddleTwo.displayScore(g,WINDOW_WIDTH-150,50);
  }

  /**
   * Method used to draw the menue when the pause button has been pressed. Draws the how to play and restart buttons 
   * on to the screen.
   *
   * @param g graphics panel
   */
  public void drawMenu(Graphics g) {
    drawGame(g);
    g.setColor(Color.WHITE);
    g.fillRect(WINDOW_WIDTH/2-200,WINDOW_HEIGHT/2-150,400,300);
    howToPlayButton.color = Color.BLACK;
    howToPlayButton.x = WINDOW_WIDTH/2-200+20;
    howToPlayButton.y = WINDOW_HEIGHT/2-100;
    howToPlayButton.draw(g);
    restartButton.draw(g);
    if(howToPlay) {
      g.setColor(Color.WHITE);
      g.setFont(new Font("Helvetica",Font.BOLD,14));
      g.drawString("USE UP AND DOWN ARROW KEYS TO MOVE AND P FOR PAUSING THE GAME",50,WINDOW_HEIGHT/2+200);
    }

  }

  /**
   * Method used to draw the win screen when either the player has won or the ai has won.  If the player has won
   * "You Won!" is displayed.  if the player lost "You Lost" is displayed.  This method also draws the restart button 
   * on to the screen.
   *
   * @param g graphics panel
   */
  public void drawWinScreen(Graphics g) {
    drawGame(g);
    g.setColor(Color.WHITE);
    g.fillRect(WINDOW_WIDTH/2-200,WINDOW_HEIGHT/2-150,400,300);
    g.setColor(Color.BLACK);
    g.setFont(smallFont);
    if(paddleOneWon) {
      g.drawString("You won!",WINDOW_WIDTH/2-100,WINDOW_HEIGHT/2+100);
    }
    else {
      g.drawString("You lost:(",WINDOW_WIDTH/2-100,WINDOW_HEIGHT/2+100);
    }
    restartButton.draw(g);
  }

  /**
   * main method used to start the pong game.
   */
  public static void main(String[] args) {
    Pong pong = new Pong();
  }
}

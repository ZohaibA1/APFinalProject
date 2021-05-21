/**
 * This prorgam checks for collision between either the user/ai's paddle
 * and the battle.  To find out wether the ball has been hit or not
 * by the players paddle
 *
 * @author Zohaib Asif, Kyra Griffin, Ashely Abrams
 */
public class Collision {
    /**
     * Method detectCollision
     *
     * @param p a paddle (users/ai's)
     * @param b the ball
     * @return boolean whether the paddle hit the ball or not
     */
    public static boolean detectCollision(Paddle p, Ball b) {
        if(p.x + p.WIDTH > b.x && p.x < b.x + b.WIDTH && p.y + p.HEIGHT > b.y && p.y < b.y + b.HEIGHT ) {
            return true;
        }
        return false;
    }
}

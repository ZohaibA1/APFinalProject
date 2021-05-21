import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
/**
 * A class designed to implement functionality as part of a fully functional poker game, and possibly a Blackjack game.
 * This class will represent the Dealer in a poker or blackjack game, as they are represented in the real world.
 *
 * @author Mark Verra
 * @version Spring 2021
 */
public class Dealer
{
    // private instance var to represent the deck of cards before shuffling.
    private ArrayList deck;
    // private instance var to represent the shuffled deck of cards
    private Stack shuffledDeck;
    
    private static Random r = new Random();
    public void createDeck()
    {
        for(int index = 0; index < 4; index++)
        {
            for(int i = 1; i <= 13; i++)
            {
                if(index == 0) deck.add(new Card("Hearts", i));
                if(index == 1) deck.add(new Card("Diamonds", i));
                if(index == 2) deck.add(new Card("Spades", i));
                if(index == 3) deck.add(new Card("Clubs", i));
            }
        }
        
        shuffledDeck.push(deck.remove(r.nextInt(deck.size())));
    }
}

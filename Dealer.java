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
    private ArrayList<Card> deck;
    // private instance var to represent the shuffled deck of cards
    private Stack<Card> shuffledDeck;

    private static Random r = new Random();
    public void createDeck()
    {
        deck = new ArrayList<Card>(52);
        Card card;
        for(int index = 0; index < 4; index++)
        {
            for(int i = 1; i <= 13; i++)
            {
                if(index == 0) 
                {
                    card = new Card("Hearts", i);
                    card.setRank();
                    deck.add(card);
                }

                if(index == 1) 
                {
                    card = new Card("Diamonds", i);
                    card.setRank();
                    deck.add(card);
                }

                if(index == 2) 
                {
                    card = new Card("Spades", i);
                    card.setRank();
                    deck.add(card); 
                }

                if(index == 3) 
                {
                    card = new Card("Clubs", i);
                    card.setRank();
                    deck.add(card);
                }
            }
        }

        shuffledDeck = new Stack<Card>();
        while(!deck.isEmpty())
        {
            shuffledDeck.push(deck.remove(r.nextInt(deck.size())));
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(Card card : shuffledDeck)
        {
            sb.append(card.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

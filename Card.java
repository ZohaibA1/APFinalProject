
/**
 * A class designed to implement functionality as part of a fully functional poker game, and possibly a Blackjack game.
 * This class will represent Card objects in a poker or blackjack game, as they are represented in the real world.
 *
 * @author Mark Verra
 * @version Spring 2021
 */
public class Card implements Comparable
{
    // instance variable to denote a given card's suit (e.g., Hearts, Diamonds, etc.)
    private String suit;
    // instance variable to denote a given card's rank (e.g., 2's, 10's, Kings, etc.)
    private String rank;
    // instance variable to denote a given card's value (e.g, 1, 2, 3, 11, etc)
    private int value;
    
    
    public Card()
    {
        
    }
    
    public Card(String suit, int value)
    {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }
    
    public Card(String suit, String rank, int value)
    {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }
    
    // ACCESSOR METHODS
    public String getSuit()
    {
        return suit;
    }
    
    public String getRank()
    {
        return rank;
    }
    
    public int getValue()
    {
        return value;
    }
    
    @Override
    public int compareTo(Object obj)
    {
        if(obj instanceof Card) 
        {
            Card card2 = (Card) obj;
            
            if(this.value < card2.value) return -1;
            if(this.value == card2.value) return 0;
            if(this.value > card2.value) return 1;
        }
        
        return 0;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Card) 
        {
            Card card2 = (Card) obj;
            
            if((this.suit.equals(card2.suit)) && (this.rank.equals(card2.rank)) && (this.value == card2.value))
            {
                return true;
            }
            
            else return false;
        }
        
        else return false;
    }
    
}

package blackjackgame;
import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
    private static final Deck instance = new Deck(); // new static instance of Deck
    private final ArrayList<Card> cardDeck = new ArrayList<>( ); // holds all cards

    //constructor
    private Deck()
    {
        loadDeck();
    }

    private void loadDeck()
    {
        String [] suits = {"\u2660", "\u2663", "\u2665", "\u2666"}; // suit in unicode
        String [] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "J", "Q", "K"};
        ArrayList<ArrayList<Integer>> value = new ArrayList<>( ); // multidimensional ArrayList for card values

        String newSuit; // for a given card
        String newRank; // for a given card

        for ( String suit : suits )// suits loop
        {
            newSuit = suit;
            for ( int j = 0; j < ranks.length; j++ ) // ranks loop
            {
                newRank = ranks[ j ];
                value.add( j, new ArrayList<>( ) );
                switch ( newRank ) // value based on rank
                {
                    case "K", "Q", "J", "10" -> value.get( j ).add( 0, 10 );
                    case "A" -> {
                        value.get( j ).add( 0, 1 );
                        value.get( j ).add( 1, 11 );
                    }
                    default -> value.get( j ).add( 0, Integer.parseInt( newRank ) );
                }
                cardDeck.add( new Card( newSuit, newRank, value.get( j ) ) );
            }
        }
    }

    public static Deck getInstance()
    {
        return instance;
    }

    public ArrayList<Card> getCardDeck()
    {
        return instance.cardDeck;
    }

    public void shuffle()
    {
        Collections.shuffle( cardDeck );
        System.out.println("Card deck reset and shuffled.");
    }
}

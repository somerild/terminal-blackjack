package blackjackgame;
import java.util.ArrayList;
import static blackjackgame.Deck.getInstance;

public class Hand
{
    private final Deck instance;
    private final ArrayList<Card> deck;
    private Card card; // individual card in a hand
    private final ArrayList<Card> hand; // ArrayList for all the cards in a hand
    private final int BLACKJACK = 21;
    private ArrayList<Integer> cardPipValue; // ArrayList for card values

    //constructor
    Hand ()
    {
        instance = getInstance( );
        deck = instance.getCardDeck();
        hand = new ArrayList<>();
    }

    public void deal()
    {
        card = deck.remove(0);
        hand.add(card);
        card = deck.remove( 2 );
        hand.add( card );
    }

    public boolean stand17()
    {
        boolean stand = false;

        int DEALERSTAND = 17;

        if(maxValue()<BLACKJACK & maxValue() < DEALERSTAND ) stand = false; //stand remains false, 1-16 with max value
        if(maxValue()<BLACKJACK & maxValue() >= DEALERSTAND )
        {
            stand = true; //17-20 with max value
            System.out.println("---Dealer stands---" );
        }
        if(maxValue()>BLACKJACK)
        {
            if(minValue()<BLACKJACK & minValue()< DEALERSTAND ) stand = false; //1-16 with min value
            if(minValue()<BLACKJACK & minValue()>= DEALERSTAND )
            {
                stand = true; //17-20 with min value
                System.out.println("---Dealer stands---" );
            }
        }
        return stand;
    }

    public boolean blackJack()
    {
        boolean playOver=false;

        if(maxValue() > minValue()) System.out.println( "Soft hand." );
        if(maxValue() > BLACKJACK) System.out.println("Total: " + minValue());
        if(maxValue() < BLACKJACK) System.out.println( "Total: " + maxValue());
        if(maxValue() == BLACKJACK | minValue() == BLACKJACK)
        {
            System.out.println( "Total: 21" );
            playOver = true; //blackjack
        }
        if (maxValue() > BLACKJACK & minValue() > BLACKJACK)
        {
            playOver = true; //busted
        }
        return playOver;
    }

    public int minValue()
    {
        int minTotal = 0; // minimum hand value

        for ( Card value : hand )
        {
            int lowValue;
            card = value;
            cardPipValue = card.getPipValue( );
            lowValue = cardPipValue.get( 0 );
            minTotal += lowValue;
        }
        return minTotal;
    }

    public int maxValue()
    {
        int maxTotal = 0; // maximum hand value

        for ( Card value : hand )
        {
            int highValue;
            card = value;
            cardPipValue = card.getPipValue( );
            try
            {
                highValue = cardPipValue.get( 1 );
            } catch ( IndexOutOfBoundsException e )
            {
                highValue = cardPipValue.get( 0 );
            }
            maxTotal += highValue;
        }
        return maxTotal;
    }

    public void hit()
    {
        card = deck.remove(0);
        hand.add(card);
    }

    public void showHand()
    {
        for ( Card value : hand )
        {
            card = value;
            System.out.print( card + " " );
        }
        System.out.println(  );
    }

    public void showUpCard()
    {
        card = hand.get(1);
        System.out.println( card + "   ? " );
    }
}

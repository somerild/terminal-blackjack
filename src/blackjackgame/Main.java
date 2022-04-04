package blackjackgame;
import java.util.InputMismatchException;
import java.util.Scanner;
import static blackjackgame.Deck.getInstance;

public class Main
{
    static boolean anotherHand(Scanner input)
    {
        String handResponse;
        boolean hand;

        System.out.println("Would you like to play another hand? (Y/N)");
        handResponse = input.nextLine( );
        hand = handResponse.equals( "y" ) | handResponse.equals( "Y" );
        return hand;
    }

    static boolean hitPrompt( Scanner input )
    {
        String hitResponse;
        boolean hit = false;

        System.out.println( "Hit? (Y/N)" );
        hitResponse = input.nextLine();
        if(hitResponse.equals( "y" ) | hitResponse.equals( "Y" )) hit = true;
        return hit;
    }

    static int result(Hand playerHand, Hand dealerHand)
    {
        final int BLACKJACK = 21;
        int endResult = -1; // -1 tie, 1 player lost, 0 player won
        boolean bust = false;
        boolean blackjack = false;

        //bust cases
        if(playerHand.minValue() > BLACKJACK & playerHand.maxValue() > BLACKJACK)
        {
            endResult = 1;
            bust = true;
            System.out.println( "Player busts." );
        }
        if(dealerHand.minValue() > BLACKJACK & dealerHand.minValue() > BLACKJACK)
        {
            endResult = 0;
            bust = true;
            System.out.println( "Dealer busts." );
        }

        //"natural" blackjack cases
        if(playerHand.minValue() == BLACKJACK | playerHand.maxValue() == BLACKJACK)
        {
            endResult = 0;
            blackjack = true;
            System.out.println( "Player won." );
        }
        if(dealerHand.minValue() == BLACKJACK | dealerHand.maxValue() == BLACKJACK)
        {
            endResult = 1;
            blackjack = true;
            System.out.println( "Dealer won." );
        }

        //stand cases
        else if (!bust & !blackjack)
        {
            int playerStand;
            int dealerStand;

            //take the largest hand
            if (playerHand.maxValue() < BLACKJACK) playerStand = playerHand.maxValue();
            else playerStand = playerHand.minValue();

            //take the largest hand
            if(dealerHand.maxValue() < BLACKJACK) dealerStand = dealerHand.maxValue();
            else dealerStand = dealerHand.minValue();

            System.out.println( "Player: " + playerStand + " Dealer: " + dealerStand );

            if (playerStand > dealerStand)
            {
                endResult = 0;
                System.out.println( "Player won." );
            }
            if (dealerStand > playerStand)
            {
                endResult = 1;
                System.out.println( "Dealer won.");
            }
            if (playerStand == dealerStand)
            {
                System.out.println( "Tie." );
            }
        }

        return endResult;
    }

    public static void main(String[] arg)
    {
        Scanner stringInput = new Scanner(System.in); // string input
        Scanner doubleInput = new Scanner(System.in); // double input
        double bankRoll = 100.0; // starting amount available for bets
        double bet; // bet for a given hand
        boolean suddenResult; // stops the hand if a sudden blackjack or bust occurs
        boolean hand; // does the player want to play another hand

        System.out.println( "\nLet's play some Blackjack.\n" );
        do
        {
            //initialize for each hand
            Hand player = new Hand(); // new player hand
            Hand dealer = new Hand(); // new dealer hand
            Deck instance = getInstance(); // retrieve card deck
            int result; // the result of the hand: -1 tie, 1 player lost, 0 player won

            //place bet
            if ( bankRoll > 0 ) // check if funds are still available.
            {
                System.out.println( "$" + bankRoll + " available. Enter your bet. " );
                try
                {
                    bet = doubleInput.nextDouble( );
                    while ( bet <= 0 | bet > bankRoll )
                    {
                        System.out.println("Invalid bet. Enter a new amount." );
                        bet = doubleInput.nextDouble( );
                    }
                }
                catch ( InputMismatchException e )
                {
                    System.out.println("Invalid input. Enter new amount. " );
                    doubleInput.reset(); //reset scanner
                    doubleInput.next(); //clear the string
                    try
                    {
                        bet = doubleInput.nextDouble( );
                        while ( bet <= 0 | bet > bankRoll )
                        {
                            System.out.println("Invalid bet. Enter a new amount." );
                            bet = doubleInput.nextDouble( );
                        }
                    }catch ( InputMismatchException e1 )
                    {
                        break; //ends the game if two invalid inputs
                    }
                }
            }
            else
            {
                System.out.println( "Insufficient funds to place a bet.  End of game." );
                break;
            }

            //shuffle
            instance.shuffle();

            //initial deal
            player.deal();
            dealer.deal();

            //show hands
            System.out.println( "Dealer's hand: " );
            dealer.showUpCard();
            System.out.println( "Player's hand: " );
            player.showHand();

            //player's play
            suddenResult = player.blackJack();
            if(!suddenResult) // check if blackjack or bust occurred before hitting
            {
                while ( hitPrompt( stringInput ) ) // while player enters "yes" in response to "Hit?"
                {
                    player.hit();
                    player.showHand();
                    suddenResult = player.blackJack();
                    if(suddenResult) break;
                }
            }

            //dealer's play
            if(!suddenResult) // check if blackjack or bust occurred before the play
            {
                System.out.println( "---Player stands---" );
                System.out.println( "Dealer's hand: " );
                dealer.showHand();
                suddenResult = dealer.blackJack();
                while (!suddenResult & !dealer.stand17()) //while dealer's hand is less than 17, hit
                {
                    System.out.println( "Dealer hits." );
                    dealer.hit();
                    dealer.showHand();
                    suddenResult = dealer.blackJack();
                }
            }

            //results and settle bet
            result = result( player, dealer );
            switch ( result )
            {
                case 1 -> bankRoll -= bet; //player lost
                case 0 -> bankRoll += bet; //player won
            }
            //prompt for another hand
            hand = anotherHand( stringInput );

        }while(hand);
    }
}

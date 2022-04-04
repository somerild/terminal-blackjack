package blackjackgame;
import java.util.ArrayList;

public class Card
{
    private final String suit; // spade, club, heart, diamond
    private final String rank; // K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2, A
    private final ArrayList<Integer> pipValue; // card value (aces can be 1 or 11)

    //constructor
    Card(String newSuit, String newRank, ArrayList<Integer> newPipValue)
    {
        suit = newSuit;
        rank = newRank;
        pipValue = newPipValue;
    }

    private String getSuit()
    {
        return suit;
    }

    private String getRank()
    {
        return rank;
    }

    public ArrayList<Integer> getPipValue()
    {
        return pipValue;
    }

    public String toString()
    {
        return (getSuit() + " " + getRank());
    }
}

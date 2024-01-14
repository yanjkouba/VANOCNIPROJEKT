import java.awt.image.*;

public class CardSet
{
    public static final int NO_OF_CARDS = 4;
    public static final int FULL_DECK = 52;
    public static final int FULL_SUIT = 13;

    private static int totalScore;
    private static int score;
    /** Card array */
    private Card[] dealtCards;
    /** Stores numbers of cards */
    private int[] cardNumbers;
    private BufferedImage[] cardImagesForHand;
    private String winningHand;

    // Total score static, so it doesn't change very new game
    static{
        totalScore = 0;
    }

    /** Card set constructor */
    public CardSet()
    {
        dealtCards = new Card[NO_OF_CARDS];
        cardNumbers = new int[NO_OF_CARDS];
        cardImagesForHand = new BufferedImage[NO_OF_CARDS];
        score = 0;

        for(int i=0; i<NO_OF_CARDS; i++)
            dealtCards[i] = new Card();
    }

    //Returns the score
    public int getScore()
    {
        return score;
    }

    //Returns the total score
    public int getTotalScore()
    {
        return totalScore;
    }

    //Resets the score back to zero
    public void resetScore()
    {
        score = 0;
    }

    //Resets the total score back to zero
    public void resetTotalScore()
    {
        totalScore = 0;
    }

    public String getWinningHand()
    {
        return winningHand;
    }

    /** Returns the image that was picked from the card object */
    public BufferedImage getImagesOfPickedCards(int cardNumber)
    {
        return cardImagesForHand[cardNumber];
    }

    /** Method that resets scores */
    public void clearScores()
    {
        totalScore = 0;
        score = 0;
    }

    /** The deal method returns an image of the card
        and also gets the card number and puts it into an array */
    public BufferedImage deal(int cardNumber)
    {
        //Array of images that have been picked from the card objects
        cardImagesForHand[cardNumber] = dealtCards[cardNumber].pick();
        //the method getCardNumber returns the element number of the array of images
        cardNumbers[cardNumber] = (dealtCards[cardNumber].getCardNumber()+1);
        return cardImagesForHand[cardNumber];
    }

    /** Determines the suit type for each card*/
    public void determineSuit()
    {
        int var = 1;
        int range;
        String suitType = null;

        while(var<NO_OF_CARDS+1){
            range = var;

            if(var==1) suitType = "Club";
            if(var==2) suitType = "Diamond";
            if(var==3) suitType = "Heart";
            if(var==4) suitType = "Spade";

            for(int i=0; i<FULL_SUIT; i++){
                for(int j=0; j<NO_OF_CARDS; j++){
                    if(range == cardNumbers[j])
                        dealtCards[j].setCardSuit(suitType);
                }
                range+=NO_OF_CARDS;
            }
            var++;
        }
    }

    /** Determines the value of each card */
    public void determineValue()
    {
        int value = 1;
        int lowRange = 1;
        int highRange = 4;

        for(int i=0; i<FULL_SUIT; i++){
            for(int j=0; j<NO_OF_CARDS; j++){
                if(cardNumbers[j] >= lowRange && cardNumbers[j] <= highRange)
                    dealtCards[j].setCardValue(value);
            }
            value++;
            lowRange+=NO_OF_CARDS;
            highRange+=NO_OF_CARDS;
        }
    }

    /** Checks what hand the user has and updates the score according to it */
    public void updateScore()
    {
        int count = 0;
        while(count!=1){
            if(checkRoyalFlush()){
                winningHand = "Royal Flush";
                score+=800;
                break;
            }
            else if(checkStraightFlush()){
                winningHand = "Straight Flush";
                score+=500;
                break;
            }
            else if(checkFourOfAKind()){
                winningHand = "Four of a Kind";
                score+=350;
                break;
            }
            else if(checkFlush()){
                winningHand = "Flush";
                score+=200;
                break;
            }
            else if(checkStraight()){
                winningHand = "Straight";
                score+=100;
                break;
            }
            else if(checkThreeOfAKind()){
                winningHand = "Three of a Kind";
                score+=75;
                break;
            }
            else if(checkTwoPair()){
                winningHand = "Two Pairs";
                score+=50;
                break;
            }
            else if(checkOnePair()){
                winningHand = "Pair";
                score+=10;
                break;
            }
            else winningHand = "You lose";
            count = 1;
        }
        totalScore+=score;
    }

    /** Checks if the user has one pair */
    public boolean checkOnePair()
    {
        int[] cardValue = new int[NO_OF_CARDS];
        for(int i=0; i<NO_OF_CARDS; i++)
            cardValue[i] = dealtCards[i].getCardValue();

        if(cardValue[0] == cardValue[1])
            return true;
        else if(cardValue[2] == cardValue[3])
            return true;
        else if(cardValue[1] == cardValue[2])
            return true;
        else if(cardValue[0] == cardValue[3])
            return true;
        else if(cardValue[0] == cardValue[2])
            return true;
        else if(cardValue[1] == cardValue[3])
            return true;
        else return false;
    }

    /** Checks for a two pair */
    public boolean checkTwoPair()
    {
        int[] cardValue = new int[NO_OF_CARDS];
        for(int i=0; i<NO_OF_CARDS; i++)
            cardValue[i] = dealtCards[i].getCardValue();

        if(cardValue[0] == cardValue[1] && cardValue[2] == cardValue[3])
            return true;
        else if(cardValue[1] == cardValue[2] && cardValue[0] == cardValue[3])
            return true;
        else return false;
    }

    /** Checks for a three of a kind */
    public boolean checkThreeOfAKind()
    {
        int[] cardValue = new int[NO_OF_CARDS];
        for(int i=0; i<NO_OF_CARDS; i++)
            cardValue[i] = dealtCards[i].getCardValue();

        if(cardValue[0] == cardValue[1] && cardValue[1] == cardValue[2])
            return true;
        else if(cardValue[1] == cardValue[2] && cardValue[2] == cardValue[3])
            return true;
        else if(cardValue[0] == cardValue[2] && cardValue[2] == cardValue[3])
            return true;
        else if(cardValue[0] == cardValue[1] && cardValue[1] == cardValue[3])
            return true;
        else return false;
    }

    /** Checks for a straight */
    public boolean checkStraight()
    {
        int[] cardValue = new int[NO_OF_CARDS];
        for(int i=0; i<NO_OF_CARDS; i++)
            cardValue[i] = dealtCards[i].getCardValue();

        int temp, j, k= 0;

        for(j=1; j<NO_OF_CARDS; j++){
            temp = cardValue[j];
            k=j;
            while(k>0 && cardValue[k-1]>temp){
                cardValue[k] = cardValue[k-1];
                k--;
            }
            cardValue[k] = temp;
        }

        if( (cardValue[0] == (cardValue[1]-1))&&
                (cardValue[1] == (cardValue[2]-1))&&
                (cardValue[2] == (cardValue[3]-1)))
            return true;
        else return false;
    }

    /** Checks if the user has a flush */
    public boolean checkFlush()
    {
        String[] cardSuit = new String[NO_OF_CARDS];
        for(int i=0; i<NO_OF_CARDS; i++)
            cardSuit[i] = dealtCards[i].getCardSuit();

        if( (cardSuit[0].equals(cardSuit[1]))&&
                (cardSuit[1].equals(cardSuit[2]))&&
                (cardSuit[2].equals(cardSuit[3])))
            return true;
        else return false;
    }

    /** Checks for a four of a kind */
    public boolean checkFourOfAKind()
    {
        int[] cardValue = new int[NO_OF_CARDS];
        for(int i=0; i<NO_OF_CARDS; i++)
            cardValue[i] = dealtCards[i].getCardValue();

        int count=0;
        for(int i=1; i<NO_OF_CARDS; i++){
            if(cardValue[0] == cardValue[i])
                count++;
        }
        if(count==3) return true;
        else return false;
    }

    /** Checks for a straight flush */
    public boolean checkStraightFlush()
    {
        if(checkStraight() && checkFlush())
            return true;
        else return false;
    }

    /** Checks for a royael flush */
    public boolean checkRoyalFlush()
    {
        int check = 0;
        if(checkFlush()){
            int[] cardValue = new int[NO_OF_CARDS];
            for(int i=0; i<NO_OF_CARDS; i++)
                cardValue[i] = dealtCards[i].getCardValue();

            int temp, j, k= 0;

            for(j=1; j<NO_OF_CARDS; j++){
                temp = cardValue[j];
                k=j;
                while(k>0 && cardValue[k-1]>temp){
                    cardValue[k] = cardValue[k-1];
                    k--;
                }
                cardValue[k] = temp;
            }
        }
        return false;
    }
}


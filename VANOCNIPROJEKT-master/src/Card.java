import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Card
{
    /** All images of cards */
    static private BufferedImage[] cardsArray;
    /** Sprites */
    private BufferedImage cardsSpriteImage;
    private int cardNumber;
    private String cardSuit;
    private int cardValue;

    /** Loads images and puts them into an array */
    public Card()
    {
        cardsArray = loadImage("VANOCNIPROJEKT-master/images/cards.png");
    }

    /** Splits the cards up */
    private BufferedImage[] loadImage(String fileName)
    {
        //Exception if the image cant be loaded
        try{
            cardsSpriteImage = ImageIO.read(new File(fileName));
        }catch (Exception e){
            System.out.print("Error");
        }

        //Size of cards
        final int width = 79;
        final int height = 123;
        final int rows = 4;
        final int cols = 13;

        //Size of the array
        BufferedImage[] cards = new BufferedImage[rows*cols];

        //Splits the images
        for(int i=0; i<cols; i++){
            for(int j=0; j<rows; j++){
                cards[(i*rows)+j] = cardsSpriteImage.getSubimage(
                        i*width,
                        j*height,
                        width,
                        height
                );
            }
        }
        return cards;
    }

    /** Return array of images */
    public BufferedImage[] getDeckOfCards()
    {
        return cardsArray;
    }

    public int getCardNumber()
    {
        return cardNumber;
    }

    /** Random cards */
    public BufferedImage pick()
    {
        Random generator = new Random();
        int index;

        //This makes so that two same cards dont get picked
        do{
            index = generator.nextInt(52);
            cardNumber = index;
        }while(cardsArray[index] == null);

        BufferedImage temp = cardsArray[index];
        cardsArray[index] = null;
        return temp;
    }

    /** Sets card suit */
    public void setCardSuit(String cardSuit)
    {
        this.cardSuit = cardSuit;
    }

    public void setCardValue(int cardValue)
    {
        this.cardValue = cardValue;
    }

    /** Suit of the card as string */
    public String getCardSuit()
    {
        return cardSuit;
    }

    public int getCardValue()
    {
        return cardValue;
    }
}

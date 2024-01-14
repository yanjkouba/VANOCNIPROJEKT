import java.io.*;
import java.awt.*;

public class GOTYPoker
{
    /** Starts the game */
    public static void main(String[] args ) throws IOException
    {
        CardGame sp = new CardGame("GOTYPoker");
        Image icon = Toolkit.getDefaultToolkit().getImage("VANOCNIPROJEKT-master/images/icon.png");
        sp.setIconImage(icon);
    }
}
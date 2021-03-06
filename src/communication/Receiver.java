
package communication;

import game.Player;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Instances of {@code Receiver} are used for getting data from C chess server.
 * It needs input stream, from which it will get data and parse them for client.
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Receiver
{
    /**
     * Stream for getting data from server
     */
    private BufferedReader reader;
    
    /**
     * Creates new instance of {@code Receiver}
     * 
     * @param reader Stream from which will {@code Receiver} get data
     */
    public Receiver(BufferedReader reader)
    {
        this.reader = reader;
    }
    
    /**
     * Close stream
     * 
     * @throws IOException 
     */
    public void closeConnection() throws IOException
    {
        if (reader != null)
        {
            reader.close();
        }
    }
    
    /**
     * Read data from server
     * 
     * @return Response from server
     */
    public Response getResponse()
    {
        try
        {
            String text = reader.readLine();
            if (text == null)
            {
                text = reader.readLine();
                if (text == null)
                {
                    System.out.println("Server or other player is down.");
                    System.exit(1);
                }
            }
            Response r = new Response(text);
            if (r.isPlayerStatus())
            {
                if (r.getParam().equals(Player.STATUS_DISCONNECTED))
                {
                    System.out.println("Opponent disconnected.");
                    System.exit(2);
                }
            }
            return r;
        }
        catch (IOException e)
        {
            System.out.println("Server disconnected.");
            System.exit(2);
        }
        return null;
    }
    
    /**
     * @return Stream of data
     */
    public BufferedReader getReader()
    {
        return this.reader;
    }
    
    /**
     * @param reader Stream of data
     */
    public void setReader(BufferedReader reader)
    {
        this.reader = reader;
    }
}

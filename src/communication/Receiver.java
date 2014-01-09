
package communication;

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
     * Read data from server
     * 
     * @return Response from server
     */
    public Response getResponse()
    {
        try
        {
            return new Response(reader.readLine());
        }
        catch (IOException e)
        {
            e.printStackTrace();
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

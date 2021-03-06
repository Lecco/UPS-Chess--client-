
package communication;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Instances of {@code Sender} are used for sending data to server.
 * For example moves of chess pieces.
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Sender
{
    /**
     * Stream for sending data to server
     */
    private OutputStream output;
    
    /**
     * Create new instance of sender
     * 
     * @param output Output stream
     */
    public Sender(OutputStream output)
    {
        this.output = output;
    }
    
    /**
     * Send data to server
     * 
     * @param move Move of chess piece
     * @throws IOException 
     */
    public void send(char[] move) throws IOException
    {
        for (int j = 0; j < move.length; j++)
        {
            output.write(move[j]);
        }
        output.flush();
    }
    
    /**
     * Close stream
     * 
     * @throws IOException 
     */
    public void closeConnection() throws IOException
    {
        if (output != null)
        {
            output.close();
        }
    }
    
    /**
     * Set new output stream
     * 
     * @param output New output stream
     */
    public void setOutput(OutputStream output)
    {
        this.output = output;
    }
    
    /**
     * Get output stream
     * 
     * @return Output stream
     */
    public OutputStream getOutput()
    {
        return this.output;
    }
}

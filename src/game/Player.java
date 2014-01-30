
package game;

import communication.Receiver;
import communication.Response;
import communication.Sender;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instances of {@code Player} represent chess players.
 * Instances can communicate with chess server, they know their color etc
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Player
{
    public final static String WHITE_PLAYER = "WHITE_PLAYER";
    public final static String BLACK_PLAYER = "BLACK_PLAYER";
    public final static String STATUS_DISCONNECTED = "DISCONNECTED";
    public final static String STATUS_CONNECTED = "CONNECTED";
    
    /**
     * Socket for connection to server
     */
    private Socket socket;
    
    /**
     * Receiver of data from server
     */
    private Receiver receiver;
    
    /**
     * Sender of data to server
     */
    private Sender sender;
    
    /**
     * Player color
     */
    private Color color;
    
    /**
     * Create new instance of {@code Player}
     * 
     * @param socket Socket for connecting to server 
     */
    public Player(Socket socket)
    {
        this.socket = socket;
        try
        {
            this.receiver = new Receiver(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            this.sender = new Sender(socket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Create new player with color
     * 
     * @param socket Socket for connecting to server
     * @param color Color of player 
     */
    public Player(Socket socket, Color color)
    {
        this(socket);
        this.color = color;
    }
    
    /**
     * Check if player is connected
     * 
     * @return True if user is connected
     */
    public boolean isConnected()
    {
        Response r = receiver.getResponse();
        return r.isSuccess();
    }
    
    /**
     * Send move to server and return true if it was succesful
     * 
     * @param move Move to perform
     * @return True if move was successful
     * @throws IOException 
     */
    public boolean sendMove(char[] move) throws IOException
    {
        sender.send(move);
        Response r = receiver.getResponse();
        return r.isSuccess();
    }
    
    /**
     * Send message to server and return true if it was succesful
     * 
     * @param message Message
     * @throws IOException 
     */
    public void send(String message) throws IOException
    {
        message = message + "\n";
        sender.send(message.toCharArray());
    }

    /**
     * Close connection of player to server
     */
    public void closeConnection()
    {
        System.out.println("zavirame");
        try
        {
            receiver.closeConnection();
            sender.closeConnection();
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Get parameter of response (for example message from server)
     * 
     * @return Parameter of message
     */
    public String getResponseParam()
    {
        Response r = receiver.getResponse();
        return r.getParam();
    }
    
    /**
     * @return Response from server
     */
    public Response getResponse()
    {
        Response r = receiver.getResponse();
        if (r.isPlayerStatus() && 
                ((this.getColor() == Color.WHITE && r.getType().equals(Player.WHITE_PLAYER)) ||
                 (this.getColor() == Color.BLACK && r.getType().equals(Player.BLACK_PLAYER))))
        {
            String message = this.getColor().name() + "---" + Player.STATUS_CONNECTED;
            try
            {
                this.sender.send(message.toCharArray());
            } catch (IOException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return r;
    }
    
    /**
     * Set color of player
     * 
     * @param color Color of player chesspieces
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    /**
     * Get player color
     * 
     * @return Color of player
     */
    public Color getColor()
    {
        return this.color;
    }
}

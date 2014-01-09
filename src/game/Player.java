
package game;

import communication.Receiver;
import communication.Sender;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Instances of {@code Player} represent chess players.
 * Instances can communicate with chess server, they know their color etc
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Player
{
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
     * @throws IOException 
     */
    public Player(Socket socket) throws IOException
    {
        this.socket = socket;
        this.receiver = new Receiver(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        this.sender = new Sender(socket.getOutputStream());
    }
    
    /**
     * Create new player with color
     * 
     * @param socket Socket for connecting to server
     * @param color Color of player
     * @throws IOException 
     */
    public Player(Socket socket, Color color) throws IOException
    {
        this.socket = socket;
        this.receiver = new Receiver(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        this.sender = new Sender(socket.getOutputStream());
        this.color = color;
    }
}

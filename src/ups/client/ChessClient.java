package ups.client;


import communication.Response;
import communication.ResponseParam;
import communication.ResponseType;
import game.*;
import java.net.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import sun.net.util.IPAddressUtil;

/**
 * Chess client
 * 
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class ChessClient
{

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        String ip = null;
        do
        {
            System.out.print("IP of server: ");
            ip = sc.nextLine();
            if (!IPAddressUtil.isIPv4LiteralAddress(ip) && !IPAddressUtil.isIPv6LiteralAddress(ip))
            {
                System.out.println("Not valid IP address.");
            }
        }
        while (!IPAddressUtil.isIPv4LiteralAddress(ip) && !IPAddressUtil.isIPv6LiteralAddress(ip));

        boolean portSet = false;
        int port = -1;
        while (!portSet)
        {
            try
            {
                System.out.print("\nPort: ");
                port = Integer.parseInt(sc.nextLine());
                if (port < 1024 || port >= 65536)
                {
                    throw new NumberFormatException();
                }
                portSet = true;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid port number, choose port number in range 1024-65535");
                portSet = false;
                port = -1;
            }
        }
        
        Player p = null;

        try
        {
            Response r;
            Game game = new Game(new ChessBoard());
            p = new Player(new Socket(ip, port));
            p.send(ResponseType.STATUS + Response.SEPARATOR + ResponseParam.SUCCESS);
            
            if (p.isConnected())
            {
                System.out.println("You are now connected.\n");
                if (p.getResponseParam().equals(Color.WHITE.toString()))
                {
                    System.out.println("Your color is white");
                    p.setColor(Color.WHITE);
                }
                else
                {
                    System.out.println("Your color is black");
                    p.setColor(Color.BLACK);
                }
                System.out.println("Waiting for other player...");
                r = p.getResponse();
                if (r.isPlayerStatus())
                {
                    if (r.getParam().equals(ResponseParam.CONNECTED.name()))
                    {
                        System.out.println("Other player connected.");
                    }
                }
            }
            
            String move;
            
            for (int moveNumber = 1; game.getStatus() != Game.STATUS_CHECKMATE && game.getStatus() != Game.STATUS_STALEMATE; moveNumber++)
            {
                System.out.println(game.getChessboard());
                System.out.println("MOVE " + moveNumber);
                if ((p.getColor() == Color.WHITE && moveNumber % 2 == 1) || (p.getColor() == Color.BLACK && moveNumber % 2 == 0))
                {
                    System.out.print("Your move (for example e2e4): ");
                    move = sc.nextLine();
                    if (move.length() != 4)
                    {
                        System.out.println("Invalid move.");
                        moveNumber--;
                        continue;
                    }
                    move += "\n";

                    if (!p.sendMove(move.toCharArray()))
                    {
                        r = p.getResponse();
                        if (r.isPlayerStatus())
                        {
                            if (r.getParam().equals(ResponseParam.DISCONNECTED.name()))
                            {
                                System.out.println("Other player disconnected.");
                                break;
                            }
                        }
                        if (r.isMessage())
                        {
                            System.out.println(r.getParam());
                        }
                        moveNumber--;
                        continue;
                    }
                    else
                    {
                        game.makeMove(move);
                    }
                    r = p.getResponse();
                    if (r.isMessage())
                    {
                        System.out.println(r.getParam());
                    }
                }
                else
                {
                    System.out.print("Incoming move: ");
                    
                    do 
                    {
                        r = p.getResponse();

                        if (r.isMove() || r.isMessage())
                        {
                            System.out.println(r.getParam());
                            game.makeMove(r.getParam());
                        }
                    }
                    while (!r.isMove());
                }
                System.out.print("Game status: ");
                r = p.getResponse();
                if (r.isGameStatus())
                {
                    System.out.println(r.getParam());
                    if (r.getParam().equals(Game.STATUS_CHECKMATE))
                    {
                        game.setStatus(Game.STATUS_CHECKMATE);
                        r = p.getResponse();
                        if (r.getType().equals(Player.WHITE_PLAYER) && r.getParam().equals(Game.STATUS_CHECKMATE))
                        {
                            if (p.getColor().name().equals(Color.WHITE.name()))
                            {
                                System.out.println("You lose.");
                            }
                            else
                            {
                                System.out.println("You win.");
                            }
                        }
                        if (r.getType().equals(Player.BLACK_PLAYER) && r.getParam().equals(Game.STATUS_CHECKMATE))
                        {
                            if (p.getColor().name().equals(Color.BLACK.name()))
                            {
                                System.out.println("You lose.");
                            }
                            else
                            {
                                System.out.println("You win.");
                            }
                        }
                    }
                    if (r.getParam().equals(Game.STATUS_STALEMATE))
                    {
                        game.setStatus(Game.STATUS_STALEMATE);
                    }
                }
                // check if player is listening
                r = p.getResponse();
                //checking if other player is connected
                r = p.getResponse();
            }
            System.out.println("Game ended.");
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if (p != null)
            {
                p.closeConnection();
            }
        }
    }
}

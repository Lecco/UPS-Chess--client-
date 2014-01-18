package ups.client;


import game.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChessClient
{

    public static void main(String[] args) throws IOException
    {
        String ip = "10.0.0.142";
        int port = 10001;
        
        Player p = null;

        try
        {
            Game game = new Game(new ChessBoard());
            p = new Player(new Socket(ip, port));
            
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
            }
            
            String move;
            Scanner sc = new Scanner(System.in);
            int moveNumber = 0;
            
            while (game.getStatus() != Game.STATUS_CHECKMATE && game.getStatus() != Game.STATUS_STALEMATE)
            {
                System.out.println("MOVE " + (++moveNumber));
                move = sc.nextLine();
                move += "\n";
                
                if (!p.sendMove(move.toCharArray()))
                {
                    System.out.println(p.getResponseParam());
                    continue;
                }
                System.out.println(p.getResponseParam());
                
            }
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

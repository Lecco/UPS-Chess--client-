package ups.client;


import game.Color;
import game.Player;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TestClient
{
    
    public static void main(String[] args) throws IOException
    {
        /*
        if (args.length < 2) {
            System.err.println("Usage: java Client1 <IP address> <Port number>");
            System.exit(0);
        }
        */
        String ip = "10.0.0.139";
        int port = 10001;
        
        Player p1 = null, p2 = null;

        try
        {
            p1 = new Player(new Socket(ip, port));
            p2 = new Player(new Socket(ip, port));
            
            if (p1.isConnected())
            {
                System.out.println("1.You are now connected.\n");
                if (p1.getResponseParam().equals(Color.WHITE.toString()))
                {
                    System.out.println("Player 1 is white");
                    p1.setColor(Color.WHITE);
                }
                else
                {
                    System.out.println("Player 1 is black");
                    p1.setColor(Color.BLACK);
                }
            }
            
            if (p2.isConnected())
            {
                System.out.println("2.You are now connected.\n");
                if (p2.getResponseParam().equals(Color.WHITE.toString()))
                {
                    System.out.println("Player 2 is white");
                    p2.setColor(Color.WHITE);
                }
                else
                {
                    System.out.println("Player 2 is black");
                    p2.setColor(Color.BLACK);
                }
            }
            
            String game[] = new String[]{"e2e4\n", "e7e5\n", "d2d3\n", "b8a6\n"};
            char[] move;
            
            Player p = p1;
            
            for (int i = 0; i < game.length; i++)
            {
                System.out.println("MOVE " + (i + 1));
                move = game[i].toCharArray();
                
                if (!p.sendMove(move))
                {
                    System.out.println(p.getResponseParam());
                    i--;
                    continue;
                }
                System.out.println(p.getResponseParam());
                
                p = (p == p1) ? p2 : p1;
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if (p1 != null)
            {
                p1.closeConnection();
            }
            if (p2 != null)
            {
                p2.closeConnection();
            }
        }
    }
}

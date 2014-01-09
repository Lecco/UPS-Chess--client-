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
        String ip = "10.0.0.142";
        int port = 10001;
        
        Player p1 = null, p2 = null;

        try
        {
            p1 = new Player(new Socket(ip, port), Color.WHITE);
            p2 = new Player(new Socket(ip, port), Color.BLACK);
            
            if (p1.isConnected())
            {
                System.out.println("1.You are now connected.\n");
            }
            
            if (p2.isConnected())
            {
                System.out.println("2.You are now connected.\n");
            }
            
            String game[] = new String[]{"e2e4\n", "e7e4\n", "e5e6\n", "b8a6\n"};
            char[] move;
            for (int i = 0; i < game.length; i++)
            {
                move = game[i].toCharArray();
                if (i % 2 == 0)
                {
                    if (!p1.sendMove(move))
                    {
                        System.out.println(p1.getResponseParam());
                        i--;
                        continue;
                    }
                    System.out.println(p1.getResponseParam());
                }
                else
                {
                    if (!p2.sendMove(move))
                    {
                        System.out.println(p2.getResponseParam());
                        i--;
                        continue;
                    }
                    System.out.println(p2.getResponseParam());
                }
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

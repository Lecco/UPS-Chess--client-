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

        try
        {
            Player p1 = new Player(new Socket(ip, port), Color.WHITE);
            Player p2 = new Player(new Socket(ip, port), Color.BLACK);
            
            if (p1.isConnected())
            {
                System.out.println("1.You are now connected.\n");
            }
            
            if (p2.isConnected())
            {
                System.out.println("2.You are now connected.\n");
            }
            
            String game[] = new String[]{"e2e4\n", "e7e4\n", "e5e6\n", "b8a6\n"};
            
            //Scanner sc = new Scanner(System.in);

            for (int i = 0; i < game.length; i++)
            {
                if (i % 2 == 0)
                {
                    //System.out.println("Your move: ");
                    char[] move = game[i].toCharArray();
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
                    //System.out.println("Your move: ");
                    char[] move = game[i].toCharArray();
                    for (int j = 0; j < move.length; j++)
                    {
                        out2.write(move[j]);
                    }
                    out2.flush();
                    response = in2.readLine();
                    System.out.println("2.response = " + response);
                    if (response.equals("FAIL"))
                    {
                        if (i == 1)
                        {
                            game[i] = "e7e5\n";
                        }
                        response = in2.readLine();
                        System.out.println("2.Message = " + response);
                        i--;
                        continue;
                    }
                    response = in2.readLine();
                    System.out.println("2.Message = " + response);
                }
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if (in1 != null)
            {
                in1.close();
            }

            if (out1 != null)
            {
                out1.close();
            }
            if (sock1 != null)
            {
                sock1.close();
            }
            
            if (in2 != null)
            {
                in2.close();
            }

            if (out2 != null)
            {
                out2.close();
            }
            if (sock2 != null)
            {
                sock2.close();
            }
            
        }
    }
}

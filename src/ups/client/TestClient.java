package ups.client;


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
        BufferedReader in1 = null;
        OutputStream out1 = null;
        Socket sock1 = null;
        
        BufferedReader in2 = null;
        OutputStream out2 = null;
        Socket sock2 = null;
        

        try
        {
            String response = null;
            
            sock1 = new Socket(ip, port);
            out1 = sock1.getOutputStream();
            in1 = new BufferedReader(new InputStreamReader(sock1.getInputStream()));
            
            response = in1.readLine();
            System.out.println("response = " + response);
            if (response.equals("SUCCESS"))
            {
                System.out.println("1.You are now connected.\n");
            }
            
            sock2 = new Socket(ip, port);
            out2 = sock2.getOutputStream();
            in2 = new BufferedReader(new InputStreamReader(sock2.getInputStream()));
            
            response = in2.readLine();
            if (response.equals("SUCCESS"))
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
                    for (int j = 0; j < move.length; j++)
                    {
                        out1.write(move[j]);
                    }
                    out1.flush();
                    response = in1.readLine();
                    System.out.println("1.response = " + response);
                    if (response.equals("FAIL"))
                    {
                        //response = in1.readLine();
                        //System.out.println("1.Message = " + response);
                        i--;
                        continue;
                    }
                    response = in1.readLine();
                    System.out.println("1.Message = " + response);
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

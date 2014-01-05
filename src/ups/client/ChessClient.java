package ups.client;


import java.net.*;
import java.io.*;

public class ChessClient
{

    public static void main(String[] args) throws IOException
    {
        /*
        if (args.length < 2) {
            System.err.println("Usage: java Client1 <IP address> <Port number>");
            System.exit(0);
        }
        */
        String ip = "127.0.0.1";
        int port = 10001;
        BufferedReader in = null;
        OutputStream out = null;
        Socket sock = null;

        try
        {
            sock = new Socket(ip, port);
            out = sock.getOutputStream();
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String line = "hey\n";
            String responseline = null;
            char[] strArray;
            strArray = line.toCharArray();

            while (true)
            {
                for (int index = 0; index < strArray.length; index++)
                {
                    out.write(strArray[index]);
                }
                out.flush();
                System.out.println("data sent ");
                System.out.println("val returned" + in.readLine());
            }
        }
        catch (IOException ioe)
        {
            
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }

            if (out != null)
            {
                out.close();
            }
            if (sock != null)
            {
                sock.close();
            }
        }
    }
}

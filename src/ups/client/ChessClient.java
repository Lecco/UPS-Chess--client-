package ups.client;


import communication.Response;
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
            Response r;
            
            for (int moveNumber = 1; game.getStatus() != Game.STATUS_CHECKMATE && game.getStatus() != Game.STATUS_STALEMATE; moveNumber++)
            {
                System.out.println("MOVE " + moveNumber);
                if ((p.getColor() == Color.WHITE && moveNumber % 2 == 1) || (p.getColor() == Color.BLACK && moveNumber % 2 == 0))
                {
                    move = sc.nextLine();
                    move += "\n";

                    if (!p.sendMove(move.toCharArray()))
                    {
                        r = p.getResponse();
                        if (r.isMessage())
                        {
                            System.out.println(r.getParam());
                        }
                        moveNumber--;
                        continue;
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
                    r = p.getResponse();
                    
                    if (r.isMove())
                    {
                        System.out.println(r.getParam());
                    }
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

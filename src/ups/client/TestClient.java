package ups.client;


import communication.Response;
import game.ChessBoard;
import game.Color;
import game.Game;
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
            Game game = new Game(new ChessBoard());
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
            
            // String game[] = new String[]{"e2e4\n", "e7e5\n", "d2d3\n", "b8a6\n"};
            // Fool's mate
            String moves[] = new String[]{"f2f3\n", "e7e5\n", "g2g4\n", "d8h4\n"};
            // Shortest stalemate
            //String game[] = new String[]{"e2e3\n", "a7a5\n", "d1h5\n", "a8a6\n", "h5a5\n", "h7h5\n", "h2h4\n", "a6h6\n", "a5c7\n", "f7f6\n", "c7d7\n", "e8f7\n", "d7b7\n", "d8d3\n", "b7b8\n", "d3h7\n", "b8c8\n", "f7g6\n", "c8e6\n"};
            String move;
            Response r;
            
            Player p = p1;
            
            for (int i = 0; i < moves.length; i++)
            {
                System.out.println("MOVE " + (i + 1));
                move = moves[i];
                move += "\n";

                if (!p.sendMove(move.toCharArray()))
                {
                    r = p.getResponse();
                    System.out.println("aaa = " + r);
                    if (r.isMessage())
                    {
                        System.out.println(r.getParam());
                    }
                    i--;
                    continue;
                }
                r = p.getResponse();
                if (r.isMessage())
                {
                    System.out.println(r.getParam());
                }
                p = (p == p1) ? p2 : p1;
                System.out.print("Incoming move: ");
                r = p.getResponse();

                if (r.isMove())
                {
                    System.out.println(r.getParam());
                }
                p = (p == p1) ? p2 : p1;
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
                r = p.getResponse();
                if (r.isPlayerStatus())
                {
                    // check other players status
                    if (r.getParam().equals(Player.STATUS_DISCONNECTED))
                    {
                        System.out.println("Opponent disconnected.");
                        System.exit(2);
                    }
                }
                p = (p == p1) ? p2 : p1;
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
                r = p.getResponse();
                if (r.isPlayerStatus())
                {
                    // check other players status
                    if (r.getParam().equals(Player.STATUS_DISCONNECTED))
                    {
                        System.out.println("Opponent disconnected.");
                        System.exit(2);
                    }
                }
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


package ups.client;

import communication.Response;
import communication.ResponseParam;
import communication.ResponseType;
import game.ChessBoard;
import game.Game;
import game.Player;
import game.Color;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.util.IPAddressUtil;

/**
 * GUI client for chess
 * 
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class ChessClientGUI
{
    JFrame frame;
    JPanel squares[][] = new JPanel[9][9];
    public final static String LABELS = " ABCDEFGH";
    String moveFrom;
    String moveTo;
    Game game;
    Player p;

    private void initFrame()
    {
        frame = new JFrame("Chess GUI Client");
        frame.setSize(630, 630);
        frame.setLayout(new GridLayout(9, 9));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                squares[i][j] = new JPanel();
                if (j != 0 && i != 8)
                {
                    if ((i + j - 1) % 2 == 0) {
                        squares[i][j].setBackground(java.awt.Color.DARK_GRAY);
                    } else {
                        squares[i][j].setBackground(java.awt.Color.white);
                    }
                }
                frame.add(squares[i][j]);
            }
        }
        
        squares[7][1].add(new JLabel(new ImageIcon("rook-white.png")));
        squares[7][2].add(new JLabel(new ImageIcon("knight-white.png")));
        squares[7][3].add(new JLabel(new ImageIcon("bishop-white.png")));
        squares[7][4].add(new JLabel(new ImageIcon("queen-white.png")));
        squares[7][5].add(new JLabel(new ImageIcon("king-white.png")));
        squares[7][6].add(new JLabel(new ImageIcon("bishop-white.png")));
        squares[7][7].add(new JLabel(new ImageIcon("knight-white.png")));
        squares[7][8].add(new JLabel(new ImageIcon("rook-white.png")));

        squares[0][1].add(new JLabel(new ImageIcon("rook-black.png")));
        squares[0][2].add(new JLabel(new ImageIcon("knight-black.png")));
        squares[0][3].add(new JLabel(new ImageIcon("bishop-black.png")));
        squares[0][4].add(new JLabel(new ImageIcon("queen-black.png")));
        squares[0][5].add(new JLabel(new ImageIcon("king-black.png")));
        squares[0][6].add(new JLabel(new ImageIcon("bishop-black.png")));
        squares[0][7].add(new JLabel(new ImageIcon("knight-black.png")));
        squares[0][8].add(new JLabel(new ImageIcon("rook-black.png")));
        
        for (int i = 0; i < 9; i++)
        {
            if (i < 9)
            {
                squares[i][0].add(new JLabel("" + (8 - i)));
            }
            squares[8][i].add(new JLabel(ChessClientGUI.LABELS.substring(i, i + 1)));
        }

        for (int i = 1; i < 9; i++) {
            squares[6][i].add(new JLabel(new ImageIcon("pawn-white.png")));
            squares[1][i].add(new JLabel(new ImageIcon("pawn-black.png")));
        }

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initConnection();
    }
    
    private void initConnection()
    {
        String ip = null;
        do
        {
            ip = JOptionPane.showInputDialog(frame, "IP address of server: ");
            if (!IPAddressUtil.isIPv4LiteralAddress(ip) && !IPAddressUtil.isIPv6LiteralAddress(ip))
            {
                JOptionPane.showMessageDialog(frame, "Not valid IP address.", "Invalid IP", JOptionPane.ERROR_MESSAGE);
            }
        }
        while (!IPAddressUtil.isIPv4LiteralAddress(ip) && !IPAddressUtil.isIPv6LiteralAddress(ip));

        boolean portSet = false;
        int port = -1;
        while (!portSet)
        {
            try
            {
                port = Integer.parseInt(JOptionPane.showInputDialog(frame, "Port: "));
                if (port < 1024 || port >= 65536)
                {
                    throw new NumberFormatException();
                }
                portSet = true;
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(frame, "Invalid port number, choose port number in range 1024-65535", "Invalid port", JOptionPane.ERROR_MESSAGE);
                portSet = false;
                port = -1;
            }
        }
        
        startGame(ip, port);
    }
    
    private void startGame(String ip, int port)
    {
        try
        {
            Response r;
            game = new Game(new ChessBoard());
            p = new Player(new Socket(ip, port));
            p.send(ResponseType.STATUS + Response.SEPARATOR + ResponseParam.SUCCESS);
            
            if (p.isConnected())
            {
                JOptionPane.showMessageDialog(frame, "You are now connected.\n", "Connection success", JOptionPane.INFORMATION_MESSAGE);
                if (p.getResponseParam().equals(Color.WHITE.toString()))
                {
                    JOptionPane.showMessageDialog(frame, "Your color is white.");
                    p.setColor(Color.WHITE);
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Your color is black");
                    p.setColor(Color.BLACK);
                }
                
                JDialog dialog = new JDialog(frame, false);
                JOptionPane optionPane = new JOptionPane("Waiting for other player...");
                dialog.getContentPane().add(optionPane);
                dialog.pack();
                dialog.setVisible(true);
                
                r = p.getResponse();
                if (r.isPlayerStatus())
                {
                    if (r.getParam().equals(ResponseParam.CONNECTED.name()))
                    {
                        Window[] windows = Window.getWindows();
                        for (Window window : windows)
                        {
                            if (window instanceof JDialog)
                            {
                                JDialog d = (JDialog) window;
                                if (d.getContentPane().getComponentCount() == 1
                                    && d.getContentPane().getComponent(0) instanceof JOptionPane)
                                {
                                    d.dispose();
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    private void getIncomingMove()
    {
        Response r;
        JDialog dialog = new JDialog(frame, false);
        JOptionPane optionPane = new JOptionPane("Waiting for other player's move...");
        dialog.getContentPane().add(optionPane);
        dialog.pack();
        dialog.setVisible(true);
        
        do
        {
            r = p.getResponse();

            if (r.isMove() || r.isMessage())
            {
                JOptionPane.showMessageDialog(frame, r.getParam());
                game.makeMove(r.getParam());

                Window[] windows = Window.getWindows();
                for (Window window : windows)
                {
                    if (window instanceof JDialog)
                    {
                        JDialog d = (JDialog) window;
                        if (d.getContentPane().getComponentCount() == 1
                            && d.getContentPane().getComponent(0) instanceof JOptionPane)
                        {
                            d.dispose();
                        }
                    }
                }
            }
        }
        while (!r.isMove());
    }
    
    private void initMouseListeners()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 1; j < 9; j++)
            {
                final String from = ChessClientGUI.LABELS.substring(j, j + 1).toLowerCase() + "" + (8 - i);
                final String to = ChessClientGUI.LABELS.substring(j, j + 1).toLowerCase() + "" + (8 - i);
                squares[i][j].addMouseListener(new MouseListener(){
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (moveFrom == null || moveFrom.length() == 0)
                        {
                            moveFrom = from;
                        }
                        else
                        {
                            moveTo = to;
                            String move = moveFrom + moveTo + "\n";
                            Response r;
                            try
                            {
                                if (!p.sendMove(move.toCharArray()))
                                {
                                    r = p.getResponse();
                                    if (r.isPlayerStatus())
                                    {
                                        if (r.getParam().equals(ResponseParam.DISCONNECTED.name()))
                                        {
                                            System.out.println("Other player disconnected.");
                                        }
                                    }
                                    if (r.isMessage())
                                    {
                                        System.out.println(r.getParam());
                                    }
                                }
                                else
                                {
                                    game.makeMove(move);
                                }

                                moveFrom = "";
                            }
                            catch (IOException ex)
                            {
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
            }
        }
    }
    
    private void chessGame()
    {
        Response r;
        if (p.getColor() == Color.BLACK)
        {
            getIncomingMove();
            //r = p.getResponse();
            //System.out.println(r);
        }
        else
        {
            try
            {
                String pokus = "aaaa\n";
                p.sendMove(pokus.toCharArray());
            }
            catch(IOException e)
            {
                
            }
        }
    }
    
    public ChessClientGUI()
    {
        initFrame();
        initMouseListeners();
        chessGame();
        
        
        System.out.println("tady to konci");
        
        if (p != null)
        {
            p.closeConnection();
        }
    }

    public static void main(String[] args)
    {
        new ChessClientGUI();
    }
}

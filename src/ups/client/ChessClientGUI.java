
package ups.client;

import javax.swing.*;
import java.awt.*;

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
                        squares[i][j].setBackground(Color.black);
                    } else {
                        squares[i][j].setBackground(Color.white);
                    }
                }
                frame.add(squares[i][j]);
            }
        }
        frame.setVisible(true);

        squares[7][1].add(new JLabel(new ImageIcon("rook-white.png")));
        squares[7][2].add(new JLabel(new ImageIcon("knight-white.png")));
        squares[7][3].add(new JLabel(new ImageIcon("bishop-white.png")));
        squares[7][4].add(new JLabel(new ImageIcon("queen-white.png")));
        squares[7][5].add(new JLabel(new ImageIcon("king-white.png")));
        squares[7][6].add(new JLabel(new ImageIcon("bishop-white.png")));
        squares[7][7].add(new JLabel(new ImageIcon("knigh-white.png")));
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public ChessClientGUI()
    {
        initFrame();
    }

    public static void main(String[] args)
    {
        new ChessClientGUI();
    }
}

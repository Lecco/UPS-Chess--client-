
package game;

/**
 * Instances of {@code ChessBoard} represent chessboard with info about all chess pieces
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class ChessBoard
{
    /**
     * Length of chessboard
     */
    public final static int LENGTH = 8;
    
    /**
     * Board consists of pieces or NULL where aren't any pieces
     */
    private Piece[][] board;
    
    /**
     * Init new chessboard
     */
    public ChessBoard()
    {
        this.board = new Piece[ChessBoard.LENGTH][ChessBoard.LENGTH];
        
        for (int i = 0; i < ChessBoard.LENGTH; i++)
        {
            for (int j = 0; j < ChessBoard.LENGTH; j++)
            {
                this.board[i][j] = null;
            }
        }
        // white pieces
        this.board[0][0] = new Piece(Color.WHITE, ChessType.ROOK);
        this.board[0][1] = new Piece(Color.WHITE, ChessType.KNIGHT);
        this.board[0][2] = new Piece(Color.WHITE, ChessType.BISHOP);
        this.board[0][3] = new Piece(Color.WHITE, ChessType.QUEEN);
        this.board[0][4] = new Piece(Color.WHITE, ChessType.KING);
        this.board[0][5] = new Piece(Color.WHITE, ChessType.BISHOP);
        this.board[0][6] = new Piece(Color.WHITE, ChessType.KNIGHT);
        this.board[0][7] = new Piece(Color.WHITE, ChessType.ROOK);
        for (int i = 0; i < ChessBoard.LENGTH; i++)
        {
            this.board[1][i] = new Piece(Color.WHITE, ChessType.PAWN);
        }
        
        // black pieces
        this.board[7][0] = new Piece(Color.BLACK, ChessType.ROOK);
        this.board[7][1] = new Piece(Color.BLACK, ChessType.KNIGHT);
        this.board[7][2] = new Piece(Color.BLACK, ChessType.BISHOP);
        this.board[7][3] = new Piece(Color.BLACK, ChessType.QUEEN);
        this.board[7][4] = new Piece(Color.BLACK, ChessType.KING);
        this.board[7][5] = new Piece(Color.BLACK, ChessType.BISHOP);
        this.board[7][6] = new Piece(Color.BLACK, ChessType.KNIGHT);
        this.board[7][7] = new Piece(Color.BLACK, ChessType.ROOK);
        for (int i = 0; i < ChessBoard.LENGTH; i++)
        {
            this.board[6][i] = new Piece(Color.BLACK, ChessType.PAWN);
        }
    }
    
    /**
     * Perform move
     * 
     * @param move
     */
    public void move(int[] move)
    {
        this.board[move[3]][move[2]] = this.board[move[1]][move[0]];
        this.board[move[1]][move[0]] = null;
    }
    
    /**
     * Print chess board
     */
    private void printChessBoard()
    {
        for (int i = ChessBoard.LENGTH - 1; i >= 0; i--)
        {
            for (int j = 0; j < ChessBoard.LENGTH; j++)
            {
                System.out.print("move[" + i + "][" + j + "] = ");
                System.out.println(this.board[i][j]);
            }
        }
    }
    
    /**
     * Returns string representation of chess board
     * 
     * @return Printable chessboard 
     */
    @Override
    public String toString()
    {
        String s = "\n";
        for (int i = ChessBoard.LENGTH - 1; i >= 0; i--)
        {
            for (int j = 0; j < ChessBoard.LENGTH; j++)
            {
                if (this.board[i][j] == null)
                {
                    s += " X  ";
                }
                else
                {
                    s += this.board[i][j].toString() + " ";
                }
            }
            s += "\n";
        }
        return s + "\n";
    }
    
    /**
     * 
     * @return 
     */
    public Piece[][] getChessBoard()
    {
        return this.board;
    }
}


package game;

/**
 * Instances of {@code Game} are chess games, they have chess boards and info
 * about game status.
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Game
{
    public final static int STATUS_DEFAULT = 0;
    public final static int STATUS_CHECK = 1;
    public final static int STATUS_CHECKMATE = 2;
    public final static int STATUS_STALEMATE = 3;
    
    private ChessBoard chessboard;
    private int status;
    
    
    public Game(ChessBoard chessboard)
    {
        this.chessboard = chessboard;
        this.status = Game.STATUS_DEFAULT;
    }

    public ChessBoard getChessboard()
    {
        return chessboard;
    }

    public void setChessboard(ChessBoard chessboard)
    {
        this.chessboard = chessboard;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
    
    
}

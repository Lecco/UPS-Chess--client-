
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
    public final static String STATUS_DEFAULT = "DEFAULT";
    public final static String STATUS_CHECK = "CHECK";
    public final static String STATUS_CHECKMATE = "CHECKMATE";
    public final static String STATUS_STALEMATE = "STALEMATE";
    
    private ChessBoard chessboard;
    private String status;
    
    
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    
}

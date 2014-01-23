
package game;

import communication.Response;

/**
 * Instances of {@code Game} are chess games, they have chess boards and info
 * about game status.
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Game
{
    /**
     * Default game status
     */
    public final static String STATUS_DEFAULT = "DEFAULT";
    
    /**
     * Status when one of players is checked
     */
    public final static String STATUS_CHECK = "CHECK";
    
    /**
     * Status of game when game ended with checkmate
     */
    public final static String STATUS_CHECKMATE = "CHECKMATE";
    
    /**
     * Status when game ended with stalemate
     */
    public final static String STATUS_STALEMATE = "STALEMATE";
    
    /**
     * Chessboard on which is game played
     */
    private ChessBoard chessboard;
    
    /**
     * Actual status of this game
     */
    private String status;
    
    /**
     * Create new game, assign chessboard for it, set initial status
     * 
     * @param chessboard 
     */
    public Game(ChessBoard chessboard)
    {
        this.chessboard = chessboard;
        this.status = Game.STATUS_DEFAULT;
    }
    
    /**
     * Make move on chessboard
     * 
     * @param move Move to be performed on chessboard 
     */
    public void makeMove(String chessMove)
    {
        int[] move = this.getSanitizedMove(chessMove);
        this.chessboard.move(move);
    }
    
    /**
     * Get move as array of indexes
     * 
     * @param chessMove String move we want to perform (like e2e4)
     * @return Array of move indexes
     */
    private int[] getSanitizedMove(String chessMove)
    {
        int[] move = new int[4];
        move[0] = Character.toLowerCase(chessMove.charAt(0)) - 'a';
        move[1] = Character.toLowerCase(chessMove.charAt(1)) - '1';
        move[2] = Character.toLowerCase(chessMove.charAt(2)) - 'a';
        move[3] = Character.toLowerCase(chessMove.charAt(3)) - '1';
        return move;
    }

    /**
     * Get current state of chessboard in game
     * 
     * @return Chessboard
     */
    public ChessBoard getChessboard()
    {
        return chessboard;
    }

    /**
     * Assign new chessboard to game
     * 
     * @param chessboard Chessboard
     */
    public void setChessboard(ChessBoard chessboard)
    {
        this.chessboard = chessboard;
    }

    /**
     * Get actual status of game
     * 
     * @return Game status
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Set new game status
     * 
     * @param status New game status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    
}

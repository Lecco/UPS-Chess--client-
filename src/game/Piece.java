
package game;

/**
 * Instances of {@code Piece} are chess pieces on chessboard, they have info
 * about their type (knight, king, pawn..), color.
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Piece
{
    /**
     * Color of this chess piece
     */
    Color color;
    
    /**
     * Type of this chesspiece
     */
    ChessType type;

    /**
     * Create new chess piece on given coordinates, in given color and type
     * 
     * @param positionX X coordinate of chess piece
     * @param positionY Y coordinate of chess piece
     * @param color Color of chess piece
     * @param type Type of chess piece
     */
    public Piece(Color color, ChessType type)
    {
        this.color = color;
        this.type = type;
    }

    /**
     * @return Color of this chess piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color New color of this chess piece
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * @return Type of chess piece
     */
    public ChessType getType()
    {
        return type;
    }

    /**
     * @param type New type of chess piece
     */
    public void setType(ChessType type)
    {
        this.type = type;
    }
}


package communication;

/**
 * Instances of {@code Response} are responses from chess server
 *
 * @author Oldřich Pulkrt <O.Pulkrt@gmail.com>
 * @version 1.0
 */
public class Response
{
    /**
     * Separator of response type and other parameters
     */
    public final static String SEPARATOR = "---";
    
    /**
     * Type of response
     */
    private String type;
    
    /**
     * Other parameters of response
     */
    private String param;

    /**
     * Create new {@code Response} from chess server response
     * 
     * @param response Exact response from chess server
     */
    public Response(String response)
    {
        String []parsedResponse = response.split(Response.SEPARATOR);
        this.type = parsedResponse[0];
        this.param = parsedResponse[1];
    }
    
    /**
     * Create new Response from chess server
     * 
     * @param type
     * @param param 
     */
    public Response(String type, String param)
    {
        this.type = type;
        this.param = param;
    }
    
    /**
     * Return true if response is status succes
     * 
     * @return true = success
     */
    public boolean isSuccess()
    {
        if (type.equals(ResponseType.STATUS.toString()))
        {
            if (param.equals(ResponseParam.SUCCESS.toString()))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return True if response is message from server
     */
    public boolean isMessage()
    {
        return this.type.equals(ResponseType.MESSAGE.name());
    }
    
    /**
     * @return True if response is chess move
     */
    public boolean isMove()
    {
        boolean isMove = this.type.equals(ResponseType.MOVE.name());
        if (isMove)
        {
            this.param = this.param.substring(0, 4);
        }
        return isMove;
    }
    
    /**
     * @return True if response is info about game status
     */
    public boolean isGameStatus()
    {
        return this.type.equals(ResponseType.GAME_STATUS.name());
    }
    
    /**
     * @return True if reponse has info about other players status
     */
    public boolean isPlayerStatus()
    {
        return (this.type.equals(ResponseType.WHITE_STATUS.name()) || this.type.equals(ResponseType.BLACK_STATUS.name()));
    }
    
    /**
     * Return type of response
     * 
     * @return Type (STATUS, MESSAGE..)
     */
    public String getType()
    {
        return type;
    }

    /**
     * Set type to response
     * 
     * @param type New type of response
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Get param of response (SUCCESS, message text..)
     * 
     * @return Parameter of response
     */
    public String getParam()
    {
        return param;
    }

    /**
     * Set new param to response
     * 
     * @param param New parameter of response
     */
    public void setParam(String param)
    {
        this.param = param;
    }
    
    /**
     * @return String representation of response
     */
    public String toString()
    {
        return this.type + "---" + this.param;
    }
    
}


package receiver;

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
    final static String SEPARATOR = "---";
    
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
    
}

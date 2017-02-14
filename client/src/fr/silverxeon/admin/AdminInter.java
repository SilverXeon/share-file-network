package fr.silverxeon.admin;

/**
 * Created by Pierre on 04/02/2017.
 */

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AdminInter {
    @WebMethod String upload(DataHandler file);
    @WebMethod DataHandler download(String session) throws Exception;
}

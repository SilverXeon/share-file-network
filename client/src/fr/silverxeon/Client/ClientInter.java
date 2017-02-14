package fr.silverxeon.client;

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
public interface ClientInter {
    @WebMethod DataHandler download(String session) throws Exception;
    @WebMethod void upload(String session, DataHandler file, String name, String surname, String ext) throws Exception;
    @WebMethod String getExtension(String session);
}

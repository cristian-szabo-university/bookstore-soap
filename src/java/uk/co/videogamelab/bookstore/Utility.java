package uk.co.videogamelab.bookstore;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

public class Utility {
    
    public static final String ERROR_SERVER = "Server";
    public static final String ERROR_CLIENT = "Client";
    
    private static SOAPFactory soapFactory;
    
    static {
        try {
            soapFactory = SOAPFactory.newInstance();
        } catch (SOAPException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static SOAPFaultException createException(String code, String message) throws SOAPException {
        SOAPFault fault = soapFactory.createFault();
        fault.setFaultCode(new QName(code));
        fault.setFaultString(message);

        return new SOAPFaultException(fault);
    }
    
}

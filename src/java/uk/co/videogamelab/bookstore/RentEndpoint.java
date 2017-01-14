package uk.co.videogamelab.bookstore;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;

@WebService(
    name = "RentInterface", 
    targetNamespace = "http://videogamelab.co.uk/")
public interface RentEndpoint {
  
    /**
     * Insert a rent record in the database. If invalid data is provided then 
     * an exception is thrown.
     * 
     * @param bookId of type Integer
     * @param personId  of type Integer
     * @return object of type Rent
     * @throws SOAPException
     */
    @WebMethod(operationName = "CreateRent")
    @WebResult(name = "Rent")
    public Rent createRent(
        @WebParam(name = "BookId")    
        Integer bookId,
        @WebParam(name = "PersonId")    
        Integer personId) throws SOAPException;
    
    /**
     * Select a rent record by book id from the database. If the book is not found 
     * by the specified id then an exception is thrown.
     * 
     * @param bookId
     * @return
     * @throws SOAPException
     */
    @WebMethod(operationName = "FindRentByBook")
    @WebResult(name = "RentList")
    public List<Rent> findRentByBook(
        @WebParam(name = "BookId")    
        Integer bookId) throws SOAPException;
    
    /**
     * Select a rent record by person id from the database. If the person is not found 
     * by the specified id then an exception is thrown.
     * 
     * @param personId
     * @return
     * @throws SOAPException
     */
    @WebMethod(operationName = "FindRentByPerson")
    @WebResult(name = "Rent")
    public List<Rent> findRentByPerson(
        @WebParam(name = "PersonId")    
        Integer personId) throws SOAPException;
    
    /**
     * Update a rent record from the database. If the rent does not exist
     * then an exception is thrown.
     * 
     * @param object
     * @throws SOAPException
     */
    @WebMethod(operationName = "UpdateRent")
    public void updateRent(
        @WebParam(name = "Rent")    
        Rent object) throws SOAPException;  
    
    /**
     * Delete a rent record from the database. If the rent is not found by the
     * specified id then an exception is thrown.
     * 
     * @param rentId
     * @throws SOAPException
     */
    @WebMethod(operationName = "DeleteRent")
    public void deleteRent(
        @WebParam(name = "RentId")    
        Integer rentId) throws SOAPException;
    
}

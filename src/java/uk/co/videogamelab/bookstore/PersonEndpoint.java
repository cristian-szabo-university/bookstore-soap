package uk.co.videogamelab.bookstore;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;

@WebService(
    name = "PersonInterface", 
    targetNamespace = "http://videogamelab.co.uk/")
public interface PersonEndpoint {
    
    /**
     * Insert a person record in the database. If invalid data is provided then 
     * an exception is thrown.
     * 
     * @param username of type String
     * @param password of type String
     * @param firstName of type String
     * @param lastName of type String
     * @return object of type Person
     * @throws SOAPException
     */
    @WebMethod(operationName = "CreatePerson")
    @WebResult(name = "Person")
    public Person createPerson(
        @WebParam(name = "Username")
        String username,
        @WebParam(name = "Password")
        String password,
        @WebParam(name = "FirstName")
        String firstName,
        @WebParam(name = "LastName")
        String lastName) throws SOAPException;
    
    /**
     * Select a person record by id from the database. If the person is not found 
     * by the specified id then an exception is thrown.
     * 
     * @param personId of type Integer
     * @return object of type Person
     * @throws SOAPException
     */
    @WebMethod(operationName = "FindPersonById")
    @WebResult(name = "Person")
    public Person findPersonById(
        @WebParam(name = "PersonId")
        Integer personId) throws SOAPException;
    
    /**
     * Select a person record by username and password from the database. If the 
     * person is not found by the specified username and password then an 
     * exception is thrown.
     * 
     * @param username of type String
     * @param password of type String
     * @return object of type Person
     * @throws SOAPException
     */
    @WebMethod(operationName = "FindPersonByUsernameAndPassword")
    @WebResult(name = "Person")
    public Person findPersonByUsernameAndPassword(
        @WebParam(name = "Username")    
        String username, 
        @WebParam(name = "Password")    
        String password) throws SOAPException;

    /**
     * Update a person record from the database. If the person does not exist
     * then an exception is thrown.
     * 
     * @param object of type Person
     * @throws SOAPException
     */
    @WebMethod(operationName = "UpdatePerson")
    public void updatePerson(
        @WebParam(name = "Person")    
        Person object) throws SOAPException;
    
    /**
     * Delete a person record from the database. If the person is not found by the
     * specified id then an exception is thrown.
     * 
     * @param personId of type Integer
     * @throws SOAPException
     */
    @WebMethod(operationName = "DeletePerson")
    public void deletePerson(
        @WebParam(name = "PersonId")    
        Integer personId) throws SOAPException;
    
}

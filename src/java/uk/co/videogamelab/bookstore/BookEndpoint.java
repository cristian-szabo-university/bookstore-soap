package uk.co.videogamelab.bookstore;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;

@WebService(
    name = "BookInterface",
    targetNamespace = "http://videogamelab.co.uk/")
public interface BookEndpoint {

    /**
     * Insert a book record in the database. If invalid data is provided then an 
     * exception is thrown.
     * 
     * @param title of type String
     * @param author of type String
     * @return object of type Book
     * @throws SOAPException
     */
    @WebMethod(operationName = "CreateBook")
    @WebResult(name = "Book")
    public Book createBook(
        @WebParam(name = "Title") 
        String title,
        @WebParam(name = "Author") 
        String author) throws SOAPException;
    
    /**
     * Select a book record by id from the database. If the book is not found by 
     * the specified id then an exception is thrown.
     * 
     * @param bookId of type Integer
     * @return object of type Book
     * @throws SOAPException
     */
    @WebMethod(operationName = "FindBookById")
    @WebResult(name = "Book")
    public Book findBookById(
        @WebParam(name = "BookId")    
        Integer bookId) throws SOAPException;
    
    /**
     * Select all book records from the database. If there are no book
     * records then an empty list is returned.
     * 
     * @return list of objects of type Book
     * @throws SOAPException
     */
    @WebMethod(operationName = "FindBookAll")
    @WebResult(name = "Book")
    public List<Book> findBookAll() throws SOAPException;
    
    /**
     * Update a book record by id from the database. If the book does not 
     * exist then an exception is thrown.
     * 
     * @param object of type Book
     * @throws SOAPException
     */
    @WebMethod(operationName = "UpdateBook")
    public void updateBook(
        @WebParam(name = "Book")
        Book object) throws SOAPException;
    
    /**
     * Delete a book record from the database. If the book is not found by the
     * specified id then an exception is thrown.
     * 
     * @param bookId of type Integer
     * @throws SOAPException
     */
    @WebMethod(operationName = "DeleteBook")
    public void deleteBook(
        @WebParam(name = "BookId")    
        Integer bookId) throws SOAPException;

}

package uk.co.videogamelab.bookstore;

import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.soap.SOAPException;

@Stateless
@WebService(
    serviceName = "Bookstore",
    portName = "BookEndpoint",
    targetNamespace = "http://videogamelab.co.uk/",
    endpointInterface = "uk.co.videogamelab.bookstore.BookEndpoint")
public class BookService implements BookEndpoint {

    @PersistenceContext(unitName = "BookstorePU")
    private EntityManager em;
    
    @Override
    public Book createBook(String title, String author) throws SOAPException {
        Book object = new Book(0);
        
        if (title == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null title instance!");
        }
        
        if (title.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Title can not be empty!");
        }
        
        object.setTitle(title);
        
        if (author == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null author instance!");
        }
        
        if (author.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Author can not be empty!");
        }
        
        object.setAuthor(author);
        
        try {
            em.persist(object);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
        
        return object;
    }
    
    @Override
    public Book findBookById(Integer bookId) throws SOAPException {
        if (bookId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null bookId instance!");
        }
        
        if (bookId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid bookId instance!");
        }

        Query query = em.createNamedQuery("Book.findById");      
        query.setParameter("id", bookId);
        
        Book object = null;
        
        try {
            object = (Book) query.getSingleResult();
        } catch (NoResultException e) {
            throw Utility.createException(Utility.ERROR_SERVER, "Book does not exist!");
        } catch (Exception e) {
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }

        return object;
    }
    
    @Override
    public List<Book> findBookAll() throws SOAPException {
        return em.createNamedQuery("Book.findAll").getResultList();
    }
    
    @Override
    public void updateBook(Book object) throws SOAPException {
        if (object == null || object.getId() < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null book instance!");
        }

        Book newObj = em.find(Book.class, object.getId());
        
        if (newObj == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Book does not exist!");
        }
        
        newObj.setTitle(object.getTitle());
        newObj.setAuthor(object.getAuthor());
        newObj.setPublishDate(object.getPublishDate());
        newObj.setDescription(object.getDescription());
        newObj.setCover(object.getCover());
        
        try {
            em.merge(newObj);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
    }

    @Override
    public void deleteBook(Integer bookId) throws SOAPException {
        if (bookId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null personId instance!");
        }
        
        if (bookId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid personId instance!");
        }
        
        Book object = em.find(Book.class, bookId);
        
        if (object == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Book does not exist!");
        }
        
        try {
            em.remove(object);
        } catch (Exception e) {          
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
    }
 
}

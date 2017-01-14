package uk.co.videogamelab.bookstore;

import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.soap.SOAPException;

@Stateless
@WebService(
    serviceName = "Bookstore",   
    portName = "RentEndpoint",
    targetNamespace = "http://videogamelab.co.uk/",
    endpointInterface = "uk.co.videogamelab.bookstore.RentEndpoint")
public class RentService implements RentEndpoint {

    @PersistenceContext(unitName = "BookstorePU")
    private EntityManager em;
    
    @Override
    public Rent createRent(Integer bookId, Integer personId) throws SOAPException {
        Rent object = new Rent(0);
        
        if (bookId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null bookId instance!");
        }
        
        if (bookId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid bookId instance!");
        }
        
        Book bookObj = em.find(Book.class, bookId);
        
        if (bookObj == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Book does not exist!");
        }
        
        object.setBook(bookObj);
        
        if (personId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null personId instance!");
        }
        
        if (personId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid personId instance!");
        }
        
        Person personObj = em.find(Person.class, personId);
        
        if (personObj == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Person does not exist!");
        }
        
        object.setPerson(personObj);
        
        try {
            em.persist(object);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
        
        return object;
    }
    
    @Override
    public List<Rent> findRentByBook(Integer bookId) throws SOAPException {
        Book object = null;
        
        if (bookId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null bookId instance!");
        }
        
        if (bookId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid bookId instance!");
        }

        object = em.find(Book.class, bookId);
        
        if (object == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Book does not exist!");
        }
        
        Query query = em.createNamedQuery("Rent.findByBook", Rent.class);
        
        query.setParameter("book", object);
        
        return query.getResultList();
    }

    @Override
    public List<Rent> findRentByPerson(Integer personId) throws SOAPException {
        Person object = null;
        
        if (personId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null personId instance!");
        }
        
        if (personId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid personId instance!");
        }

        object = em.find(Person.class, personId);
        
        if (object == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Person does not exist!");
        }
        
        Query query = em.createNamedQuery("Rent.findByPerson");
        
        query.setParameter("person", object);
        
        return query.getResultList();
    }

    @Override
    public void updateRent(Rent object) throws SOAPException {
        if (object == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null rent instance!");
        }

        Rent newObj = em.find(Rent.class, object.getId());
        
        if (newObj == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Rent does not exist!");
        }
        
        newObj.setStartDate(object.getStartDate());
        newObj.setEndDate(object.getEndDate());
        
        try {
            em.merge(newObj);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
    }

    @Override
    public void deleteRent(Integer rentId) throws SOAPException {
        if (rentId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null rentId instance!");
        }
        
        if (rentId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid rentId instance!");
        }
        
        Rent object = em.find(Rent.class, rentId);
        
        if (object == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Rent does not exist!");
        }
        
        try {
            em.remove(object);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
    }
    
}

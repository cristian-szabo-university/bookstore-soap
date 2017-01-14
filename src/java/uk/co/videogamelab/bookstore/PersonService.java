package uk.co.videogamelab.bookstore;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;
import javax.xml.ws.soap.*;
import javax.xml.soap.*;

@Stateless
@WebService(
    serviceName = "Bookstore",
    portName = "PersonEndpoint",
    targetNamespace = "http://videogamelab.co.uk/", 
    endpointInterface = "uk.co.videogamelab.bookstore.PersonEndpoint")
public class PersonService implements PersonEndpoint {

    @PersistenceContext(unitName = "BookstorePU")
    private EntityManager em;
     
    @Override
    public Person createPerson(String username, String password, String firstName, String lastName) throws SOAPException {
        Person object = new Person(0);
        
        if (username == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null username instance!");
        }
        
        if (username.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Username can not be empty!");
        }
        
        object.setUsername(username);
        
        if (password == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null password instance!");
        }
        
        if (password.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Password can not be empty!");
        }
        
        object.setPassword(password);
        
        if (firstName == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null firstName instance!");
        }
        
        if (firstName.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "First name can not be empty!");
        }
        
        object.setFirstName(firstName);
        
        if (lastName == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null lastName instance!");
        }
        
        if (lastName.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Last name can not be empty!");
        }
        
        object.setLastName(lastName);
        
        object.setAdmin(Boolean.FALSE);
        
        try {
            em.persist(object);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
        
        return object;
    }
    
    @Override
    public Person findPersonById(Integer personId) throws SOAPException {
        Person object = null;
        
        if (personId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null personId instance!");
        }
        
        if (personId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid personId instance!");
        }

        Query query = em.createNamedQuery("Person.findById");
        query.setParameter("id", personId);
        
        try {
            object = (Person) query.getSingleResult();
        } catch (NoResultException e) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Account doesn not exist!");
        } catch (Exception e) {
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
        
        return object;
    }
    
    @Override
    public Person findPersonByUsernameAndPassword(String username, String password) throws SOAPException {
        Person object = null;
        
        if (username == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null username instance!");
        }
        
        if (username.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Username can not be empty!");
        }
        
        if (password == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null password instance!");
        }
        
        if (password.isEmpty()) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Password can not be empty!");
        }

        Query query = em.createNamedQuery("Person.findByUsernameAndPassword");  
        
        query.setParameter("username", username).setParameter("password", password);
        
        try {
            object = (Person) query.getSingleResult();
        } catch (NoResultException e) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Account does not exist!");
        } catch (Exception e) {
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
        
        return object;
    }

    @Override
    public void updatePerson(Person object) throws SOAPException {
        if (object == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null person instance!");
        }

        Person newObj = em.find(Person.class, object.getId());
        
        if (newObj == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Account does not exist!");
        }
        
        newObj.setUsername(object.getUsername());
        newObj.setPassword(object.getPassword());
        newObj.setFirstName(object.getFirstName());
        newObj.setLastName(object.getLastName());
        newObj.setBirthDate(object.getBirthDate());
        newObj.setGender(object.getGender());
        newObj.setAvatar(object.getAvatar());
        
        try {
            em.merge(newObj);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
    }
    
    @Override
    public void deletePerson(Integer personId) throws SOAPException {
        if (personId == null) {
            throw Utility.createException(Utility.ERROR_SERVER, "Null personId instance!");
        }
        
        if (personId < 1) {
            throw Utility.createException(Utility.ERROR_SERVER, "Invalid personId instance!");
        }
        
        Person object = em.find(Person.class, personId);
        
        if (object == null) {
            throw Utility.createException(Utility.ERROR_CLIENT, "Account does not exist!");
        }
        
        try {
            em.remove(object);
        } catch (Exception e) {            
            throw Utility.createException(Utility.ERROR_SERVER, e.getMessage());
        }
    }

}

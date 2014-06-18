package pl.itcrowd.blog.arquillian_rest_and_persistence.app;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * A REST service for retrieving Customer records
 */
@Stateless
public class CustomerResourceImpl implements CustomerResource {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> getAllCustomers()
    {
        return findAllCustomers();
    }

    @Override
    public Customer getCustomerById(@PathParam("id") long id)
    {
        return findCustomerById(id);
    }

    @Override
    public Customer createCustomer(Customer customer)
    {
        entityManager.persist(customer);
        return customer;
    }

    private List<Customer> findAllCustomers()
    {
        return entityManager.createQuery("from Customer", Customer.class).getResultList();
    }

    private Customer findCustomerById(long id)
    {
        return entityManager.find(Customer.class, id);
    }
}

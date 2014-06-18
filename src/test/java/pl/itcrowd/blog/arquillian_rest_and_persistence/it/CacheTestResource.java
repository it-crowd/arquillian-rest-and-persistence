package pl.itcrowd.blog.arquillian_rest_and_persistence.it;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("/cache-test-controller")
public class CacheTestResource {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @GET
    public void clearCache()
    {
        entityManagerFactory.getCache().evictAll();
    }
}

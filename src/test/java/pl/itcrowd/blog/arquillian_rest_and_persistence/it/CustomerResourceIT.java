package pl.itcrowd.blog.arquillian_rest_and_persistence.it;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.itcrowd.blog.arquillian_rest_and_persistence.app.Customer;
import pl.itcrowd.blog.arquillian_rest_and_persistence.app.CustomerResource;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class CustomerResourceIT {

    @Deployment(testable = false)
    public static Archive createArchive()
    {
        return ShrinkWrap.create(WebArchive.class, "CustomerResourceIT.war")
            .addAsResource("META-INF/persistence.xml")
            .addPackages(true, CustomerResource.class.getPackage());
    }

    @Test
    public void getAllCustomers_always_returnsAllCustomersFromDB(@ArquillianResteasyResource CustomerResource resource) throws Exception
    {
        //        Given

        //        When
        final List<Customer> result = resource.getAllCustomers();

        //        Then
        assertEquals(3, result.size());
    }
}

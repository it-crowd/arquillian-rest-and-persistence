package pl.itcrowd.blog.arquillian_rest_and_persistence.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/customer")
public interface CustomerResource {

    @GET
    List<Customer> getAllCustomers();

    @GET
    @Path("/{id:[1-9][0-9]*}")
    Customer getCustomerById(@PathParam("id") long id);

    @POST
    @Path("/")
    Customer createCustomer(Customer customer);
}

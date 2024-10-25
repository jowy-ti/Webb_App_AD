package test.servidor.resources;

import DB.QueryDB;
import DB.UpdateDB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username,
    @FormParam("password") String password) {
        try {
                System.out.println(username);
                // Se comprueba si se ha enviado un usuario o contraseña vacios
                if(username.isEmpty() || password.isEmpty()) {
                    return Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity("Invalid credentials: null")
                            .build();
                }

                // Se comprueba si existe la cuenta, si existe dejamos pasar
                int res = QueryDB.exists_user(username,password);

                if (res == 0) return Response.accepted().build();
                else return Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid username or password")
                            .build(); //error

            } catch (Exception e) {
                System.err.println(e.getMessage());
                return Response.serverError().build();
            }        
    }
    /**
* POST method to register a new user
* @param username
* @param password
* @param confirmPass
* @return
*/

@Path("registerUser")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)

    public Response registerUser(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("confirmPass") String confirmPass) {
        try {
            
            // Se comprueba si se ha enviado un usuario o contraseña vacios
            if (username.isEmpty() || password.isEmpty()) {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Invalid credentials: null")
                        .build();
            }
            
            if (password.equals(confirmPass)) 
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Invalid credentials: null")
                        .build();

            // Comprabamos si existen las cuentas y en caso de no existir se crean
            int res = QueryDB.exists_user(username, password);

            if (res == -2) {
                res = UpdateDB.add_user(username, password);
                
                if (res == 0) Response.accepted().build();

                else return Response.status(Response.Status.EXPECTATION_FAILED)
                        .entity("Failed to register")
                        .build(); //error
            }
            else return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid username or password")
                        .build(); //error
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        } 
    }
    
    /**
    * POST method to register a new image – File is not uploaded
    * @param title
    * @param description
    * @param keywords
    * @param author
    * @param creator
    * @param capt_date
    * @return
    */
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerImage (@FormParam("title") String title,
    @FormParam("description") String description,
    @FormParam("keywords") String keywords,
    @FormParam("author") String author,
    @FormParam("creator") String creator,
    @FormParam("capture") String capt_date) {

    }
    /**
    * POST method to modify an existing image
    * @param id
    * @param title
    * @param description
    * @param keywords
    * @param author
    * @param creator, used for checking image ownership
    * @param capt_date
    * @return
    */
    @Path("modify")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyImage (@FormParam("id") String id,
    @FormParam("title") String title,
    @FormParam("description") String description,
    @FormParam("keywords") String keywords,
    @FormParam("author") String author,
    @FormParam("creator") String creator,
    @FormParam("capture") String capt_date) {

    }
    /**
    * POST method to delete an existing image
    * @param id
    * @return
    */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage (@FormParam("id") String id) {

    }

    /**
    * GET method to search images by id
    * @param id
    * @return
    */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID (@PathParam("id") int id) {

    }
    /**
    * GET method to search images by title
    * @param title
    * @return
    */
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByTitle (@PathParam("title") String title) {

    }
    /**
    * GET method to search images by creation date. Date format should be
    * yyyy-mm-dd
    * @param date
    * @return
    */
    @Path("searchCreationDate/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByCreationDate (@PathParam("date") String date) {

    }
    /**
    * GET method to search images by author
    * @param author
    * @return
    */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByAuthor (@PathParam("author") String author) {

    }
    /**
    * GET method to search images by keyword
    * @param keywords
    * @return
    */
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByKeywords (@PathParam("keywords") String keywords) {

    }
}

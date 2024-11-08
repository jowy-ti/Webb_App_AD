package test.servidor.resources;

import DB.QueryDB;
import DB.QueryDB.Image;
import DB.UpdateDB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    /**
    * POST method to login in the application
    * @param username
    * @param password
    * @return
    */
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
                            .entity("{\"error\": \"Invalid credentials: null\"}")
                            .build();
                }

                // Se comprueba si existe la cuenta, si existe dejamos pasar
                int res = QueryDB.exists_user(username,password);

                if (res == 0) return Response.ok().build();
                else return Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"error\": \"Invalid credentials\"}")
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
                        .entity("{\"error\": \"Invalid credentials: null\"}")
                        .build();
            }
            
            if (password.equals(confirmPass)) 
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("{\"error\": \"Invalid credentials\"}")
                        .build();

            // Comprabamos si existen las cuentas y en caso de no existir se crean
            int res = QueryDB.exists_user(username, password);

            if (res == -2) {
                res = UpdateDB.add_user(username, password);
                
                if (res == 0) Response.ok().build();

                else return Response.status(Response.Status.EXPECTATION_FAILED)
                        .entity("{\"error\": \"Failed to register\"}")
                        .build(); //error
            }
            else return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"Invalid username or password\"}")
                        .build(); //error
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        } 
        return null;
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
        //No dejamos que ningun parametro sea null
        if (title.isEmpty() || description.isEmpty() || keywords.isEmpty() ||
                author.isEmpty() || creator.isEmpty() || capt_date.isEmpty()) 
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("{\"error\": \"Some field is null\"}")
                        .build();
        
        //filename no importa, le ponemos el mismo que el titulo
        int res = DB.UpdateDB.add_image(title, creator, keywords, author, creator, capt_date, title);
        
        if (res == 0) //todo bien
            return Response.ok().build();
        else return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed registring the image\"}")
                .build();

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
        
        int num = Integer.parseInt(id);
        int res = DB.UpdateDB.update_image(title, description, keywords, author, capt_date, title, num);
        
        if (res == 0) //todo bien
            return Response.ok().build();
        else return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed modifying the image\"}")
                .build();

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
        int num = Integer.parseInt(id);
        int res = DB.UpdateDB.delete_image(num);
        
        if (res == 0) //todo bien
            return Response.ok().build();
        else return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed deleting the image\"}")
                .build();
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
        ArrayList<Image> Images = QueryDB.search_image(id, null, "id");
        
        if (Images != null) {
            Gson gson = new Gson();
            String json = gson.toJson(Images);
        
            return Response.ok(json)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\":\"Data not found for ID: " + id + "\"}")
                               .build();
        }
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
        ArrayList<Image> Images = QueryDB.search_image(-1, title, "title");
        
        if (Images != null) {
            Gson gson = new Gson();
            String json = gson.toJson(Images);
        
            return Response.ok(json)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\":\"Data not found for Title: " + title + "\"}")
                               .build();
        }
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
        ArrayList<Image> Images = QueryDB.search_image(-1, date, "capture_date");
        
        if (Images != null) {
            Gson gson = new Gson();
            String json = gson.toJson(Images);
        
            return Response.ok(json)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\":\"Data not found for Capture_date: " + date + "\"}")
                               .build();
        }
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
        ArrayList<Image> Images = QueryDB.search_image(-1, author, "author");
        
        if (Images != null) {
            Gson gson = new Gson();
            String json = gson.toJson(Images);
        
            return Response.ok(json)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\":\"Data not found for Author: " + author + "\"}")
                               .build();
        }
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
        ArrayList<Image> Images = QueryDB.search_image(-1, keywords, "keywords");
        
        if (Images != null) {
            Gson gson = new Gson();
            String json = gson.toJson(Images);
        
            return Response.ok(json)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\":\"Data not found for Keywords: " + keywords + "\"}")
                               .build();
        }
    }
}

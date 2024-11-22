package test.servidor.resources;

import DB.QueryDB;
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
import image.Image;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
                // Se comprueba si existe la cuenta, si existe dejamos pasar
                int res = QueryDB.exists_user(username,password);

                if (res == 0) return Response.ok().build();
                else return Response.status(Response.Status.NOT_ACCEPTABLE)
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
            
            // Comprabamos si existen las cuentas y en caso de no existir se crean
            int res = QueryDB.exists_user(username, password);

            if (res == -2) {
                res = UpdateDB.add_user(username, password);
                
                if (res == 0) return Response.ok().build();

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
    }
    
     /** 
    * POST method to register a new image 
    * @param title 
    * @param description 
    * @param keywords      
    * @param author 
    * @param creator 
    * @param capt_date     
    * @param filename     
    * @param fileInputStream     
    * @param fileMetaData     
    * @return 
    */ 
    @Path("register") 
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Produces(MediaType.APPLICATION_JSON) 
    public Response registerImage (@FormDataParam("title") String title, 
            @FormDataParam("description") String description, 
            @FormDataParam("keywords") String keywords, 
            @FormDataParam("author") String author, 
            @FormDataParam("creator") String creator, 
            @FormDataParam("capture") String capt_date,
            @FormDataParam("filename") String filename,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        
        Integer StatusCode = 201;                
   
        if (!writeImage(filename, fileInputStream)) { //no sha pogut guardar la imatge            
            return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed registering the image\"}")
                .build();
        }
        
        int res = DB.UpdateDB.add_image(title, creator, keywords, author, creator, capt_date, title);
        
        if (res == 0) //todo bien
            return Response.ok().build();
        else return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed registring the image\"}")
                .build();
    }
       
    public static Boolean writeImage(String filename, Part part) 
        throws IOException {
        makeDirIfNotExists();
        
        InputStream content = part.getInputStream();
        
        File targetfile = new File("/var/webapp/uploads/server/" + filename);
        
        java.nio.file.Files.copy(
                content,
                targetfile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
        
        return true;
    }
    public static Boolean writeImage(String filename, InputStream fileInputStream)  {
        try{
            makeDirIfNotExists();
            File targetfile = new File("/var/webapp/uploads/server/" + filename);
        
            java.nio.file.Files.copy(
                    fileInputStream,
                    targetfile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );  
        } catch (IOException e){
            return false;
        }
        return true; 
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
    * @param filename
    * @param fileInputStream     
    * @param fileMetaData   
    * @return
    */
    @Path("modify")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyImage (@FormParam("id") String id,
    @FormDataParam("title") String title,
    @FormDataParam("description") String description,
    @FormDataParam("keywords") String keywords,
    @FormDataParam("author") String author,
    @FormDataParam("creator") String creator,
    @FormDataParam("capture") String capt_date,
    @FormDataParam("filename") String filename,
    @FormDataParam("file") InputStream fileInputStream,
    @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        
        int num = Integer.parseInt(id);
        String old_filename = DB.QueryDB.get_filename(num);
        
        if (filename.equals("old_image")) {
            
            if (!deleteFile(old_filename)) 
                return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed deleting the image\"}")
                .build();
            
            if (!writeImage(filename, fileInputStream))  //no sha pogut guardar la imatge            
                return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed modifying the image\"}")
                .build();  
            
        }
        
        
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
        
        String filename = DB.QueryDB.get_filename(num);
        if (!deleteFile(filename)) 
            return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed deleting the image\"}")
                .build();
        
        int res = DB.UpdateDB.delete_image(num);
        if (res == 0) //todo bien
            return Response.ok().build();
        else return Response.status( Response.Status.EXPECTATION_FAILED)
                .entity("{\"error\": \"Failed deleting the image\"}")
                .build();
    }
    
    public static Boolean deleteFile(String filename) 
    {
        makeDirIfNotExists();
        
        File targetfile = new File("/var/webapp/uploads/server/" + filename);
        if(! targetfile.delete()) {
            System.out.println("ERROR: Failed to delete " + targetfile.getAbsolutePath());
            return false;
        }
       
        System.out.println("SUCCESS: deleted " + targetfile.getAbsolutePath());
        return true;
    }
    

    /**
    * GET method to search images by id
    * @param id
    * @return
     * @throws java.io.FileNotFoundException
    */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID (@PathParam("id") int id) throws FileNotFoundException, IOException {
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
                               .entity("{\"error\":\"Data not found for Id: " + id + "\"}")
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
    
    @Path("searchTitle_Author/{title}/{author}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByTitle_Author (@PathParam("title") String title, @PathParam("author") String author) {
        
        ArrayList<Image> Images = QueryDB.search_image2(title, author);
        
        if (Images != null) {
            Gson gson = new Gson();
            String json = gson.toJson(Images);
        
            return Response.ok(json)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\":\"Data not found for Title: " + title + "and Author: +" + author + "\"}")
                               .build();
        }
    }
    
    @Path("getimage/{filename}")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetImage (@PathParam("filename") String filename) throws FileNotFoundException {
        
        try {
            
            if (filename == null) {
                return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"Data not found for filename: " + filename + "\"}")
                       .type(MediaType.APPLICATION_JSON)
                       .build();
            }

            final String path = "/var/webapp/uploads/server/" + filename;
            File image_f = new File(path);
        
            if (!image_f.exists() || !image_f.canRead()) {
                return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"File not found or inaccessible: " + filename + "\"}")
                       .type(MediaType.APPLICATION_JSON)
                       .build();
            }
            
            String type = Files.probeContentType(image_f.toPath());
        
            if (type == null) type = "application/octet-stream"; // Tipo predeterminado
        
            InputStream image = new FileInputStream(image_f);
            
            return Response.status(Response.Status.OK).entity(image)
                    .header("Content-Type", type)
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .build();
            
        } catch (IOException e) {
        // Manejo genérico de errores
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("{\"error\":\"An unexpected error occurred: " + e.getMessage() + "\"}")
                       .type(MediaType.APPLICATION_JSON)
                       .build();
        }
    }
    
    private static void makeDirIfNotExists() {
        File dir = new File("/var/webapp/uploads/server/");
        // Creamos directorio si no existe.
        if (! dir.exists() ) {
           dir.mkdirs();
        }
    }
}

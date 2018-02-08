/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.helper.FileHelper;
import java.io.File;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author j-m_d
 */
@Path("files")
public class FileRoutes {

    @GET
    @Path("avatar/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getJson(@PathParam("filename") String filename) {
        try {
            File file = FileHelper.fetchAvatarFile(filename);
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
}

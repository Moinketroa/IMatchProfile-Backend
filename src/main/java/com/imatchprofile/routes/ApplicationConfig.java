/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author MasterChief
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.imatchprofile.routes.ApplyRoutes.class);
        resources.add(com.imatchprofile.routes.CandidateRoutes.class);
        resources.add(com.imatchprofile.routes.CrossOriginResourceSharingFilter.class);
        resources.add(com.imatchprofile.routes.ExperienceRoutes.class);
        resources.add(com.imatchprofile.routes.FileRoutes.class);
        resources.add(com.imatchprofile.routes.JobRoutes.class);
        resources.add(com.imatchprofile.routes.LoginRoutes.class);
        resources.add(com.imatchprofile.routes.MasterRoutes.class);
        resources.add(com.imatchprofile.routes.MatchingRoutes.class);
        resources.add(com.imatchprofile.routes.MediaRoutes.class);
        resources.add(com.imatchprofile.routes.ModeratorRoutes.class);
        resources.add(com.imatchprofile.routes.NeedRoutes.class);
        resources.add(com.imatchprofile.routes.RecruiterRoutes.class);
        resources.add(com.imatchprofile.routes.TokenRoutes.class);
        resources.add(com.imatchprofile.routes.TrainingRoutes.class);
        resources.add(com.imatchprofile.routes.UserRoutes.class);
    }  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.MediaDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPBadUploadFormatException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.helper.FileHelper;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Media;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author j-m_d
 */
public class MediaService extends Service {
    
    private final UserDAO userDAO = new UserDAO();
    private final MediaDAO mediaDAO = new MediaDAO();
    
    public String addMedia(String content, Integer userId) throws IMPException {
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        
        String filename, filetype, value;
        try {
            filename = payload.getString("filename");
            filetype = payload.getString("filetype");
            value = payload.getString("value");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
            
        //verification de l'existence des champs
        if (oneOfIsNull(filename, filetype, value))
            throw new IMPPayloadException();
            
        if (!(filetype.equals("image/png") || filetype.equals("image/jpeg") || filetype.equals("application/pdf")))
            throw new IMPBadUploadFormatException("image or pdf");
        
        //recuperation du user authentifié
        User userFound = userDAO.findById(userId);
        
        //verification si id trouvé
        if (userFound == null)
            throw new IMPNotAUserException();
        
        Candidate candidate = userFound.getCandidate();
        
        //verification si le user est un candidat
        if (candidate == null)
            throw new IMPNotACandidateException();
        
        //importation du media
        String fileUrl = FileHelper.writeUploadBase64ToFile(filename, value);
        
        //creation du media (entite)
        Media media = new Media(candidate, fileUrl);
        
        mediaDAO.create(media);
        
        return media.toJSON().toString();
    }
    
}

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
import com.imatchprofile.exceptions.IMPTooMuchMediasException;
import com.imatchprofile.helper.FileHelper;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Media;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import org.json.JSONArray;
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
        JSONArray payload = new JSONArray(content);
        
        //verification que pas plus de 3 fichiers
        if (payload.length() > 3) 
            throw new IMPTooMuchMediasException(3);
            
        //recuperation du user authentifié
        User userFound = userDAO.findById(userId);
        
        //verification si id trouvé
        if (userFound == null)
            throw new IMPNotAUserException();
        
        Candidate candidate = userFound.getCandidate();
        
        //verification si le user est un candidat
        if (candidate == null)
            throw new IMPNotACandidateException();
        
        //check du nombre des medias existants
        int nbMediaCandidat = candidate.getMedias().size();
        if (payload.length() > (3 - nbMediaCandidat))
            throw new IMPTooMuchMediasException(3 - nbMediaCandidat);
        
        JSONArray responseMedias = new JSONArray();
        
        for (Object mediaObject : payload) {
            
            JSONObject mediaJson = (JSONObject) mediaObject;
        
            String filename, filetype, value;
            try {
                filename = mediaJson.getString("filename");
                filetype = mediaJson.getString("filetype");
                value = mediaJson.getString("value");
            } catch (JSONException e) {
                throw new IMPPayloadException();
            }

            //verification de l'existence des champs
            if (oneOfIsNull(filename, filetype, value))
                throw new IMPPayloadException();

            if (!(filetype.equals("image/png") || filetype.equals("image/jpeg") || filetype.equals("application/pdf")))
                throw new IMPBadUploadFormatException("image or pdf");

            //importation du media
            String fileUrl = FileHelper.writeUploadBase64ToFile(filename, value);

            //creation du media (entite)
            Media media = new Media(candidate, fileUrl);

            mediaDAO.create(media);
        
            responseMedias.put(media.toJSON());
        }
        
        return responseMedias.toString();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.dao.RecruiterDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPBadFormatException;
import com.imatchprofile.exceptions.IMPBadUploadFormatException;
import com.imatchprofile.exceptions.IMPEmailAlreadyTakenException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.helper.PasswordHelper;
import com.imatchprofile.helper.UploadHelper;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MasterChief
 */
public class UserService extends Service {

    protected final CandidateDAO candidateDAO = new CandidateDAO();
    protected final RecruiterDAO recruiterDAO = new RecruiterDAO();
    protected final UserDAO userDAO = new UserDAO();
    
    public String[] signInToVerif(String content) throws IMPException {
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        JSONObject avatar = null;
        String[] tabContent = new String[5];
        try {
            tabContent[0] = payload.getString("lastname");
            tabContent[1] = payload.getString("firstname");
            tabContent[2] = payload.getString("email");
            tabContent[3] = payload.getString("password");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        
        //recupération de photoUrl
        try {
            tabContent[4] = payload.getString("photoUrl");
        } catch (JSONException e) {
            tabContent[4] = null;
        }
        //recuperation de avatar
        try {
            avatar = payload.getJSONObject("avatar");
        } catch (JSONException e) {
            avatar = null;
        }
        
        //verification de l'existence des champs
        if (oneOfIsNull(tabContent[0], tabContent[1], tabContent[2], tabContent[3]))
            throw new IMPPayloadException();
        
        //verification de l'email
        if (!EmailValidator.getInstance().isValid(tabContent[2]))
            throw new IMPBadFormatException("email");

        //verification si email déjà présent
        User userFoundByEmail = userDAO.findOneByEmail(tabContent[2]);
        if (userFoundByEmail != null)
            throw new IMPEmailAlreadyTakenException();
        //chiffrage du password
        tabContent[3] = PasswordHelper.encryptPassword(tabContent[3]);
        
        //recuperation et verification de l'url de l'avatar
        tabContent[4] = decideAvatarUrl(tabContent[4], avatar);
        return tabContent;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public CandidateDAO getCandidateDAO() {
        return candidateDAO;
    }

    public RecruiterDAO getRecruiterDAO() {
        return recruiterDAO;
    }
    
    public String getAllUsers(){
        List<User> listUsers = userDAO.findAll();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i=0; i < listUsers.size()-1; i++)
            sb.append(listUsers.get(i).toJSON()+",\n");
        sb.append(listUsers.get(listUsers.size()-1).toJSON());
        sb.append("\n]");
        return sb.toString();
    }

    private String decideAvatarUrl(String photoUrl, JSONObject avatar) throws IMPException {
        //verification si photoUrl ou avatar
        if (photoUrl == null) {
            if (avatar == null) { //pas de photoUrl ni d'avatar
                throw new IMPPayloadException();
            }
            //pas de photoUrl mais un avatar
            String filename, filetype, value;
            try {
                filename = avatar.getString("filename");
                filetype = avatar.getString("filetype");
                value = avatar.getString("value");
            } catch (JSONException e) {
                throw new IMPPayloadException();
            }
            
            if (!(filetype.equals("image/png")))
                throw new IMPBadUploadFormatException("png");
            
            try {
                return UploadHelper.writeByteArraysToFile(filename, value);
            } catch (IOException ex) {
                throw new IMPInternalServerException("unable to write file");
            }
        } else {
            //verification de l'url de la photo
            if (!UrlValidator.getInstance().isValid(photoUrl))
                throw new IMPBadFormatException("url");
            
            return photoUrl;
        }
    }
}

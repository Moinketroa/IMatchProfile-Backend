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
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPVerifWrongPassword;
import com.imatchprofile.exceptions.IMPWrongPassword;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.helper.PasswordHelper;
import com.imatchprofile.helper.FileHelper;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
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
            
            //verification de l'existence des champs
            if (oneOfIsNull(filename, filetype, value))
                throw new IMPPayloadException();
            
            if (!(filetype.equals("image/png") || filetype.equals("image/jpeg")))
                throw new IMPBadUploadFormatException("image");
            
            return FileHelper.writeAvatarBase64ToFile(filename, value);
        } else {
            //verification de l'url de la photo
            if (!UrlValidator.getInstance().isValid(photoUrl))
                throw new IMPBadFormatException("url");
            
            return photoUrl;
        }
    }
    
    public String editBasicUser(String id, String content, Integer userId) throws IMPException {
        //verification du parametre
        if(!isInteger(id) || id == null)
            throw new IMPWrongURLParameterException();
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        String lastname, firstname, email;
        try {
            lastname = payload.getString("lastname");
            firstname = payload.getString("firstname");
            email = payload.getString("email");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(lastname, firstname, email))
            throw new IMPPayloadException();
        //verification de l'email
        if (!EmailValidator.getInstance().isValid(email))
            throw new IMPBadFormatException("email");
        //verification si email déjà présent
        User userFoundByEmail = userDAO.findOneByEmail(email);
        if (userFoundByEmail != null){
            if (userFoundByEmail.getUserId() != userId)
                throw new IMPEmailAlreadyTakenException();
        }
            
        //recuperation du user authentifié
        User userFound = userDAO.findById(userId);
        //verification si id trouvé
        if (userFound == null)
            throw new IMPNotAUserException();
        userFound.setLastname(lastname);
        userFound.setFirstname(firstname);
        userFound.setEmail(email);
        userDAO.editUser(userFound);
        return userFound.toJSON().toString();
    }
    
    public String editPwdUser(String id, String content, Integer userId) throws IMPException {
        //verification du parametre
        if(!isInteger(id) || id == null)
            throw new IMPWrongURLParameterException();
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        String password, newPassword1, newPassword2;
        try {
            password = payload.getString("password");
            newPassword1 = payload.getString("newPassword1");
            newPassword2 = payload.getString("newPassword2");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(password, newPassword1, newPassword2))
            throw new IMPPayloadException();
        //verification newPassword1 = newPassword2
        if(!newPassword1.equals(newPassword2))
            throw new IMPVerifWrongPassword();
        //recuperation du user authentifié
        User userFound = userDAO.findById(userId);
        //verification si id trouvé
        if (userFound == null)
            throw new IMPNotAUserException();
        //verification password
        if (!(userFound.getPassword().equals(PasswordHelper.encryptPassword(password))))
            throw new IMPWrongPassword();
        
        userFound.setPassword(PasswordHelper.encryptPassword(newPassword1));
        userDAO.editUser(userFound);
        return userFound.toJSON().toString();
    }
}

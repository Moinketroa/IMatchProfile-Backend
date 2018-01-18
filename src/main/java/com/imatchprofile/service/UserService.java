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
import com.imatchprofile.exceptions.IMPEmailAlreadyTakenException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
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
        String[] tabContent = new String[5];
        try {
            tabContent[0] = payload.getString("lastname");
            tabContent[1] = payload.getString("firstname");
            tabContent[2] = payload.getString("email");
            tabContent[3] = payload.getString("password");
            tabContent[4] = payload.getString("photoUrl");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(tabContent[0], tabContent[1], tabContent[2], tabContent[3], tabContent[4]))
            throw new IMPPayloadException();
        //verification de l'email et de l'url de la photo
        if (!EmailValidator.getInstance().isValid(tabContent[2]))
            throw new IMPBadFormatException("email");
        if (!UrlValidator.getInstance().isValid(tabContent[4]))
            throw new IMPBadFormatException("url");
        //verification si email déjà présent
        User userFoundByEmail = userDAO.findOneByEmail(tabContent[2]);
        if (userFoundByEmail != null)
            throw new IMPEmailAlreadyTakenException();
        //chiffrage du password
        tabContent[3] = User.encryptPassword(tabContent[3]);
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
}

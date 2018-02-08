/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.helper;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @author j-m_d
 */
public class FileHelper {
    
    private static final String SERVER_URL = "http://localhost:8080/IMatchProfile-Backend/api/files/";
    
    private static final String UPLOADED_FOLDER = "/uploadedFiles/uploads/";
    private static final String AVATAR_FOLDER = "/uploadedFiles/avatar/";
    
    public static String writeAvatarBase64ToFile(String fileName, String content) throws IMPException { 
        createFolderIfNotExists(AVATAR_FOLDER);
        
        Date now = new Date();
        String name = now.getTime() + fileName;
        
        writeBase64ToFile(AVATAR_FOLDER + name, content);
        
        return SERVER_URL + "avatar/" + name;
    }
    
    public static String writeUploadBase64ToFile(String fileName, String content) throws IMPException {
        createFolderIfNotExists(UPLOADED_FOLDER);
        
        Date now = new Date();
        String name = now.getTime() + fileName;
        
        writeBase64ToFile(UPLOADED_FOLDER + name, content);
        
        return SERVER_URL + "upload/" + name;
    }
    
    private static void writeBase64ToFile(String fullFileName, String content) throws IMPException {
        BufferedOutputStream writer = null;
        
        try {
            File file = new File(fullFileName);
            writer = new BufferedOutputStream(new FileOutputStream(file));
            writer.write(Base64.getDecoder().decode(content));
            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            throw new IMPInternalServerException("cannot create file - file not found");
        } catch (IOException ex) {
            throw new IMPInternalServerException("cannot create file - io exception");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                throw new IMPInternalServerException("cannot close writer");
            }
        }
    }
    
    private static void createFolderIfNotExists(String dirName) throws IMPException {
        try {
            File theDir = new File(dirName);
            if (!theDir.exists()) {
                theDir.mkdir();
            }
        } catch (SecurityException ex) {
            throw new IMPInternalServerException("cannot create folder");
        }
    }
    
    public static File fetchAvatarFile(String filename){
        return new File(AVATAR_FOLDER + filename);
    }
    
    public static File fetchUploadedFile(String filename){
        return new File(UPLOADED_FOLDER + filename);
    }
}

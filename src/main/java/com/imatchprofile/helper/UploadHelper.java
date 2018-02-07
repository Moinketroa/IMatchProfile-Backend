/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @author j-m_d
 */
public class UploadHelper {
    
    private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";
    
    public static String writeByteArraysToFile(String fileName, String content) throws IOException {
        Date now = new Date();
        File file = new File(UPLOAD_FOLDER + now.getTime() + fileName + ".png");
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        
        writer.write(Base64.getDecoder().decode(content));
        writer.flush();
        writer.close();
        
        return file.getAbsolutePath();
    }
    
}

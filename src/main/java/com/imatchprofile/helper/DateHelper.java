/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.helper;

import com.imatchprofile.exceptions.IMPException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author j-m_d
 */
public class DateHelper {
    
    public static String getPrettyDate(Date date) {
        Instant instantCreated = date.toInstant();
        ZoneId z = ZoneId.of( "Europe/Paris" );
        ZonedDateTime zdt = instantCreated.atZone( z );
        return zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace( "T" , " " );
    }
    public static Date converStringToDate(String datestring){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
       

        try {
            Date date = formatter.parse(datestring);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
               return null;
        }
    }
    
}

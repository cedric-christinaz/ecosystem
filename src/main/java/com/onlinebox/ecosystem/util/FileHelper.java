
package com.onlinebox.ecosystem.util;

/**
 * This class contains some static method that help to manipulate file more easily.
 * @author cedric
 */
public class FileHelper {
    
    /**
     * This method takes a mime type and return the corresponding extension (whitout the .).
     * @param mimeType mime type (for example: image/png)
     * @return the corresponding extension (for example: png)
     */
    public static String getExtensionFromMimeType(String mimeType){
        int start = mimeType.lastIndexOf("/");
        if(start == -1) {
            return "";
        }
        return mimeType.substring(start+1);
    }
    
}

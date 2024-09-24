package AnnualDetailedAuditReport.util;

import java.io.IOException;
import java.io.InputStream;

public class FileDetctionUtil {
	
	/**
     * Get the first 4 byte of a file file signature. 
     * 
     * @param part File from part.
     */
     public static byte[] fileSignature(InputStream is)
             throws IOException, NullPointerException {
    	 byte[] data = new byte[4];
         is.read(data, 0, 4);
         return data;
     }
	
	 /**
     * Get the file type based on the file signature.
     * Here restricted to only recognized file type jpeg, jpg, png and
     * pdf where the signature of jpg and jpeg files are the same.
     *
     * @param fileData Byte array of the file.
     * @return String of the file type.
     */
	public static String getFileType(byte[] fileData) {
        String type = "undefined";
        if(Byte.toUnsignedInt(fileData[0]) == 0x89 && Byte.toUnsignedInt(fileData[1]) == 0x50)
            type = "png";
        else if(Byte.toUnsignedInt(fileData[0]) == 0xFF && Byte.toUnsignedInt(fileData[1]) == 0xD8)
            type = "jpg";
        else if(Byte.toUnsignedInt(fileData[0]) == 0x25 && Byte.toUnsignedInt(fileData[1]) == 0x50)
            type = "pdf";

       return type;
   }
}

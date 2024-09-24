package com.monthly.pfm.util;

public class FileTypeDetection {

	private String getFileType(byte[] fileData) {
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

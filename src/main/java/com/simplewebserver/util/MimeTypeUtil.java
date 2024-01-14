package com.simplewebserver.util;

import lombok.extern.slf4j.Slf4j;

import static com.simplewebserver.connector.Constants.DEFAULT_CONTENT_TYPE;


@Slf4j
public class MimeTypeUtil {


    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static String getTypes(String fileName) throws Exception {
        if (fileName.endsWith(".html")) {
            return DEFAULT_CONTENT_TYPE;
        }
        String fileExtension = getFileExtension(fileName);
        return MimeTypeEnum.getByExtension(fileExtension).getMimeType();
    }

}



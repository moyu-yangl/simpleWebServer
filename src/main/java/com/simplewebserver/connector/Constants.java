/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.simplewebserver.connector;


import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Constants.
 *
 * @author Remy Maucherat
 */
public final class Constants {


    // -------------------------------------------------------------- Constants


    public static final int DEFAULT_CONNECTION_LINGER = -1;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final boolean DEFAULT_TCP_NO_DELAY = true;


    /**
     * CRLF.
     */
    public static final String CRLF = "\r\n";
    public static final String BLANK = " ";
    public static final String UTF_8 = "UTF-8";
    public static final ArrayList<String> STATIC_RESOURCE;
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
    public static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
    /**
     * CR.
     */
    public static final byte CR = (byte) '\r';
    /**
     * LF.
     */
    public static final byte LF = (byte) '\n';
    /**
     * SP.
     */
    public static final byte SP = (byte) ' ';
    /**
     * HT.
     */
    public static final byte HT = (byte) '\t';
    /**
     * COLON.
     */
    public static final byte COLON = (byte) ':';
    /**
     * SEMI_COLON.
     */
    public static final byte SEMI_COLON = (byte) ';';
    /**
     * 'A'.
     */
    public static final byte A = (byte) 'A';
    /**
     * 'a'.
     */
    public static final byte a = (byte) 'a';
    /**
     * 'Z'.
     */
    public static final byte Z = (byte) 'Z';
    /**
     * '?'.
     */
    public static final byte QUESTION = (byte) '?';
    /**
     * Lower case offset.
     */
    public static final byte LC_OFFSET = A - a;
    /* Various constant "strings" */
    public static final String CONNECTION = "Connection";
    public static final String CLOSE = "close";
    public static final String KEEPALIVE = "keep-alive";
    public static final String CHUNKED = "chunked";
    public static final String TRANSFERENCODING = "Transfer-Encoding";
    /**
     * Identity filters (input and output).
     */
    public static final int IDENTITY_FILTER = 0;
    /**
     * Chunked filters (input and output).
     */
    public static final int CHUNKED_FILTER = 1;
    /**
     * Void filters (input and output).
     */
    public static final int VOID_FILTER = 2;
    /**
     * GZIP filter (output).
     */
    public static final int GZIP_FILTER = 3;
    /**
     * Buffered filter (input)
     */
    public static final int BUFFERED_FILTER = 3;
    /**
     * HTTP/1.0.
     */
    public static final String HTTP_10 = "HTTP/1.0";
    /**
     * HTTP/1.1.
     */
    public static final String HTTP_11 = "HTTP/1.1";
    /**
     * GET.
     */
    public static final String GET = "GET";
    /**
     * HEAD.
     */
    public static final String HEAD = "HEAD";
    /**
     * POST.
     */
    public static final String POST = "POST";

    static {
        STATIC_RESOURCE = new ArrayList<>();
        STATIC_RESOURCE.add(".html");
        STATIC_RESOURCE.add(".css");
        STATIC_RESOURCE.add(".js");
        STATIC_RESOURCE.add(".png");
        STATIC_RESOURCE.add(".jpeg");
        STATIC_RESOURCE.add(".ioc");
    }
}

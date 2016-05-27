/*
 * Copyright (c) 2016 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.html;

/**
 * 
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class Tools {

    public static String escape(String str) {
        if (str == null) {
            return "";
        }
        
        String out = str;
        out = out.replace("&", "&amp;");
        out = out.replace("<", "&lt;");
        out = out.replace(">", "&gt;");
        return out;
    }
}

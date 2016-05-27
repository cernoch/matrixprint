/*
 * Copyright (c) 2016 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.html;

import cz.cvut.felk.ida.cybercalc.print.io.Printer;
import cz.cvut.felk.ida.cybercalc.print.model.HeadedTable;

/**
 * 
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class TablePage extends SingleTable {

    protected final String title;
    
    public TablePage(HeadedTable<String> mat, String title, Printer out) {
        super(mat, out);
        this.title = title;
    }

    public void headerOnly() {
        out.a("<!DOCTYPE html>").nl();
        out.a("<title>").a(title).a("</title>").nl();
        out.a("<style type='text/css'>").nl();
        out.a("table.printed { border-collapse: collapse }").nl();
        out.a("table.printed .filled { padding: 0.25em 0.5em }").nl();
        out.a("table.printed .filled { padding: 0.25em 0.5em }").nl();
        out.a("table.printed .body:nth-child(odd)     { background: #F6F6F6 }").nl();
        out.a("table.printed .body:nth-child(even)    { background: #EAEAEA }").nl();
        out.a("table.printed .body:nth-child(odd)  th { background: #F0F0F0 }").nl();
        out.a("table.printed .body:nth-child(even) th { background: #E4E4E4 }").nl();
        out.a("</style>").nl();
    }
    
    @Override
    public Void call() {
        headerOnly();
        return super.call();
    }
    
    public static void print(
            HeadedTable<String> mat,
            String title, Printer out) {
        
        new TablePage(mat, title, out).call();
    }
}

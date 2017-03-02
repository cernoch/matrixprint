/* 
 * The MIT License
 *
 * Copyright 2017 Radomír Černoch (radomir.cernoch at gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.cernoch.matrixprint.html;

import io.github.cernoch.matrixprint.io.Printer;
import io.github.cernoch.matrixprint.model.HeadedTable;

/**
 * Prints a HTML header before the table.
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

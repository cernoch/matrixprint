/*
 * Copyright (c) 2016 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.html;

import cz.cvut.felk.ida.cybercalc.print.io.Printer;
import cz.cvut.felk.ida.cybercalc.print.model.HeadedTable;
import cz.cvut.felk.ida.cybercalc.print.model.StringMatrix;
import java.util.concurrent.Callable;

/**
 * 
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class SingleTable implements Callable<Void> {

    protected final HeadedTable<String> mat;
    
    protected final Printer out;

    public SingleTable(HeadedTable<String> mat, Printer out) {
        this.mat = mat;
        this.out = out;
    }
    
    protected Printer indent(int level) {
        for (int i = 0; i < level; i++) {
            out.a("\t");
        }
        return out;
    }
    
    @Override
    public Void call() {
        indent(1).a("<table class='printed'>").nl();
        printColumnTitle(mat.colHead(), "head");

        int rowHeadCols = mat.rowHead().cols();
        int rowTailCols = mat.rowTail().cols();
        
        for (int row = 0; row < mat.rows(); row++) {
            indent(2).a("<tr class='body'>").nl();
            
            for (int aux = 0; aux < rowHeadCols; aux++) {
                printOneCell(mat.rowHead().get(aux, row), true, true);
            }
            
            for (int col = 0; col < mat.cols(); col++) {
                printOneCell(mat.get(col, row), false, true);
            }
            
            for (int aux = 0; aux < rowTailCols; aux++) {
                printOneCell(mat.rowTail().get(aux, row), true, true);
            }
            indent(2).a("</tr>").nl();
        }
        
        
        printColumnTitle(mat.colTail(), "foot");
        indent(1).a("</table>").nl();
        return null;
    }
    
    private void printColumnTitle(StringMatrix data, String name) {
        
        int rowHeadCols = mat.rowHead().cols();
        int rowTailCols = mat.rowTail().cols();
        
        for (int row = 0; row < data.rows(); row++) {
            indent(2).a("<tr class='").a(name).a("'>").nl();
            
            for (int aux = 0; aux < rowHeadCols; aux++) {
                printOneCell(null, true, false);
            }
            
            for (int col = 0; col < data.cols(); col++) {
                printOneCell(data.get(col, row), true, true);
            }
            
            for (int aux = 0; aux < rowTailCols; aux++) {
                printOneCell(null, true, false);
            }
            
            indent(2).a("</tr>").nl();
        }
    }
    
    protected void printOneCell(String value, boolean head, boolean filled) {
        
        String tag = head ? "th" : "td";
        String cls = filled ? "filled" : "filler";
        String val = Tools.escape(value);

        indent(3).a("<").a(tag).a(" class='").a(cls).a("'>")
                .a(val).a("</").a(tag).a(">").nl();
    }
}

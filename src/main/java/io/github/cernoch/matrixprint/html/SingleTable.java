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
import io.github.cernoch.matrixprint.model.StringMatrix;
import java.util.concurrent.Callable;

/**
 * Print a table in HTML format.
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

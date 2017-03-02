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
package io.github.cernoch.matrixprint.txt;

import io.github.cernoch.matrixprint.io.Printer;
import io.github.cernoch.matrixprint.model.Groupping;
import io.github.cernoch.matrixprint.model.HeadedTable;
import io.github.cernoch.matrixprint.model.StringMatrix;

/**
 * Prints a {@link HeadedTable} of {@code char}s to a printer.
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public final class ComplexTable {
    private ComplexTable() {}
    
    private static void repeat(String s, int n, Printer p) {
        for (int i = 0; i < n; i++) {
            p.a(s);
        }
    }
    
    /**
     * Print the table.
     * 
     * @param t table to be printed
     * @param g define column and row groups
     * @param p printer accepts the textual output
     */
    public static void asciiTable(
            HeadedTable<Character> t,
            Groupping g, Printer p) {

        int rowHeadWidth = 0; // Width of the row header
        for (int rhCol = 0; rhCol < t.rowHead().cols(); rhCol++) {
            rowHeadWidth += 1 + t.rowHead().maxColLen(rhCol);
        }
        //p.nl();

        colTitles(t.colHead(), rowHeadWidth, true, g, p);

        // Separate head from the cells
        rowSeparator(t, g, p);
        
        // Now the actual rows
        int rowGrp = 0, rowEnd = -1;
        for (int row = 0; row < t.rows(); row++) {

            rowTitle(t.rowHead(), row, g, p);

            { // Actual values
                p.a("|");
                int colGrp = 0, colEnd = -1;
                for (int col = 0; col < t.cols(); col++) {

                    p.a(String.valueOf(t.get(col, row)));
                    
                    if (col == colEnd) {
                        p.a("|");
                    }
                    while (colEnd <= col && colGrp < g.groups()) {
                        colEnd = g.lastCol(colGrp++);
                    }
                }
                
                if (colEnd == -1) {
                    p.a("|");
                }
            }
            
            // Tail of each row
            for (int col = 0; col < t.rowTail().cols(); col++) {
                String value = t.rowTail().get(col, row);
                
                p.a(value);
                repeat(" ", t.rowTail().maxColLen(col) - value.length(), p);
                p.a("|");
            }
            
            p.nl();
            
            // Print the separator at the bottom of each group
            if (row == rowEnd) {
                rowSeparator(t, g, p);
            }
            
            while (rowEnd <= row && rowGrp < g.groups()) {
                rowEnd = g.lastRow(rowGrp++);
            }
        }
        
        if (rowEnd == -1) {
            rowSeparator(t, g, p);
        }
        
        colTitles(t.colTail(), rowHeadWidth, false, g, p);
    }
    
    private static void rowTitle(StringMatrix t, int row, Groupping g, Printer p) {
        // Header of each row
        for (int col = 0; col < t.cols(); col++) {
            String value = t.get(col, row);

            p.a("|").a(value);
            repeat(" ", t.maxColLen(col) - value.length(), p);
        }
    }
    
    private static void rowSeparator(HeadedTable<Character> t, Groupping g, Printer p) {
        // Print the separator between row header rows
        for (int col = 0; col < t.rowHead().cols(); col++) {
            p.a("+"); 
            repeat("-", t.rowHead().maxColLen(col), p);
        }

        p.a("+");

        // Print the separator between cells
        int colGrp = 0, colEnd = -1;
        for (int col = 0; col < t.cols(); col++) {

            p.a("-");

            if (col == colEnd) {
                p.a("+"); 
            }
            while (colEnd <= col && colGrp < g.groups()) {
                colEnd = g.lastCol(colGrp++);
            }
        }

        if (colEnd == -1) {
            p.a("+");
        }
        
        // Print the separator between row tail rows
        for (int col = 0; col < t.rowTail().cols(); col++) {
            repeat("-", t.rowTail().maxColLen(col), p);
            p.a("+");
        }

        p.nl();
    }
    
    private static void rowTitleSep(
            StringMatrix m, int skip,
            Groupping g, Printer p) {
        
        repeat(" ", skip, p);
        p.a("+");

        int grp = 0; // Group ID of the next group
        int end = -1; // Last column that belongs to the current group
        for (int col = 0; col < m.cols(); col++) {

            p.a("-");

            if (col == end) { // Group divider
                p.a("+");
            }
            while (end <= col && grp < g.groups()) { // Skip to next
                end = g.lastCol(grp++); // group if there is any.
            }
        }
        
        if (end == -1) {
            p.a("+");
        }
        
        p.nl();
    }
    
    private static void colTitles(
            StringMatrix m,
            int skip, boolean above,
            Groupping g, Printer p) {
        for (int row = 0; row < m.rows(); row++) {

            if (above) { // Print the top border of the head
                rowTitleSep(m, skip, g, p);
            }
            
            // Iterate over all characters in the head
            for (int pos = 0; pos < m.maxRowLen(row); pos++) {
                
                int grp = 0, end = -1;
                repeat(" ", skip, p);
                p.a("|");
                for (int col = 0; col < m.cols(); col++) {
                    // Value in the header
                    String s = m.get(col, row);
                    
                    // Padd the string with spaces
                    if (pos < s.length()) {
                        p.a(s.substring(pos, pos + 1));
                    } else {
                        p.a(" ");
                    }
                    
                    if (col == end) {
                        p.a("|");
                    }
                    while (end <= col && grp < g.groups()) {
                        end = g.lastCol(grp++);
                    }
                }
                
                if (end == -1) {
                    p.a("|");
                }
                
                p.nl();
            }
            
            if (!above) { // Print the top border of the head
                rowTitleSep(m, skip, g, p);
            }
        }
    }
}

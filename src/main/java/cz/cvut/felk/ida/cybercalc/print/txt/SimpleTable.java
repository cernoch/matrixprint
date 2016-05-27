/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.txt;

import cz.cvut.felk.ida.cybercalc.print.io.Printer;
import cz.cvut.felk.ida.cybercalc.print.model.SimpleBoolTable;

/**
 * Prints a simple table to a printer.
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class SimpleTable {

    private SimpleTable() {}

    private static void boolTableHeader(SimpleBoolTable t, Printer p, String padd) {

        // Find the longest column header
        int longest = Integer.MIN_VALUE;
        for (int col = 0; col < t.cols(); col++) {
            if (longest < t.colHead(col).length()) {
                longest = t.colHead(col).length();
            }
        }
        
        for (int row = 0; row < longest; row++) {
            p.a(padd);
            for (int col = 0; col < t.cols(); col++) {
            
                String h = t.colHead(col);
                //int hPos = row - longest + h.length(); // align at right
                int hPos = row; 
            
                p.a(hPos >= 0 && hPos < h.length()
                        ? h.substring(hPos, hPos + 1) : " ");
            }
            p.nl();
        }
    }
    
    /**
     * Table of boolean values in a compact format.
     * @param table non-{@code null} table to be printed
     * @param p non-{@code null} sink for the table
     */
    public static void asciiTable(final SimpleBoolTable table, Printer p) {
        
        SimpleCompressor small = new SimpleCompressor(table);
        
        // Find the longest row header
        int longest = Integer.MIN_VALUE;
        for (int row = 0; row < small.rows(); row++) {
            if (longest < small.rowHead(row).length()) {
                longest = small.rowHead(row).length();
            }
        }
        
        // Create padding
        StringBuilder padd = new StringBuilder();
        for (int i = 0; i < longest; i++) {
            padd.append(" ");
        }
        padd.append("|");
        
        // Print the header, ...
        boolTableHeader(small, p, padd.toString());

        // ... separator, ...
        for (int i = 0; i < longest; i++) {
            p.a("-");
        }
        p.a("+");
        for (int col = 0; col < small.cols(); col++) {
            p.a("-");
        }
        p.nl();
        
        //... and the table itself
        for (int row = 0; row < small.rows(); row++) {
            String rowHead = small.rowHead(row);

            p.a(rowHead);
            for (int i = rowHead.length(); i < longest; i++) {
                p.a(" ");
            }
            p.a("|");
            
            for (int col = 0; col < small.cols(); col++) {
                boolean value1 = small.value(col, row);
                boolean value2 = small.valueAux(col, row);
                
                if ( value1 &&  value2) { p.a("█"); continue; }
                if ( value1 && !value2) { p.a("▀"); continue; }
                if (!value1 &&  value2) { p.a("▄"); continue; }
                if (!value1 && !value2) { p.a(":"); continue; }
            }
            p.nl();
        }
    }

    /**
     * Merges 2 rows of a table into a single row.
     */
    private static class SimpleCompressor extends SimpleBoolTable {

        private final SimpleBoolTable table;
        
        SimpleCompressor(SimpleBoolTable table) {
            this.table = table;
        }

        @Override
        public String name() {
            return table.name();
        }
        
        @Override
        public int cols() {
            return table.cols();
        }
        
        @Override
        public int rows() {
            return (table.rows() + 1) / 2;
        }

        @Override
        public String colHead(int col) {
            return table.colHead(col);
        }

        @Override
        public String rowHead(int row) {
            String out = table.rowHead(2 * row);
            if (hasAuxRow(row)) {
                out += " / " + table.rowHead(2 * row + 1);
            }
            return out;
        }

        @Override
        public boolean value(int col, int row) {
            return table.value(col, 2 * row);
        }

        public boolean valueAux(int col, int row) {
            return hasAuxRow(row) ? table.value(col, 2 * row + 1) : false;
        }
        
        public boolean hasAuxRow(int row) {
            return 2 * (row + 1) <= table.rows();
        }
    }
}

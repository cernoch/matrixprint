/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.model;

import cz.cvut.felk.ida.cybercalc.print.model.Compressor;
import cz.cvut.felk.ida.cybercalc.print.model.Groupping;
import cz.cvut.felk.ida.cybercalc.print.model.HeadedTable;
import cz.cvut.felk.ida.cybercalc.print.model.StringMatrix;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 *
 * @author radek
 */
public class CompressorTest {

    private class ArrayRowGroup implements Groupping {
        
        private final int cols;
        private final int[] lastRows;

        public ArrayRowGroup(int cols, int[] lastRows) {
            this.cols = cols;
            this.lastRows = lastRows;
        }

        @Override
        public int groups() {
            return lastRows.length;
        }

        @Override
        public int lastCol(int group) {
            return cols - 1;
        }

        @Override
        public int lastRow(int group) {
            return lastRows[group];
        }
    }
    
    @Test
    public void mappingBetweenOldAndNew() {
        final HeadedTable table = new HeadedTable() {

            @Override
            public int cols() {
                return 9;
            }

            @Override
            public int rows() {
                return 9;
            }

            @Override
            public Boolean get(int col, int row) {
                return col == row;
            }

            @Override public StringMatrix colHead() { return null; }
            @Override public StringMatrix rowHead() { return null; }
            @Override public StringMatrix colTail() { return null; }
            @Override public StringMatrix rowTail() { return null; }
        };
        
        final Groupping group = new ArrayRowGroup(
                table.cols() - 1,
                new int[] {0, 4, 6, 7});
        
        Compressor comp = new Compressor(table, group);
        
        assertEquals(6, comp.rows());
        
        assertArrayEquals(
                comp.new2firstOld(),
                new int[] {0, 1, 3, 5, 7, 8});
        
        assertArrayEquals(comp.old2new(),
                new int[] {0, 1, 1, 2, 2, 3, 3, 4, 5});
        
        assertTrue(comp.getAbove(0, 0));
        assertTrue(comp.getAbove(1, 1));
        assertTrue(comp.getBelow(2, 1));
        assertTrue(comp.getAbove(3, 2));
        assertTrue(comp.getBelow(4, 2));
        assertTrue(comp.getAbove(5, 3));
        assertTrue(comp.getBelow(6, 3));
        assertTrue(comp.getAbove(7, 4));
        assertTrue(comp.getAbove(8, 5));
        
        int suma = 0;
        for (int row = 0; row < comp.rows(); row++) {
        for (int col = 0; col < comp.cols(); col++) {

            if (comp.getAbove(col, row)) {
                suma++;
            }
            
            if (comp.hasBelow(row) && comp.getBelow(col, row)) {
                suma++;
            }
        }}
        assertEquals(9, suma);
    }
    
    @Test
    public void emptyGrouppingProducesEqualBoolTable() {
        final HeadedTable table = new HeadedTable() {

            @Override
            public int cols() {
                return 9;
            }

            @Override
            public int rows() {
                return 9;
            }

            @Override
            public Boolean get(int col, int row) {
                return col == row;
            }

            @Override public StringMatrix colHead() { return null; }
            @Override public StringMatrix rowHead() { return null; }
            @Override public StringMatrix colTail() { return null; }
            @Override public StringMatrix rowTail() { return null; }
        };
        
        final Groupping group = new Groupping() {

            @Override
            public int groups() {
                return 0;
            }

            @Override
            public int lastCol(int group) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int lastRow(int group) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        Compressor comp = new Compressor(table, group);
        
        assertEquals(5, comp.rows());
        
        assertArrayEquals(
                comp.new2firstOld(),
                new int[] {0, 2, 4, 6, 8});
        
        assertArrayEquals(comp.old2new(),
                new int[] {0, 0, 1, 1, 2, 2, 3, 3, 4});
    }
    
    @Test
    public void oddNumberOfRowsIsFine() {
        final HeadedTable table = new HeadedTable() {

            @Override
            public int cols() {
                return 4;
            }

            @Override
            public int rows() {
                return 4;
            }

            @Override
            public Boolean get(int col, int row) {
                return col == row;
            }

            @Override public StringMatrix colHead() { return null; }
            @Override public StringMatrix rowHead() { return null; }
            @Override public StringMatrix colTail() { return null; }
            @Override public StringMatrix rowTail() { return null; }
        };
        
        final Groupping group = new Groupping() {

            @Override
            public int groups() {
                return 0;
            }

            @Override
            public int lastCol(int group) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int lastRow(int group) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        Compressor comp = new Compressor(table, group);
        
        assertEquals(2, comp.rows());
        
        assertArrayEquals(
                comp.new2firstOld(),
                new int[] {0, 2});
        
        assertArrayEquals(comp.old2new(),
                new int[] {0, 0, 1, 1});
    }
}

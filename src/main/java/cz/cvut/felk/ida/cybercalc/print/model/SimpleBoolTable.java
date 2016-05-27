/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.model;

/**
 * Abstract 2D table of booleans with row and column headers.
 */
public abstract class SimpleBoolTable extends HeadCache<Boolean> {

    public abstract String name();

    public abstract String colHead(int col);

    public abstract String rowHead(int row);

    public abstract boolean value(int col, int row);

    public SimpleBoolTable() {
        this.rowTail = StringMatrix.EMPTY;
        this.colTail = StringMatrix.EMPTY;
        this.rowHead = new RowHead();
        this.colHead = new ColHead();
    }

    public class RowHead extends StringMatrix {

        @Override
        public int cols() {
            return 1;
        }

        @Override
        public int rows() {
            return SimpleBoolTable.this.rows();
        }

        @Override
        public String get(int col, int row) {
            
            if (col == 0) {
                return rowHead(row);
            }
            
            return null;
        }
    }
    
    public class ColHead extends StringMatrix {

        @Override
        public int cols() {
            return SimpleBoolTable.this.cols();
        }

        @Override
        public int rows() {
            return 1;
        }

        @Override
        public String get(int col, int row) {
            
            if (row == 0) {
                return rowHead(col);
            }
            
            return null;
        }
    }
    
    @Override
    public Boolean get(int col, int row) {
        return value(col, row);
    }
}

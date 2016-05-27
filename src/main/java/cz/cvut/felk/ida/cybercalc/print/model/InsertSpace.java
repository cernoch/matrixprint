/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */
package cz.cvut.felk.ida.cybercalc.print.model;

/**
 * Creates an aesthetically-pleasing
 * character table with 1:1 row-column ratio.
 * 
 * @author Radek
 */
public class InsertSpace implements HeadedTable<Character> {
    
    private final HeadedTable<Character> delegate;

    public InsertSpace(HeadedTable<Character> delegate) {
        this.delegate = delegate;
        this.colHead = new Separator(delegate.colHead());
        this.colTail = new Separator(delegate.colTail());
    }

    private class Separator extends StringMatrix {

        private final StringMatrix delegate;

        public Separator(StringMatrix delegate) {
            this.delegate = delegate;
        }

        @Override
        public int cols() {
            return delegate.cols() * 2 + 1;
        }

        @Override
        public int rows() {
            return delegate.rows();
        }

        @Override
        public String get(int col, int row) {
            if (col % 2 == 0) {
                return "";
            } else {
                return delegate.get(col / 2, row);
            }
        }
    }
    private final StringMatrix colHead;

    @Override
    public StringMatrix colHead() {
        return colHead;
    }

    @Override
    public StringMatrix rowHead() {
        return delegate.rowHead();
    }
    private final StringMatrix colTail;

    @Override
    public StringMatrix colTail() {
        return colTail;
    }

    @Override
    public StringMatrix rowTail() {
        return delegate.rowTail();
    }

    @Override
    public int cols() {
        return delegate.cols() * 2 + 1;
    }

    @Override
    public int rows() {
        return delegate.rows();
    }

    @Override
    public Character get(int col, int row) {
        if (col % 2 == 0) {
            return ' ';
        } else {
            return delegate.get(col / 2, row);
        }
    }
}

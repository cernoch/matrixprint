/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */
package cz.cvut.felk.ida.cybercalc.print.model;

/**
 *
 * 
 * @author Radek
 */
public class StarTable implements HeadedTable<Character> {
   
    private final Character trueChar;
    
    private final Character falseChar;
    
    private final Character nullChar;
    
    private final HeadedTable<Boolean> delegate;

    public StarTable(HeadedTable<Boolean> delegate) {
        this(delegate, '*', '.');
    }

    public StarTable(HeadedTable<Boolean> delegate,
            Character trueChar, Character falseChar) {
        this(delegate, trueChar, falseChar, ' ');
    }

    public StarTable(HeadedTable<Boolean> delegate,
            Character trueChar, Character falseChar, Character nullChar) {
        this.delegate = delegate;
        this.trueChar = trueChar;
        this.falseChar = falseChar;
        this.nullChar = nullChar;
    }

    @Override
    public StringMatrix colHead() {
        return delegate.colHead();
    }

    @Override
    public StringMatrix rowHead() {
        return delegate.rowHead();
    }

    @Override
    public StringMatrix colTail() {
        return delegate.colTail();
    }

    @Override
    public StringMatrix rowTail() {
        return delegate.rowTail();
    }

    @Override
    public int cols() {
        return delegate.cols();
    }

    @Override
    public int rows() {
        return delegate.rows();
    }

    @Override
    public Character get(int col, int row) {
        Boolean value = delegate.get(col, row);
        if (value == null) {
            return nullChar;
        }
        return value ? trueChar : falseChar;
    }
}

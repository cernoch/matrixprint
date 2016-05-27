/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.model;

/**
 * 
 *
 * @author radek
 */
public abstract class HeadCache<T> implements HeadedTable<T> {
    
    protected StringMatrix colHead;
    protected StringMatrix rowHead;
    protected StringMatrix colTail;
    protected StringMatrix rowTail;

    @Override
    public StringMatrix colHead() {
        return colHead;
    }

    @Override
    public StringMatrix rowHead() {
        return rowHead;
    }

    @Override
    public StringMatrix colTail() {
        return colTail;
    }

    @Override
    public StringMatrix rowTail() {
        return rowTail;
    }

    @Override
    public int cols() {
        assert colHead.cols() == colTail.cols();
        return colHead.cols();
    }

    @Override
    public int rows() {
        assert rowHead.rows() == rowTail.rows();
        return rowHead.rows();
    }
}

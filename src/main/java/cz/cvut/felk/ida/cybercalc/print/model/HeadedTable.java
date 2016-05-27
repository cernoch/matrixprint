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
public interface HeadedTable<T> extends Matrix<T> {

    public StringMatrix colHead();

    public StringMatrix rowHead();

    public StringMatrix colTail();

    public StringMatrix rowTail();
}

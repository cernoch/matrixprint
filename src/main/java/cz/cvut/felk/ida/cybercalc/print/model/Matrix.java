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
public interface Matrix<V> {

    int cols();

    int rows();

    V get(int col, int row);
}

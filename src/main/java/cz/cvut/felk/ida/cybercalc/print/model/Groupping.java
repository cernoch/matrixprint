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
public interface Groupping {

    public int groups();

    public int lastCol(int group);

    public int lastRow(int group);
    
    
    
    public static final Groupping EMPTY = new Groupping() {

        @Override
        public int groups() {
            return 0;
        }

        @Override
        public int lastCol(int group) {
            throw new IllegalArgumentException("Groupping is empty");
        }

        @Override
        public int lastRow(int group) {
            throw new IllegalArgumentException("Groupping is empty");
        }
    };
}

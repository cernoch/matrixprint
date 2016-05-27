/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.io;

/**
 * Capable of appending strings and making a new line.
 * 
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public interface Printer {

    /**
     * Appends a string.
     *
     * @param str non-{@code null} string to be appended
     * 
     * @return {@code this} for code convenience
     */
    public Printer a(String str);

    /**
     * Starts a new line.
     * 
     * @return {@code this} for code convenience
     */
    public Printer nl();
}

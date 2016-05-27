/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.io;

/**
 * View of a {@link StringBuilder} as a {@link Printer}.
 */
public class PrinterFromStringBuilder implements Printer {

    private final StringBuilder sb;
    
    public PrinterFromStringBuilder(StringBuilder sb) {
        this.sb = sb;
    }

    @Override
    public PrinterFromStringBuilder a(String str) {
        sb.append(str);
        return this;
    }

    @Override
    public PrinterFromStringBuilder nl() {
        sb.append("\n");
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

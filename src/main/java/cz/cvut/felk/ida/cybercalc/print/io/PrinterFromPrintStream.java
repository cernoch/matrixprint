/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.io;

import java.io.PrintStream;

/**
 * View of a {@link PrintStream} as a {@link Printer}. 
 *
 * @author Radomír Černoch
 */
public class PrinterFromPrintStream implements Printer {

    private final PrintStream ps;
    
    public PrinterFromPrintStream(PrintStream ps) {
        this.ps = ps;
    }

    @Override
    public PrinterFromPrintStream a(String str) {
        ps.print(str);
        return this;
    }

    @Override
    public PrinterFromPrintStream nl() {
        ps.println();
        return this;
    }

    @Override
    public String toString() {
        return "Printer(" + ps.toString() + ")";
    }
}

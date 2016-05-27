/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.io;

/**
 * Encapsulates each line by a prefix and suffix.
 */
public class PrefixedPrinter implements Printer {

    private final Printer printer;
    private final String prefix;
    private final String suffix;

    public PrefixedPrinter(Printer printer, String prefix, String suffix) {
        this.printer = printer;
        this.prefix = prefix;
        this.suffix = suffix;
    }
    private boolean prefixPrinted = false;

    private void printPrefixIfNeeded() {
        if (!prefixPrinted) {
            printer.a(prefix);
            prefixPrinted = true;
        }
    }

    @Override
    public Printer a(String str) {
        printPrefixIfNeeded();
        printer.a(str);
        return this;
    }

    @Override
    public Printer nl() {
        printPrefixIfNeeded();
        printer.a(suffix);
        printer.nl();
        prefixPrinted = false;
        return this;
    }
}

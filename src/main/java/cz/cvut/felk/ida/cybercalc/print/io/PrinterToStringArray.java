/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.io;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Printer} that creates a {@link List} of {@link String}s.
 */
public class PrinterToStringArray implements Printer {

    private StringBuilder curr;
    private final List<StringBuilder> list = new ArrayList<>();

    public PrinterToStringArray() {
        list.add(curr = new StringBuilder());
    }

    @Override
    public PrinterToStringArray a(String str) {
        curr.append(str);
        return this;
    }

    @Override
    public PrinterToStringArray nl() {
        list.add(curr = new StringBuilder());
        return this;
    }

    public String[] pickUp() {
        int i = 0;
        String[] out = new String[list.size()];
        for (StringBuilder line : list) {
            out[i++] = line.toString();
        }
        return out;
    }
}

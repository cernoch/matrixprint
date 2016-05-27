/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.io;

import java.io.IOException;
import java.io.Writer;

/**
 * View of a {@link Writer} as a {@link Printer}. 
 *
 * @author Radomír Černoch
 */
public class PrinterFromWriter implements Printer, AutoCloseable {

    protected final Writer wr;
    
    protected final String nl;
    
    private IOException thrown = null;

    public PrinterFromWriter(Writer wr, String nl) {
        this.wr = wr;
        this.nl = nl;
    }        
    
    public PrinterFromWriter(Writer wr) {
        this.wr = wr;
        
        String delim;
        try {
            delim = System.getProperty("line.separator");
        } catch (Exception ex) {
            delim = null;
        }
        
        if (delim == null || delim.isEmpty()) {
            delim = "\n";
        }
        
        this.nl = delim;
    }
    
    @Override
    public PrinterFromWriter a(String str) {
        if (thrown == null) {
            try {
                wr.write(str);
            } catch (IOException ex) {
                thrown = ex;
            }
        }
        return this;
    }

    @Override
    public PrinterFromWriter nl() {
        if (thrown == null) {
            try {
                wr.write(nl);
            } catch (IOException ex) {
                thrown = ex;
            }
        }
        return this;
    }

    public void rethrow() throws IOException {
        IOException cause = thrown;
        if (cause != null) {
            thrown = null;
            throw new IOException("See nested exception.", cause);
        }
    }

    @Override
    public String toString() {
        return "Printer(" + wr.toString() + ")";
    }

    @Override
    public void close() throws IOException {
        try {
            wr.close();
            
        } catch (IOException ex) {
            if (thrown == null) {
                thrown = ex;
            } else {
                thrown.addSuppressed(ex);
            }
        }
        
        rethrow();
    }
}

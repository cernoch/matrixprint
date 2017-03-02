/* 
 * The MIT License
 *
 * Copyright 2017 Radomír Černoch (radomir.cernoch at gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.cernoch.matrixprint.io;

import java.io.IOException;
import java.io.Writer;

/**
 * View of a {@link Writer} as a {@link Printer}. 
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
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
    
    public PrinterFromWriter flush() {
        if (thrown == null) {
            try {
                wr.flush();
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

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

import io.github.cernoch.matrixprint.io.PrinterFromWriter;
import java.io.IOException;
import java.io.Writer;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the {@link PrinterFromWriter} class.
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class PrinterFromWriterTest {
    
    @Test()
    public void reportsTheCorrectException() throws IOException {
        
        try {
            try (PrinterFromWriter pfw 
                = new PrinterFromWriter(
                    new FaultyWriter())) {
                
                pfw.a("Ahoj").nl();
                pfw.rethrow();
            }
            
        } catch (IOException ex) {
            assertEquals(ControlledIOException.class,
                        ex.getCause().getClass());
        }
    }

    @Test()
    public void closeIsCalledEvenWhenFailed() throws IOException {
        FaultyWriter writer = new FaultyWriter();
        
        try {
            try (PrinterFromWriter pfw = new PrinterFromWriter(writer)) {
                pfw.a("Ahoj").nl();
                pfw.rethrow();
            }
            
        } catch (IOException ex) {
            // Yes, the exception is expected
        }
        
        Assert.assertTrue(writer.closed);
    }

    private static class ControlledIOException extends IOException {

        public ControlledIOException(String message) {
            super(message);
        }
    }
    
    
    /**
     * Throws {@link IOException} on every operation.
     */
    private static class FaultyWriter extends Writer {

        public boolean written, flushed, closed;
        
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            this.written = true;
            throw new ControlledIOException("write");
        }

        @Override
        public void flush() throws IOException {
            this.flushed = true;
            throw new ControlledIOException("flush");
        }
        
        @Override        
        public void close() throws IOException {
            this.closed = true;
            throw new ControlledIOException("close");
        }
    }
}

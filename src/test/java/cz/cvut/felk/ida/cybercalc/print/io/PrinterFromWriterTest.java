package cz.cvut.felk.ida.cybercalc.print.io;

import java.io.IOException;
import java.io.Writer;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Radek
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

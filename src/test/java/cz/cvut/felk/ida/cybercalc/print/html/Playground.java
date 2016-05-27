/*
 * Copyright (c) 2016 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.html;

import cz.cvut.felk.ida.cybercalc.print.io.PrinterFromWriter;
import cz.cvut.felk.ida.cybercalc.print.model.HeadCache;
import cz.cvut.felk.ida.cybercalc.print.model.StringMatrix;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class Playground {

    class TempTable extends HeadCache<String> {

        public TempTable() {
            colHead = new HeadTail() {
                @Override
                public int rows() {
                    return 2;
                }
            };
            
            colTail = new HeadTail() {
                @Override
                public int rows() {
                    return 1;
                }
            };
            
            rowHead = new HeadTail() {
                @Override
                public int cols() {
                    return 2;
                }
            };
            
            rowTail = new HeadTail() {
                @Override
                public int cols() {
                    return 1;
                }
            };
        }
        
        public class HeadTail extends StringMatrix {

            @Override
            public int cols() {
                return TempTable.this.cols();
            }

            @Override
            public int rows() {
                return TempTable.this.rows();
            }

            @Override
            public String get(int col, int row) {
                return TempTable.this.get(col, row);
            }
        }
        
        @Override
        public int cols() {
            return 8;
        }

        @Override
        public int rows() {
            return 15;
        }

        @Override
        public String get(int col, int row) {
            return String.format("%X.%X", col + 1, row + 1);
        }
    };
    
    @Test
    @Ignore
    public void test() throws IOException {
        try(PrinterFromWriter pfw = new PrinterFromWriter(
                new FileWriter("target/test.html"))) {
            new TablePage(new TempTable(), "Pokus", pfw).call();
        }
    }
}

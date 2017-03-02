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
package io.github.cernoch.matrixprint.html;

import io.github.cernoch.matrixprint.html.TablePage;
import io.github.cernoch.matrixprint.io.PrinterFromWriter;
import io.github.cernoch.matrixprint.model.HeadCache;
import io.github.cernoch.matrixprint.model.StringMatrix;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Playground for testing various ideas.
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

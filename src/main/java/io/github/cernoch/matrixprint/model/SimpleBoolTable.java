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
package io.github.cernoch.matrixprint.model;

/**
 * Abstract 2D table of booleans with row and column headers.
 * 
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public abstract class SimpleBoolTable extends HeadCache<Boolean> {

    public abstract String name();

    public abstract String colHead(int col);

    public abstract String rowHead(int row);

    public abstract boolean value(int col, int row);

    public SimpleBoolTable() {
        this.rowTail = StringMatrix.EMPTY;
        this.colTail = StringMatrix.EMPTY;
        this.rowHead = new RowHead();
        this.colHead = new ColHead();
    }

    public class RowHead extends StringMatrix {

        @Override
        public int cols() {
            return 1;
        }

        @Override
        public int rows() {
            return SimpleBoolTable.this.rows();
        }

        @Override
        public String get(int col, int row) {
            
            if (col == 0) {
                return rowHead(row);
            }
            
            return null;
        }
    }
    
    public class ColHead extends StringMatrix {

        @Override
        public int cols() {
            return SimpleBoolTable.this.cols();
        }

        @Override
        public int rows() {
            return 1;
        }

        @Override
        public String get(int col, int row) {
            
            if (row == 0) {
                return rowHead(col);
            }
            
            return null;
        }
    }
    
    @Override
    public Boolean get(int col, int row) {
        return value(col, row);
    }
}

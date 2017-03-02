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
 * Creates an aesthetically-pleasing
 * character table with 1:1 row-column ratio.
 * 
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class InsertSpace implements HeadedTable<Character> {
    
    private final HeadedTable<Character> delegate;

    public InsertSpace(HeadedTable<Character> delegate) {
        this.delegate = delegate;
        this.colHead = new Separator(delegate.colHead());
        this.colTail = new Separator(delegate.colTail());
    }

    private class Separator extends StringMatrix {

        private final StringMatrix delegate;

        public Separator(StringMatrix delegate) {
            this.delegate = delegate;
        }

        @Override
        public int cols() {
            return delegate.cols() * 2 + 1;
        }

        @Override
        public int rows() {
            return delegate.rows();
        }

        @Override
        public String get(int col, int row) {
            if (col % 2 == 0) {
                return "";
            } else {
                return delegate.get(col / 2, row);
            }
        }
    }
    private final StringMatrix colHead;

    @Override
    public StringMatrix colHead() {
        return colHead;
    }

    @Override
    public StringMatrix rowHead() {
        return delegate.rowHead();
    }
    private final StringMatrix colTail;

    @Override
    public StringMatrix colTail() {
        return colTail;
    }

    @Override
    public StringMatrix rowTail() {
        return delegate.rowTail();
    }

    @Override
    public int cols() {
        return delegate.cols() * 2 + 1;
    }

    @Override
    public int rows() {
        return delegate.rows();
    }

    @Override
    public Character get(int col, int row) {
        if (col % 2 == 0) {
            return ' ';
        } else {
            return delegate.get(col / 2, row);
        }
    }
}

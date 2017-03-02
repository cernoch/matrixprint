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
 * Headed table which caches the row/column header/tailer.
 *
 * <p>Every sub-class must set the {@link #colHead}, {@link #rowHead},
 * {@link #colTail}, {@link #rowTail} fields in the constructor.</p>
 * 
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public abstract class HeadCache<T> implements HeadedTable<T> {
    
    protected StringMatrix colHead;
    protected StringMatrix rowHead;
    protected StringMatrix colTail;
    protected StringMatrix rowTail;

    @Override
    public StringMatrix colHead() {
        return colHead;
    }

    @Override
    public StringMatrix rowHead() {
        return rowHead;
    }

    @Override
    public StringMatrix colTail() {
        return colTail;
    }

    @Override
    public StringMatrix rowTail() {
        return rowTail;
    }

    @Override
    public int cols() {
        assert colHead.cols() == colTail.cols();
        return colHead.cols();
    }

    @Override
    public int rows() {
        assert rowHead.rows() == rowTail.rows();
        return rowHead.rows();
    }
}

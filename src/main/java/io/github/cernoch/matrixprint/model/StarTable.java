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
 * Converts a table of `boolean`s into a table of `char`s.
 * 
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class StarTable implements HeadedTable<Character> {
   
    private final Character trueChar;
    
    private final Character falseChar;
    
    private final Character nullChar;
    
    private final HeadedTable<Boolean> delegate;

    /**
     * Use "*" for {@code true}, "." for {@code false} and " " for {@code null}.
     * @param delegate underlying table containing booleans
     */
    public StarTable(HeadedTable<Boolean> delegate) {
        this(delegate, '*', '.');
    }

    /**
     * Use " " for {@code null} and supplied characters for other values.
     * 
     * @param delegate underlying table containing booleans
     * @param trueChar character to be printed for {@code true} values
     * @param falseChar character to be printed for {@code false} values 
     */
    public StarTable(HeadedTable<Boolean> delegate,
            Character trueChar, Character falseChar) {
        this(delegate, trueChar, falseChar, ' ');
    }

    /**
     * Default constructor
     * 
     * @param delegate underlying table containing booleans
     * @param trueChar character to be printed for {@code true} values
     * @param falseChar character to be printed for {@code false} values
     * @param nullChar character to be printed for {@code null} values
     */
    public StarTable(HeadedTable<Boolean> delegate,
            Character trueChar, Character falseChar, Character nullChar) {
        this.delegate = delegate;
        this.trueChar = trueChar;
        this.falseChar = falseChar;
        this.nullChar = nullChar;
    }

    @Override
    public StringMatrix colHead() {
        return delegate.colHead();
    }

    @Override
    public StringMatrix rowHead() {
        return delegate.rowHead();
    }

    @Override
    public StringMatrix colTail() {
        return delegate.colTail();
    }

    @Override
    public StringMatrix rowTail() {
        return delegate.rowTail();
    }

    @Override
    public int cols() {
        return delegate.cols();
    }

    @Override
    public int rows() {
        return delegate.rows();
    }

    @Override
    public Character get(int col, int row) {
        Boolean value = delegate.get(col, row);
        if (value == null) {
            return nullChar;
        }
        return value ? trueChar : falseChar;
    }
}

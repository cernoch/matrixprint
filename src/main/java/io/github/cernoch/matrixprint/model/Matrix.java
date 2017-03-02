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
 * Matrix is a 2D structure of values of given type.
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 * @param <V> type stored in the cells
 */
public interface Matrix<V> {

    /**
     * Number of columns in the matrix.
     * 
     * @return non-negative number
     */
    int cols();

    /**
     * Number of rows in the matrix.
     * 
     * @return non-negative number
     */
    int rows();

    /**
     * Cell-value in the matrix.
     * 
     * <p>Many implementation require cell values to be non-{@code null}.</p>
     * 
     * @param col column index, between 0 (incl.) and {@link #cols()} (excl.).
     * @param row row index, between 0 (incl.) and {@link #cols()} (excl.).
     * @return value in the cell
     */
    V get(int col, int row);
}

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

import java.util.Arrays;

/**
 * Matrix of strings computes the max column and row sizes.
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public abstract class StringMatrix implements Matrix<String> {

    private int[] maxColLen = null;
    private int[] maxRowLen = null;

    /**
     * Length of the longest string in a column.
     * 
     * @param col value between 0 (incl.) and {@link #cols()} (excl.)
     * @return non-negative integer
     */
    public int maxColLen(int col) {
        if (maxColLen == null) {
            maxColLen = new int[cols()];
            Arrays.fill(maxColLen, -1);
        }
        if (maxColLen[col] < 0) {
            for (int row = 0; row < rows(); row++) {
                assert get(col, row) != null;
                if (maxColLen[col] < get(col, row).length()) {
                    maxColLen[col] = get(col, row).length();
                }
            }
        }
        return maxColLen[col];
    }

    /**
     * Length of the longest string in a row.
     * 
     * @param row value between 0 (incl.) and {@link #rows()} (excl.)
     * @return non-negative integer
     */
    public int maxRowLen(int row) {
        if (maxRowLen == null) {
            maxRowLen = new int[rows()];
            Arrays.fill(maxRowLen, -1);
        }
        if (maxRowLen[row] < 0) {
            for (int col = 0; col < cols(); col++) {
                assert get(col, row) != null;
                if (maxRowLen[row] < get(col, row).length()) {
                    maxRowLen[row] = get(col, row).length();
                }
            }
        }
        return maxRowLen[row];
    }
    
    /**
     * An empty matrix with 0 columns and 0 rows.
     */
    public static final StringMatrix EMPTY = new Empty();

    private static class Empty extends StringMatrix {

        @Override
        public int cols() {
            return 0;
        }

        @Override
        public int rows() {
            return 0;
        }

        @Override
        public String get(int col, int row) {
            return null;
        }
    }
}

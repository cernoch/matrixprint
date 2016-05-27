/*
 * Copyright (c) 2014 Czech Technical University in Prague 
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 */

package cz.cvut.felk.ida.cybercalc.print.model;

import java.util.Arrays;

/**
 * 
 *
 * @author radek
 */
public abstract class StringMatrix implements Matrix<String> {

    private int[] maxColLen = null;
    private int[] maxRowLen = null;

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

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
 * Compresses 2 rows of `boolean` table into a single row of `char`s.
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class Compressor extends HeadCache<Character> implements Groupping {

    /**
     * The original table.
     */
    private final HeadedTable<Boolean> table;
    
    /**
     * Groups old rows.
     */
    private final Groupping group;
    
    /**
     * Maps new rows to the first corresponding old row.
     */
    private final int[] new2old;

    /**
     * Maps old row to new rows.
     */
    private final int[] old2neu;

    /**
     * Number of new rows.
     */
    private final int rows;
    
    /**
     * Compress 2 rows and split rows/cells by the groupping.
     * @param table non-{@code null} table to be compressed
     * @param groupping non-{@code null} row/col groups
     * (see {@link Groupping#EMPTY} to replace {@code null})
     */
    public Compressor(HeadedTable<Boolean> table, Groupping groupping) {
        this.table = table;
        this.group = groupping;
        
        this.rows = countRows();
        this.new2old = new2firstOld();
        this.old2neu = old2new();
        
        this.colHead = table.colHead();
        this.colTail = table.colTail();
        this.rowHead = new CompressedRowLabels(table.rowHead());
        this.rowTail = new CompressedRowLabels(table.rowTail());
    }

    private class CompressedRowLabels extends StringMatrix {

        private final StringMatrix original;

        public CompressedRowLabels(StringMatrix original) {
            this.original = original;
        }
        
        @Override
        public int cols() {
            if (original.cols() == 1) {
                return 2;
            }
            
            return original.cols();
        }

        @Override
        public int rows() {
            return rows;
        }

        @Override
        public String get(int col, int row) {
            
            if (original.cols() == 1) {
                if (col == 0) {
                    return original.get(0, new2old[row]);
                }
                
                if (col == 1) {
                    if (hasBelow(row)) {
                        return original.get(0, new2old[row] + 1);
                    } else {
                        return "";
                    }
                }
                return null;
            }
            
            if (hasBelow(row)) {
                return original.get(col, new2old[row]) + " / " +
                       original.get(col, new2old[row] + 1);
            } else {
                return original.get(col, new2old[row]);
            }
        }
        
    }

    /**
     * Count all rows of the new table.
     * 
     * @see #rows
     */
    private int countRows() {
        int out = 0;

        int last = -1; // ID of the last row
        for (int grp = 0; grp < group.groups(); grp++) {
            int curr = group.lastRow(grp);
            assert curr < table.rows();
            
            out += (curr - last + 1) / 2;
            last = curr;
        }
        
        if (last < table.rows() - 1) {
            out += (table.rows() - last) / 2;
        }
        
        return out;
    }
    
    /**
     * Maps new rows to the first old row.
     * 
     * @return non-{@code null} array with {@link #rows()} items
     * 
     * @see #new2old
     */
    public final int[] new2firstOld() {
        int[] out = new int[rows];
        
        int grp = 0; // Group ID of the next group
        // Last row of the old table that still belongs to this group
        int end = group.groups() == 0 ? -1 : group.lastRow(grp++);
        
        int old = 0; // Next ID of the old table
        for (int neu = 0; neu < out.length; neu++) {
            
            out[neu] = old;
            
            old += old == end ? 1 : 2;
            while (end < old && grp < group.groups()) {
                end = group.lastRow(grp++);
            }
        }
        
        return out;
    }

    /**
     * Maps old rows to their new row.
     * 
     * @return non-{@code null} array with {@link Matrix#rows()} items
     * 
     * @see #old2neu
     */
    public final int[] old2new() {
        int[] out = new int[table.rows()];
        
        int grp = 0; // Group ID of the next group
        // Last row of the old table that still belongs to this group
        int end = group.groups() == 0 ? -1 : group.lastRow(grp++);
        
        int old = 0; // Next ID of the old table
        for (int neu = 0; neu < rows; neu++) {
            
            out[old] = neu;
            if (old != end && old + 1 < out.length) {
                out[old + 1] = neu;
            }
            
            old += old == end ? 1 : 2;
            while (end < old && grp < group.groups()) {
                end = group.lastRow(grp++);
            }
        }
        
        return out;
    }

    @Override
    public int cols() {
        return table.cols();
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public Character get(int col, int row) {
        
        Boolean value1 = getAbove(col, row);
        Boolean value2 = getBelow(col, row);
        
        value1 = value1 == null ? false : value1;
        value2 = value2 == null ? false : value2;
        
        if (value1 || value2) {
            
            if (value1 && value2) {
                return '█';
            }

            if (value1) {
                return '▀';
            } else {
                return '▄';
            }
            
        } else {
            return ':';
        }
    }

    /**
     * Does the row encode 2 original rows or just 1.
     * 
     * @param row index of the row (up to {@link #rows()}{@code - 1})
     * @return {@code true} iff the row packs 2 old rows together
     */
    public boolean hasBelow(int row) {
        if (row == (rows - 1)) { // If asked for the last row...
            // ...check if the pointer points the but-last row in old table...
            return new2old[row] == table.rows() - 2;
        } else { // ...or check that if the map spans two rows.
            return new2old[row] + 2 == new2old[row + 1];
        }
    }

    /**
     * Get the top value of the packed cell.
     * 
     * @param col index of the column (up to {@link #cols()}{@code - 1})
     * @param row index of the row (up to {@link #rows()}{@code - 1})
     * 
     * @return the original value in the cell
     */
    public Boolean getAbove(int col, int row) {
        return table.get(col, new2old[row]);
    }

    /**
     * Get the bottom value of the packed cell.
     * 
     * @param col index of the column (up to {@link #cols()}{@code - 1})
     * @param row index of the row (up to {@link #rows()}{@code - 1})
     * 
     * @return {@code null} if {@link #hasBelow(int)}{@code == false},
     * otherwise the original value is returned.
     */
    public Boolean getBelow(int col, int row) {
        if (!hasBelow(row)) {
            return null;
        }
        return table.get(col, new2old[row] + 1);
    }

    @Override
    public int groups() {
        return group.groups();
    }

    @Override
    public int lastCol(int grp) {
        return group.lastCol(grp);
    }

    @Override
    public int lastRow(int grp) {
        return old2neu[group.lastRow(grp)];
    }
}

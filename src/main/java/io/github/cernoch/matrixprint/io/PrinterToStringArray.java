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
package io.github.cernoch.matrixprint.io;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Printer} that creates a {@link List} of {@link String}s.
 * 
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class PrinterToStringArray implements Printer {

    private StringBuilder curr;
    private final List<StringBuilder> list = new ArrayList<>();

    public PrinterToStringArray() {
        list.add(curr = new StringBuilder());
    }

    @Override
    public PrinterToStringArray a(String str) {
        curr.append(str);
        return this;
    }

    @Override
    public PrinterToStringArray nl() {
        list.add(curr = new StringBuilder());
        return this;
    }

    public String[] pickUp() {
        int i = 0;
        String[] out = new String[list.size()];
        for (StringBuilder line : list) {
            out[i++] = line.toString();
        }
        return out;
    }
}

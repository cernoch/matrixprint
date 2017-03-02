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

/**
 * Encapsulates each line by a prefix and suffix.
 * 
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class PrefixedPrinter implements Printer {

    private final Printer printer;
    private final String prefix;
    private final String suffix;

    public PrefixedPrinter(Printer printer, String prefix, String suffix) {
        this.printer = printer;
        this.prefix = prefix;
        this.suffix = suffix;
    }
    private boolean prefixPrinted = false;

    private void printPrefixIfNeeded() {
        if (!prefixPrinted) {
            printer.a(prefix);
            prefixPrinted = true;
        }
    }

    @Override
    public Printer a(String str) {
        printPrefixIfNeeded();
        printer.a(str);
        return this;
    }

    @Override
    public Printer nl() {
        printPrefixIfNeeded();
        printer.a(suffix);
        printer.nl();
        prefixPrinted = false;
        return this;
    }
}

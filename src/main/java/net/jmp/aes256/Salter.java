package net.jmp.aes256;

/*
 * (#)Salter.java   0.3.0   07/07/2024
 *
 * @author    Jonathan Parker
 * @version   0.3.0
 * @since     0.3.0
 *
 * MIT License
 *
 * Copyright (c) 2024 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.Objects;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * A class used to convert a string
 * into a Base64 encoded salt value.
 */
final class Salter {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The string to produce salt from. */
    private final String string;

    /**
     * The default constructor.
     */
    private Salter() {
        throw new UnsupportedOperationException("The default constructor is not supported");
    }

    /**
     * A constructor that takes the options.
     *
     * @param   string  java.lang.String
     */
    Salter(final String string) {
        super();

        this.string = Objects.requireNonNull(string);
    }

    String getSalt() {
        this.logger.entry();

        String result = null;

        this.logger.exit(result);

        return result;
    }
}

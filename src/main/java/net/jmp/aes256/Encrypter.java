package net.jmp.aes256;

/*
 * (#)Encrypter.java    0.2.0   07/05/2024
 *
 * @author   Jonathan Parker
 * @version  0.2.0
 * @since    0.2.0
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

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The encrypter class.
 */
final class Encrypter {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The options. */
    private final Options options;

    /**
     * The default constructor.
     */
    private Encrypter() {
        throw new UnsupportedOperationException("The default constructor is not supported");
    }

    /**
     * A constructor that takes the options.
     *
     * @param   options net.jmp.aes256.Options
     */
    Encrypter(final Options options) {
        super();

        this.options = options;
    }

    /**
     * The encrypt method.
     */
    void encrypt() {
        this.logger.entry();

        this.logger.info("Begin encryption");
        this.logger.info("Ending encryption");

        this.logger.exit();
    }
}

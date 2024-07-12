package net.jmp.aes256.crypto;

/*
 * (#)Decrypter.java    0.4.0   07/12/2024
 * (#)Decrypter.java    0.3.0   07/06/2024
 * (#)Decrypter.java    0.2.0   07/05/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
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

import java.io.File;

import java.util.Objects;

import net.jmp.aes256.config.Config;

import net.jmp.aes256.input.Options;

import net.jmp.aes256.utils.Salter;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The decrypter class.
 */
public final class Decrypter {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The configuration. @since 0.4.0 */
    private final Config config;

    /** The options. */
    private final Options options;

    /**
     * The default constructor.
     */
    private Decrypter() {
        throw new UnsupportedOperationException("The default constructor is not supported");
    }

    /**
     * A constructor that takes the configuration and options.
     *
     * @param   config  net.jmp.aes256.config.Config
     * @param   options net.jmp.aes256.input.Options
     */
    public Decrypter(final Config config, final Options options) {
        super();

        this.config = Objects.requireNonNull(config);
        this.options = Objects.requireNonNull(options);
    }

    /**
     * The decrypt method.
     */
    public void decrypt() {
        this.logger.entry();

        if (this.options.getString() != null) {
            this.decryptString();
        }

        if (this.options.getInputFile() != null && this.options.getOutputFile() != null) {
            this.decryptFile();
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("End decryption");
        }

        this.logger.exit();
    }

    /**
     * Decrypt a string.
     *
     * @since   0.3.0
     */
    private void decryptString() {
        this.logger.entry();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Begin decrypting string: '{}'", this.options.getString());
        }

        final Salter salter = new Salter(this.config);
        final String salt = salter.getSalt(this.options.getUserId());

        this.logger.exit();
    }

    /**
     * Decrypt a file.
     *
     * @since   0.3.0
     */
    private void decryptFile() {
        this.logger.entry();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Begin decrypting file: '{}'", this.options.getInputFile());
        }

        if (this.doesInputFileExist()) {
            final Salter salter = new Salter(this.config);
            final String salt = salter.getSalt(this.options.getUserId());
        } else {
            System.out.format("Input file '%s' does not exist%n", this.options.getInputFile());
        }
        
        this.logger.exit();
    }

    /**
     * Return true if the specified input file exists.
     *
     * @return  boolean
     * @since   0.3.0
     */
    private boolean doesInputFileExist() {
        this.logger.entry();

        final File file = new File(this.options.getInputFile());
        final boolean exists = file.exists();

        this.logger.exit(exists);

        return exists;
    }
}

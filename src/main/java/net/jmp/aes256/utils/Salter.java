package net.jmp.aes256.utils;

/*
 * (#)Salter.java   0.4.0   07/12/2024
 * (#)Salter.java   0.3.0   07/07/2024
 *
 * @author    Jonathan Parker
 * @version   0.4.0
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

import java.io.UnsupportedEncodingException;

import java.util.Objects;

import net.jmp.aes256.config.CharacterSets;
import net.jmp.aes256.config.Config;

import org.apache.commons.codec.binary.Base64;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * A class used to convert a string
 * into a Base64 encoded salt value.
 */
public final class Salter {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The configuration. @since 0.4.0 */
    private final Config config;

    /**
     * The default constructor.
     */
    private Salter() {
        throw new UnsupportedOperationException("The default constructor is not supported");
    }

    /**
     * A constructor that takes the configuration.
     *
     * @param   config  net.jmp.aes256.config.Config
     */
    public Salter(final Config config) {
        super();

        this.config = Objects.requireNonNull(config);

        final var characterSets = CharacterSets.getInstance();

        if (!characterSets.getCharacterSets().contains(this.config.getSalter().getCharacterSet())) {
            throw new SalterException("Character set '" + this.config.getSalter().getCharacterSet() + "' is not supported");
        }
    }

    /**
     * Return the salt from the specified string.
     *
     * @param   string  java.lang.String
     * @return          java.lang.String
     */
    public String getSalt(final String string) {
        this.logger.entry(string);

        final String characterSet = this.config.getSalter().getCharacterSet();
        final int iterations = this.config.getSalter().getIterations();

        String unencodedString = string;
        String encodedString = null;

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Unencoded string    : {}", string);
            this.logger.debug("Using character set : {}", characterSet);
            this.logger.debug("Number of iterations: {}", iterations);
        }

        try {
            for (int i = 0; i < iterations; i++) {
                encodedString = Base64.encodeBase64String(unencodedString.getBytes(characterSet));
                unencodedString = encodedString;
            }
        } catch (final UnsupportedEncodingException use) {
            this.logger.catching(use);
        }

        this.logger.debug("Encoded string: {}", encodedString);

        this.logger.exit(encodedString);

        return encodedString;
    }
}

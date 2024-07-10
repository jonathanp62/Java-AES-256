package net.jmp.aes256.utils;

/*
 * (#)Password.java 0.4.0   07/10/2024
 *
 * @author    Jonathan Parker
 * @version   0.4.0
 * @since     0.4.0
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

public final class Password {
    /** The logger. */
    private static final XLogger logger = new XLogger(LoggerFactory.getLogger(Password.class.getName()));

    /**
     * The default constructor.
     */
    private Password() {
        super();
    }

    /**
     * Validate the password.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    public void validate(final String password) throws PasswordException {
        logger.entry(password);

        Objects.requireNonNull(password);

        checkUpperCase(password);
        checkLowerCase(password);
        checkNumbers(password);
        checkSpecialCharacters(password);
        checkLength(password);

        logger.exit();
    }

    /**
     * Validate the password for uppercase letters.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private void checkUpperCase(final String password) throws PasswordException {
        logger.entry(password);
        logger.exit();
    }

    /**
     * Validate the password for lowercase letters.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private void checkLowerCase(final String password) throws PasswordException {
        logger.entry(password);
        logger.exit();
    }

    /**
     * Validate the password for numbers.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private void checkNumbers(final String password) throws PasswordException {
        logger.entry(password);
        logger.exit();
    }

    /**
     * Validate the password for special characters.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private void checkSpecialCharacters(final String password) throws PasswordException {
        logger.entry(password);
        logger.exit();
    }

    /**
     * Validate the length of the password.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private void checkLength(final String password) throws PasswordException {
        logger.entry(password);
        logger.exit();
    }
}

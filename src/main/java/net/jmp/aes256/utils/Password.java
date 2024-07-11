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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param   password        java.lang.String
     * @param   minimumLength   int
     * @throws                  net.jmp.aes256.utils.PasswordException
     */
    public static void validate(final String password, final int minimumLength) throws PasswordException {
        logger.entry(password, minimumLength);

        Objects.requireNonNull(password);

        checkUpperCase(password);
        checkLowerCase(password);
        checkNumbers(password);
        checkSpecialCharacters(password);
        checkLength(password, minimumLength);

        logger.exit();
    }

    /**
     * Validate the password for uppercase letters.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private static void checkUpperCase(final String password) throws PasswordException {
        logger.entry(password);

        final String regex = "(?=(.*[A-Z]+))";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            throw new PasswordException("At least one uppercase letter must be specified: " + password);
        }

        logger.exit();
    }

    /**
     * Validate the password for lowercase letters.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private static void checkLowerCase(final String password) throws PasswordException {
        logger.entry(password);

        final String regex = "(?=(.*[a-z]+))";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            throw new PasswordException("At least one lowercase letter must be specified: " + password);
        }

        logger.exit();
    }

    /**
     * Validate the password for numbers.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private static void checkNumbers(final String password) throws PasswordException {
        logger.entry(password);

        final String regex = "(?=(.*[0-9]+))";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            throw new PasswordException("At least one number must be specified: " + password);
        }

        logger.exit();
    }

    /**
     * Validate the password for special characters.
     *
     * @param   password    java.lang.String
     * @throws              net.jmp.aes256.utils.PasswordException
     */
    private static void checkSpecialCharacters(final String password) throws PasswordException {
        logger.entry(password);

        final String regex = "(?=(.*[!@#$%^&*()\\-_+.,<>{}=\\[\\]\\\\|~`\\/'\"\\?]+))"; // @todo Missing ;:
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            throw new PasswordException("At least one special character must be specified: " + password);
        }

        logger.exit();
    }

    /**
     * Validate the length of the password.
     *
     * @param   password        java.lang.String
     * @param   minimumLength   int
     * @throws                  net.jmp.aes256.utils.PasswordException
     */
    private static void checkLength(final String password, final int minimumLength) throws PasswordException {
        logger.entry(password, minimumLength);

        if (password.length() < minimumLength) {
            throw new PasswordException("The password must be equal to or greater than " + minimumLength + " characters");
        }

        logger.exit();
    }
}

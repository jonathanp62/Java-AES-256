package net.jmp.aes256.input;

/*
 * (#)Prompter.java 0.5.0   07/19/2024
 *
 * @author   Jonathan Parker
 * @version  0.5.0
 * @since    0.5.0
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

import java.io.Console;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import net.jmp.aes256.config.Config;

import net.jmp.aes256.utils.Password;
import net.jmp.aes256.utils.PasswordException;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The prompter class that prompts the user for credentials.
 */
public final class Prompter {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The configuration. */
    private final Config config;

    /** The console object. */
    private final Console console;

    /**
     * A constructor that takes the application configuration.
     *
     * @param   config  net.jmp.aes256.config.Config
     */
    public Prompter(final Config config) {
        super();

        this.config = Objects.requireNonNull(config);
        this.console = Objects.requireNonNull(System.console(), () -> "There is no console available");
    }

    /**
     * Prompt for the user identifier and return it.
     *
     * @return  java.lang.String
     */
    public String promptForUserId(final String userId) {
        this.logger.entry(userId);

        String result;

        if (userId == null) {
            System.out.println("Please enter your user ID:");

            result = console.readLine();
        } else {
            result = userId;
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Prompt for the password. An empty
     * optional is returned if the two
     * encryption passwords entered did
     * not match or the password was not
     * valid. For the decryption password
     * the optional is never empty.
     *
     * @return  java.util.Optional&lt;java.lang.String&;gt
     */
    public Optional<String> promptForPassword(final CommandOperation commandOperation) {
        this.logger.entry(commandOperation);

        Objects.requireNonNull(commandOperation);

        Optional<String> result;

        if (commandOperation == CommandOperation.ENCRYPT) {
            result = this.promptForEncryptPassword();
        } else if (commandOperation == CommandOperation.DECRYPT) {
            result = Optional.of(this.promptForDecryptPassword());
        } else {
            result = Optional.empty();
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Prompt for the encryption password. An
     * empty optional is returned if the two
     * passwords entered did not match or the
     * password is not valid.
     *
     * @return  java.util.Optional&lt;java.lang.String&;gt
     */
    private Optional<String> promptForEncryptPassword() {
        this.logger.entry();

        Optional<String> result;

        System.out.print("Please enter your password:");

        final char[] password1 = this.console.readPassword();

        if (this.isPasswordValid(password1)) {
            System.out.print("Please re-enter your password:");

            final char[] password2 = this.console.readPassword();

            if (Arrays.equals(password1, password2)) {
                result = Optional.of(new String(password1));
            } else {
                System.err.println("The two (2) entered passwords do not match");

                result = Optional.empty();
            }
        } else {
            result = Optional.empty();
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Prompt for the decryption password and return it.
     *
     * @return  java.util.Optional&lt;java.lang.String&;gt
     */
    private String promptForDecryptPassword() {
        this.logger.entry();

        System.out.print("Please enter your password:");

        final char[] password = console.readPassword();

        final String result = new String(password);

        this.logger.exit(result);

        return result;
    }

    /**
     * Check to see if the password entered is valid and
     * return true if it is else false.
     *
     * @param   password    char[]
     * @return              boolean
     */
    private boolean isPasswordValid(final char[] password) {
        this.logger.entry(password);

        assert password != null;

        boolean result;

        try {
            Password.validate(new String(password), this.config.getPasswordMinimumLength());

            result = true;
        } catch (final PasswordException pe) {
            System.err.println(pe.getMessage());

            result = false;
        }

        this.logger.exit(result);

        return result;
    }
}

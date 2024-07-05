package net.jmp.aes256;

/*
 * (#)Main.java 0.2.0   06/29/2024
 * (#)Main.java 0.1.0   06/27/2024
 *
 * @author   Jonathan Parker
 * @version  0.2.0
 * @since    0.1.0
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
import java.util.Optional;

import org.apache.commons.cli.CommandLine;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The main application class.
 */
public final class Main {
    /** The default configuration file name. */
    private static final String DEFAULT_APP_CONFIG_FILE = "config/config.json";

    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The command operation. */
    private CommandOperation commandOperation;

    /** The command line. */
    private CommandLine commandLine;

    /**
     * The default constructor.
     */
    private Main() {
        super();
    }

    /**
     * The go method.
     *
     * @param   args    java.lang.String[]
     */
    private void go(final String[] args) {
        this.logger.entry((Object) args);

        assert args != null;

        this.logger.info("{} {}", Name.NAME_STRING, Version.VERSION_STRING);

        this.processCommandLine(args);

        if (this.commandLine != null) {
            this.handleCommandLine();
        }

        /*
         * 1. Turn the Base 64 encoded user ID into salt for the forthcoming encryption (3 iterations) (See the NetBeans Base64Encoder project)
         * 2. Always prompt for the password; this avoids storing it in a script (a unit test is ok)
         * 3. The password provided serves as the secret key
         * 4. The Apache Commons CLI dependency will be needed; commons-cli:commons-cli:1.8.0; the MyWordle projects are my latest to use it
         * 5. https://stackoverflow.com/questions/13329282/test-java-programs-that-read-from-stdin-and-write-to-stdout
         * 6. First feature branch will be features/input; personal branches can align with steps 1-5
         */

        this.logger.exit();
    }

    /**
     * Process all the command line arguments into
     * a single argument followed by options.
     *
     * @param   args    java.lang.String[]
     */
    private void processCommandLine(final String[] args) {
        this.logger.entry((Object) args);

        assert args != null;

        CommandLineHandler commandLineHandler = null;

        if (args.length == 0)
            commandLineHandler = new CommandLineHandler(new String[] {"--help"});
        else
            commandLineHandler = new CommandLineHandler(args);

        commandLineHandler.handle();

        this.commandOperation = commandLineHandler.getCommandOperation();
        this.commandLine = commandLineHandler.getCommandLine().orElse(null);

        this.logger.exit();
    }

    /**
     * Handle the command line.
     */
    private void handleCommandLine() {
        this.logger.entry();

        /* Make sure all required options are provided */

        final var optionsHandler = new OptionsHandler(this.commandLine);

        if (optionsHandler.handle()) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(optionsHandler.toString());
            }

            this.handleCommandLineOptions(optionsHandler);
        }

        this.logger.exit();
    }

    private void handleCommandLineOptions(final OptionsHandler optionsHandler) {
        this.logger.entry(optionsHandler);

        final Options options = Builder.of(Options::new)
                .with(Options::setString, (optionsHandler.containsString()) ? this.commandLine.getOptionValue("s") : null)
                .with(Options::setInputFile, (optionsHandler.containsInputFile()) ? this.commandLine.getOptionValue("i") : null)
                .with(Options::setOutputFile, (optionsHandler.containsOutputFile()) ? this.commandLine.getOptionValue("o") : null)
                .with(Options::setUserId, this.promptForUserId((optionsHandler.containsUserId()) ? this.commandLine.getOptionValue("u") : null).orElse(null))
                .with(Options::setPassword, this.promptForPassword().orElse(null))
                .build();

        if (options.getPassword() != null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(options.toString());
            }

            this.handleOperation(options);
        }

        this.logger.exit();
    }

    /**
     * Prompt for the user identifier. An
     * empty optional is returned if a
     * console is not available.
     *
     * @return  java.util.Optional&lt;java.lang.String&;gt
     */
    private Optional<String> promptForUserId(final String userId) {
        this.logger.entry(userId);

        final Console console = System.console();

        Optional<String> result;

        if (console == null) {
            this.logger.error("There is no console available");

            result = Optional.empty();
        } else {
            if (userId == null) {
                System.out.println("Please enter your user ID:");

                result = Optional.of(console.readLine());
            } else {
                result = Optional.of(userId);
            }
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Prompt for the password. An empty
     * optional is returned if the two
     * passwords entered did not match or
     * a console is not available.
     *
     * @return  java.util.Optional&lt;java.lang.String&;gt
     */
    private Optional<String> promptForPassword() {
        this.logger.entry();

        Optional<String> result;

        final Console console = System.console();

        if (console == null) {
            this.logger.error("There is no console available");

            result = Optional.empty();
        } else {
            System.out.println("Please enter your password:");

            final char[] password1 = console.readPassword();

            System.out.println("Please re-enter your password:");

            final char[] password2 = console.readPassword();

            if (Arrays.equals(password1, password2)) {
                result = Optional.of(new String(password1));
            } else {
                this.logger.error("The two (2) entered passwords do not match");

                result = Optional.empty();
            }
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Handle the operation.
     *
     * @param   options net.jmp.aes256.Options
     */
    private void handleOperation(final Options options) {
        this.logger.entry(options);

        this.logger.debug("Handling argument: {}", this.commandOperation);

        switch (this.commandOperation) {
            case DECRYPT:
                this.decrypt(options);
                break;
            case ENCRYPT:
                this.encrypt(options);
                break;
            case UNRECOGNIZED:
                this.logger.error("Unrecognized argument: {}", this.commandOperation);
                break;
            default:
                this.logger.error("Unexpected argument: {}", this.commandOperation);
        }

        this.logger.exit();
    }

    /**
     * Decrypt.
     */
    private void decrypt(final Options options) {
        this.logger.entry(options);

        final Decrypter decrypter = new Decrypter(options);

        decrypter.decrypt();

        this.logger.exit();
    }

    /**
     * Encrypt.
     */
    private void encrypt(final Options options) {
        this.logger.entry(options);

        final Encrypter encrypter = new Encrypter(options);

        encrypter.encrypt();

        this.logger.exit();
    }

    /**
     * The main method.
     *
     * @param   args    java.lang.String[]
     */
    public static void main(final String[] args) {
        new Main().go(args);
    }
}

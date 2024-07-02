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
         * 1. Prompt the user for a user identifier, e.g. email address if not provided on the command line (--user | -u)
         * 2. Turn the Base 64 encoded UID into salt for the forthcoming encryption (3 iterations) (See the NetBease Base64Encoder project)
         * 3. Always prompt for the password; this avoids storing it in a script (a unit test is ok)
         * 4. The password provided serves as the secret key
         * 5. The Apache Commons CLI dependency will be needed; commons-cli:commons-cli:1.8.0; the MyWordle projects are my latest to use it
         * 6. https://stackoverflow.com/questions/13329282/test-java-programs-that-read-from-stdin-and-write-to-stdout
         * 7. First feature branch will be features/input; personal branches can align with steps 1-5
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
            final var containsString = optionsHandler.containsString();
            final var containsInputFile = optionsHandler.containsInputFile();
            final var containsOutputFile = optionsHandler.containsOutputFile();
            final var containsUserId = optionsHandler.containsUserId();

            if (this.logger.isDebugEnabled()) {
                this.logger.debug("string?     {}", containsString);
                this.logger.debug("inputFile?  {}", containsInputFile);
                this.logger.debug("outputFile? {}", containsOutputFile);
                this.logger.debug("userId?     {}", containsUserId);
            }

            /* Handle the operation */

            this.logger.debug("Handling argument: {}", this.commandOperation);

            switch (this.commandOperation) {
                case DECRYPT:
                    this.decrypt();
                    break;
                case ENCRYPT:
                    this.encrypt();
                    break;
                case UNRECOGNIZED:
                    this.logger.error("Unrecognized argument: {}", this.commandOperation);
                    break;
                default:
                    this.logger.error("Unexpected argument: {}", this.commandOperation);
            }
        }

        this.logger.exit();
    }

    /**
     * Decrypt.
     */
    private void decrypt() {
        this.logger.entry();

        this.logger.info("Operation: Decrypt");

        this.logger.exit();
    }

    /**
     * Encrypt.
     */
    private void encrypt() {
        this.logger.entry();

        this.logger.info("Operation: Encrypt");

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

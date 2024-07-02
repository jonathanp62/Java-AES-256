package net.jmp.aes256;

/*
 * (#)OptionsHandler.java   0.2.0   07/02/2024
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

import org.apache.commons.cli.CommandLine;
import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

import java.util.Objects;

final class OptionsHandler {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The command line. */
    private final CommandLine commandLine;

    /** True if the --string option was provided. */
    private boolean hasString;

    /** True if the --input-file option was provided. */
    private boolean hasInputFile;

    /** True if the --output-file option was provided. */
    private boolean hasOutputFile;

    /** True if the --user-id option was provided. */
    private boolean hasUserId;

    /**
     * A constructor that takes the command line object.
     *
     * @param   commandLine org.apache.commons.cli.CommandLine
     */
    OptionsHandler(final CommandLine commandLine) {
        super();

        this.commandLine = Objects.requireNonNull(commandLine);
    }

    /**
     * Return true when the --string option is provided.
     *
     * @return  boolean
     */
    boolean containsString() {
        return this.hasString;
    }

    /**
     * Return true when the --input-file option is provided.
     *
     * @return  boolean
     */
    boolean containsInputFile() {
        return this.hasInputFile;
    }

    /**
     * Return true when the --output-file option is provided.
     *
     * @return  boolean
     */
    boolean containsOutputFile() {
        return this.hasOutputFile;
    }

    /**
     * Return true when the --user-id option is provided.
     *
     * @return  boolean
     */
    boolean containsUserId() {
        return this.hasUserId;
    }

    /**
     * The handler. Return true if there were
     * no errors examining the options.
     */
    boolean handle() {
        this.logger.entry();

        boolean result = this.validateOptions();

        if (result && (this.hasString || (this.hasInputFile && this.hasOutputFile))) {
            if (this.hasString) {
                this.stringHandled();
            } else {
                this.filesHandled();
            }
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Log the handled string.
     */
    private void stringHandled() {
        this.logger.entry();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Will handle string: {}", this.commandLine.getOptionValue("s"));

            if (this.hasUserId) {
                this.logger.debug("Will handle user ID: {}", this.commandLine.getOptionValue("u"));
            }
        }

        this.logger.exit();
    }

    /**
     * Log the handled files.
     */
    private void filesHandled() {
        this.logger.entry();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Will handle input file: {}", this.commandLine.getOptionValue("i"));
            this.logger.debug("Will handle output file: {}", this.commandLine.getOptionValue("o"));

            if (this.hasUserId) {
                this.logger.debug("Will handle user ID: {}", this.commandLine.getOptionValue("u"));
            }
        }

        this.logger.exit();
    }

    /**
     * Return true if the combination of options is valid.
     *
     * @return  boolean
     */
    private boolean validateOptions() {
        this.logger.entry();

        /*
         * Look for each option checking for mutual exclusivity and
         * that if files are used, both input and output file names
         * are provided.
         */

        boolean result = this.handleString();

        if (result) {
            result = this.handleInputFile();

            if (result) {
                result = this.handleOutputFile();

                if (result) {
                    this.handleUserId();
                }
            }
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Handle the --string option. True
     * is returned if no errors were detected.
     *
     * @return  boolean
     */
    private boolean handleString() {
        this.logger.entry();

        boolean result = true;

        if (this.commandLine.hasOption("s")) {
            this.logger.debug("Found --string option");

            if (this.commandLine.hasOption("i") || this.commandLine.hasOption("o")) {
                this.logger.error("Options --input-file or --output-file are not allowed with --string");

                result = false;
            }

            if (result) {
                this.hasString = true;
            }
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Handle the --input-file option. True
     * is returned if no errors were detected.
     *
     * @return  boolean
     */
    private boolean handleInputFile() {
        this.logger.entry();

        boolean result = true;

        if (this.commandLine.hasOption("i")) {
            this.logger.debug("Found --input-file option");

            if (!this.commandLine.hasOption("o")) {
                this.logger.error("Option --output-file option must be specified with --input-file");

                result = false;
            }

            if (this.commandLine.hasOption("s")) {
                this.logger.debug("Option --string is not allowed with --input-file");

                result = false;
            }

            if (result) {
                this.hasInputFile = true;
            }
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Handle the --output-file option. True
     * is returned if no errors were detected.
     *
     * @return  boolean
     */
    private boolean handleOutputFile() {
        this.logger.entry();

        boolean result = true;

        if (this.commandLine.hasOption("o")) {
            this.logger.debug("Found --output-file option");

            if (!this.commandLine.hasOption("i")) {
                this.logger.error("Option --input-file option must be specified with --output-file");

                result = false;
            }

            if (this.commandLine.hasOption("s")) {
                this.logger.debug("Option --string is not allowed with --output-file");

                result = false;
            }

            if (result) {
                this.hasOutputFile = true;
            }
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Handle the --user-id option.
     */
    private void handleUserId() {
        this.logger.entry();

        if (this.commandLine.hasOption("u")) {
            this.logger.debug("Found --user-id option");

            this.hasUserId = true;
        }

        this.logger.exit();
    }
}

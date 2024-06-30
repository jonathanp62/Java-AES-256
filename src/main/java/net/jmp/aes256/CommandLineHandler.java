package net.jmp.aes256;

/*
 * (#)CommandLineHandler.java   0.2.0   06/30/2024
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

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.cli.*;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The class that handles the command line.
 */
final class CommandLineHandler {
    /**
     * The logger.
     */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /**
     * The full command line arguments.
     */
    private final String[] arguments;

    /**
     * A constructor that takes the array
     * of command line arguments.
     *
     * @param arguments java.lang.String[]
     */
    CommandLineHandler(final String[] arguments) {
        super();

        this.arguments = Objects.requireNonNull(arguments);
    }

    /**
     * Digest the command line arguments and
     * return an optional CommandLine object.
     *
     * @return java.util.Optional&lt;org.apache.commons.cli.CommandLine&gt;
     */
    Optional<CommandLine> digestCommandLineArguments() {
        this.logger.entry();

        if (this.logger.isDebugEnabled()) {
            for (final String argument : this.arguments) {
                this.logger.debug("arg: {}", argument);
            }
        }

        final var commandLine = this.handleCommandLineArgument();

        this.logger.exit(commandLine);

        return commandLine;
    }

    /**
     * Handle the command line arguments and
     * return an optional CommandLine object.
     * The optional is returned empty if the
     * -help argument was specified.
     *
     * @return java.util.Optional&lt;org.apache.commons.cli.CommandLine&gt;
     */
    private Optional<CommandLine> handleCommandLineArgument() {
        this.logger.entry();

        final var options = this.buildOptions();

        Optional<CommandLine> result = Optional.empty();

        try {
            final CommandLineParser parser = new DefaultParser();

            final var commandLine = parser.parse(options, this.arguments);

            if (commandLine.hasOption("help")) {
                final var formatter = new HelpFormatter();

                formatter.printHelp("aes-256.main/net.jmp.aes256.Main", options);
            }
            else
                result = Optional.of(commandLine);
        } catch (final ParseException pe) {
            this.logger.error("Failed to parse the command line arguments");
            this.logger.catching(pe);
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Build and return the options
     * needed for the command line.
     *
     * @return org.apache.commons.cli.Options
     */
    private Options buildOptions() {
        this.logger.entry();

        final Option help = Option.builder("h")
                .desc("Display this help message")
                .longOpt("help")
                .build();
        final Option string = Option.builder("s")
                .desc("Encrypt/Decrypt a string")
                .longOpt("string")
                .build();
        final Option inputFile = Option.builder("i")
                .argName("file-name")
                .hasArg()
                .desc("Encrypt/Decrypt a file")
                .longOpt("input-file")
                .build();
        final Option outputFile = Option.builder("o")
                .argName("file-name")
                .hasArg()
                .desc("Encrypted/Decrypted output file")
                .longOpt("output-file")
                .build();
        final Option userId = Option.builder("u")
                .argName("user-id")
                .hasArg()
                .desc("User identifier")
                .longOpt("user")
                .build();

        final Options options = new Options();

        options.addOption(help);
        options.addOption(string);
        options.addOption(inputFile);
        options.addOption(outputFile);
        options.addOption(userId);

        this.logger.exit(options);

        return options;
    }
}
package net.jmp.aes256;

/*
 * (#)Main.java 0.5.0   07/19/2024
 * (#)Main.java 0.4.0   07/11/2024
 * (#)Main.java 0.3.0   07/06/2024
 * (#)Main.java 0.2.0   06/29/2024
 * (#)Main.java 0.1.0   06/27/2024
 *
 * @author   Jonathan Parker
 * @version  0.5.0
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

import com.google.gson.Gson;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Objects;
import java.util.Optional;

import net.jmp.aes256.config.Config;

import net.jmp.aes256.crypto.CryptographyException;
import net.jmp.aes256.crypto.Decrypter;
import net.jmp.aes256.crypto.Encrypter;

import net.jmp.aes256.input.*;

import net.jmp.aes256.utils.Builder;

import org.apache.commons.cli.CommandLine;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * The main application class.
 */
public final class Main implements Runnable {
    /** The default configuration file name. */
    private static final String DEFAULT_APP_CONFIG_FILE = "config/config.json";

    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The command line arguments. @since 0.5.0 */
    private final String[] arguments;

    /** The command operation. */
    private CommandOperation commandOperation;

    /** The command line. */
    private CommandLine commandLine;

    /**
     * A constructor that takes the
     * command line arguments from
     * the bootstrap class.
     */
    public Main(final String[] args) {
        super();

        this.arguments = Objects.requireNonNull(args);
    }

    /**
     * The run method.
     */
    @Override
    public void run() {
        this.logger.entry();

        if (this.logger.isInfoEnabled() || this.logger.isWarnEnabled() || this.logger.isErrorEnabled()) {
            System.out.format("%s %s%n", Name.NAME_STRING, Version.VERSION_STRING);
        } else {
            this.logger.debug("{} {}", Name.NAME_STRING, Version.VERSION_STRING);
        }

        this.getAppConfig().ifPresentOrElse(appConfig -> {
            this.processCommandLine();

            if (this.commandLine != null) {
                this.handleCommandLine(appConfig);
            }
        }, () -> this.logger.error("No configuration found for {}", Name.NAME_STRING));

        this.logger.exit();
    }

    /**
     * Get the application configuration.
     *
     * @return  java.lang.Optional&lt;net.jmp.aes256.Config&gt;
     * @since   0.3.0
     */
    private Optional<Config> getAppConfig() {
        this.logger.entry();

        final String configFileName = System.getProperty("app.configurationFile", DEFAULT_APP_CONFIG_FILE);

        this.logger.debug("Reading the configuration from: {}", configFileName);

        Config appConfig = null;

        try {
            appConfig = new Gson().fromJson(Files.readString(Paths.get(configFileName)), Config.class);
        } catch (final IOException ioe) {
            this.logger.catching(ioe);
        }

        this.logger.exit(appConfig);

        return Optional.ofNullable(appConfig);
    }

    /**
     * Process all the command line arguments into
     * a single argument followed by options.
     */
    private void processCommandLine() {
        this.logger.entry();

        CommandLineHandler commandLineHandler;

        if (this.arguments.length == 0)
            commandLineHandler = new CommandLineHandler(new String[] {"--help"});
        else
            commandLineHandler = new CommandLineHandler(this.arguments);

        commandLineHandler.handle();

        this.commandOperation = commandLineHandler.getCommandOperation();
        this.commandLine = commandLineHandler.getCommandLine().orElse(null);

        this.logger.exit();
    }

    /**
     * Handle the command line.
     *
     * @param   config  net.jmp.aes256.config.Config
     */
    private void handleCommandLine(final Config config) {
        this.logger.entry(config);

        assert config != null;

        /* Make sure all required options are provided */

        final var optionsHandler = new OptionsHandler(this.commandLine);

        if (optionsHandler.handle()) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(optionsHandler.toString());
            }

            this.handleCommandLineOptions(config, optionsHandler);
        }

        this.logger.exit();
    }

    /**
     * Handle the command line options.
     *
     * @param   config          net.jmp.aes256.config.Config
     * @param   optionsHandler  net.jmp.aes256.OptionsHandler
     */
    private void handleCommandLineOptions(final Config config, final OptionsHandler optionsHandler) {
        this.logger.entry(config, optionsHandler);

        assert config != null;
        assert optionsHandler != null;

        final Prompter prompter = new Prompter(config);

        final Options options = Builder.of(Options::new)
                .with(Options::setString, (optionsHandler.containsString()) ? this.commandLine.getOptionValue("s") : null)
                .with(Options::setInputFile, (optionsHandler.containsInputFile()) ? this.commandLine.getOptionValue("i") : null)
                .with(Options::setOutputFile, (optionsHandler.containsOutputFile()) ? this.commandLine.getOptionValue("o") : null)
                .with(Options::setUserId, prompter.promptForUserId((optionsHandler.containsUserId()) ? this.commandLine.getOptionValue("u") : null))
                .with(Options::setPassword, prompter.promptForPassword(this.commandOperation).orElse(null))
                .build();

        if (options.getPassword() != null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(options.toString());
            }

            this.handleOperation(config, options);
        } else {
            throw new IllegalStateException("No password was supplied");
        }

        this.logger.exit();
    }

    /**
     * Handle the operation.
     *
     * @param   config  net.jmp.aes256.config.Config
     * @param   options net.jmp.aes256.Options
     */
    private void handleOperation(final Config config, final Options options) {
        this.logger.entry(config, options);

        assert config != null;
        assert options != null;

        this.logger.debug("Handling argument: {}", this.commandOperation);

        switch (this.commandOperation) {
            case DECRYPT:
                this.decrypt(config, options);
                break;
            case ENCRYPT:
                this.encrypt(config, options);
                break;
            case UNRECOGNIZED:
                System.out.format("Unrecognized argument: %s%n", this.commandOperation);
                break;
            default:
                this.logger.error("Unexpected argument: {}", this.commandOperation);
        }

        this.logger.exit();
    }

    /**
     * Decrypt.
     *
     * @param   config  net.jmp.aes256.config.Config
     * @param   options net.jmp.aes256.Options
     */
    private void decrypt(final Config config, final Options options) {
        this.logger.entry(config, options);

        assert config != null;
        assert options != null;

        final Decrypter decrypter = new Decrypter(config, options);

        Optional<String> result;

        try {
            result = decrypter.decrypt();
        } catch (final CryptographyException ce) {
            result = Optional.empty();

            this.logger.catching(ce);
        }

        result.ifPresent(decrypted -> System.out.format("Decrypted: %s%n", decrypted));

        this.logger.exit();
    }

    /**
     * Encrypt.
     *
     * @param   config  net.jmp.aes256.config.Config
     * @param   options net.jmp.aes256.Options
     */
    private void encrypt(final Config config, final Options options) {
        this.logger.entry(config, options);

        assert config != null;
        assert options != null;

        final Encrypter encrypter = new Encrypter(config, options);

        Optional<String> result;

        try {
            result = encrypter.encrypt();
        } catch (final CryptographyException ce) {
            result = Optional.empty();

            this.logger.catching(ce);
        }

        result.ifPresent(encrypted -> System.out.format("Encrypted: %s%n", encrypted));

        this.logger.exit();
    }
}

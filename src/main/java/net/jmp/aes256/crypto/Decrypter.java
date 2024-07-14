package net.jmp.aes256.crypto;

/*
 * (#)Decrypter.java    0.5.0   07/13/2024
 * (#)Decrypter.java    0.4.0   07/12/2024
 * (#)Decrypter.java    0.3.0   07/06/2024
 * (#)Decrypter.java    0.2.0   07/05/2024
 *
 * @author   Jonathan Parker
 * @version  0.5.0
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

import java.io.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import javax.crypto.*;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.jmp.aes256.config.Config;

import net.jmp.aes256.input.Options;

import net.jmp.aes256.utils.Salter;
import net.jmp.aes256.utils.SecretKeySpecBuilder;

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

        this.config.validate();
    }

    /**
     * The decrypt method. An optional string
     * is returned if the operation involved
     * decrypting a string.
     *
     * @return  java.util.Optional&lt;java.lang.String&gt;
     * @throws  net.jmp.aes256.crypto.CryptographyException
     */
    public Optional<String> decrypt() throws CryptographyException {
        this.logger.entry();

        Optional<String> result = Optional.empty();

        if (this.options.getString() != null) {
            result = Optional.of(this.decryptString());
        }

        if (this.options.getInputFile() != null && this.options.getOutputFile() != null) {
            this.decryptFile();
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("End decryption");
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Decrypt a string.
     *
     * @return  java.lang.String
     * @throws  net.jmp.aes256.crypto.CryptographyException
     * @since   0.3.0
     */
    private String decryptString() throws CryptographyException {
        this.logger.entry();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Begin decrypting string    : '{}'", this.options.getString());
            this.config.logCryptoSettings(this.logger);
        }

        final Salter salter = new Salter(this.config);
        final String salt = salter.getSalt(this.options.getUserId());

        /* Set up the initialization vector from the previously encrypted data */

        final byte[] encryptedData = Base64.getDecoder().decode(this.options.getString());
        final byte[] initializationVector = new byte[Config.INITIALIZATION_VECTOR_SIZE];

        System.arraycopy(encryptedData, 0, initializationVector, 0, initializationVector.length);

        final IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        /* Set up the secret key spec */

        final SecretKeySpecBuilder secretKeySpecBuilder = new SecretKeySpecBuilder(this.config);
        final SecretKeySpec secretKeySpec = secretKeySpecBuilder.build(this.options.getPassword(), salt);

        /* Set up the cipher */

        final Cipher cipher = this.createCipher(secretKeySpec, ivParameterSpec);

        /* Perform the decryption - The cipher text does not contain the IV */

        final byte[] cipherText = new byte[encryptedData.length - initializationVector.length];

        System.arraycopy(encryptedData, initializationVector.length, cipherText, 0, cipherText.length);

        byte[] decryptedData;

        try {
            decryptedData = cipher.doFinal(cipherText);
        } catch (final IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptographyException("Unable to decrypt data", e);
        }

        String result;

        try {
            result = new String(decryptedData, this.config.getCipher().getCharacterSet());
        } catch (final UnsupportedEncodingException uee) {
            throw new CryptographyException("Unable to stringify decrypted data", uee);
        }

        this.logger.exit(result);

        return result;
    }

    /**
     * Decrypt a file.
     *
     * @since   0.3.0
     * @throws  net.jmp.aes256.crypto.CryptographyException
     */
    private void decryptFile() throws CryptographyException {
        this.logger.entry();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Begin decrypting file: '{}'", this.options.getInputFile());
        }

        if (this.doesInputFileExist()) {
            final Salter salter = new Salter(this.config);
            final String salt = salter.getSalt(this.options.getUserId());

            /* Set up the initialization vector from the previously encrypted data */

            final byte[] initializationVector = new byte[Config.INITIALIZATION_VECTOR_SIZE];

            try (final FileInputStream inputStream = new FileInputStream(this.options.getInputFile())) {
                final int bytesRead = inputStream.read(initializationVector, 0, Config.INITIALIZATION_VECTOR_SIZE);

                if (bytesRead != Config.INITIALIZATION_VECTOR_SIZE) {
                    throw new CryptographyException("Unable to read initialization vector");
                }
            } catch (final IOException ioe) {
                throw new CryptographyException("I/O error processing input file: " + this.options.getInputFile(), ioe);
            }

            final IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

            /* Set up the secret key spec */

            final SecretKeySpecBuilder secretKeySpecBuilder = new SecretKeySpecBuilder(this.config);
            final SecretKeySpec secretKeySpec = secretKeySpecBuilder.build(this.options.getPassword(), salt);

            /* Set up the cipher */

            final Cipher cipher = this.createCipher(secretKeySpec, ivParameterSpec);

            /* Perform the decryption */

            try (final FileInputStream inputStream = new FileInputStream(this.options.getInputFile())) {
                try (final FileOutputStream outputStream = new FileOutputStream(this.options.getOutputFile())) {
                    final byte[] buffer = new byte[64];

                    /* Skip over the IV */

                    final long bytesSkipped = inputStream.skip(Config.INITIALIZATION_VECTOR_SIZE);

                    if (bytesSkipped != Config.INITIALIZATION_VECTOR_SIZE) {
                        throw new CryptographyException("Unable to read beyond initialization vector");
                    }

                    /* The remaining data is cipher text */

                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        final byte[] output = cipher.update(buffer, 0, bytesRead);

                        if (output != null) {
                            outputStream.write(output);
                        }
                    }

                    final byte[] output = cipher.doFinal();

                    if (output != null) {
                        outputStream.write(output);
                    }
                } catch (final IllegalBlockSizeException | BadPaddingException e) {
                    throw new CryptographyException("Unable to decrypt data", e);
                }
            } catch (final IOException ioe) {
                throw new CryptographyException("I/O error processing input file: " + this.options.getInputFile(), ioe);
            }

        } else {
            System.out.format("Input file '%s' does not exist%n", this.options.getInputFile());
        }
        
        this.logger.exit();
    }

    /**
     * Create and return the cipher.
     *
     * @param   secretKeySpec   javax.crypto.spec.SecretKeySpec
     * @param   ivParameterSpec javax.crypto.spec.IvParameterSpec
     * @return                  javax.crypto.Cipher
     * @throws                  net.jmp.aes256.crypto.CryptographyException
     * @since                   0.5.0
     */
    private Cipher createCipher(final SecretKeySpec secretKeySpec, final IvParameterSpec ivParameterSpec) throws CryptographyException {
        this.logger.entry(secretKeySpec, ivParameterSpec);

        assert secretKeySpec != null;
        assert ivParameterSpec != null;

        Cipher cipher;

        try {
            cipher = Cipher.getInstance(this.config.getCipher().getInstance());
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CryptographyException("Unable to instantiate cipher: " + this.config.getCipher().getInstance(), e);
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        } catch (final InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new CryptographyException("Unable to initialize cipher", e);
        }

        this.logger.exit(cipher);

        return cipher;
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

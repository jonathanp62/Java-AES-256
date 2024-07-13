package net.jmp.aes256.utils;

/*
 * (#)SecretKeySpecBuilder.java 0.4.0   07/13/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
 * @since    0.4.0
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

import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.util.Objects;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import net.jmp.aes256.config.Config;

import net.jmp.aes256.crypto.CryptographyException;

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

/**
 * A utility class that builds the secret
 * key spec based on the configuration.
 */
public final class SecretKeySpecBuilder {
    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /** The configuration. */
    private final Config config;

    /**
     * The default constructor.
     */
    private SecretKeySpecBuilder() {
        throw new UnsupportedOperationException("The default constructor is not supported");
    }

    /**
     * A constructor that takes the configuration.
     *
     * @param   config  net.jmp.aes256.config.Config
     */
    public SecretKeySpecBuilder(final Config config) {
        super();

        this.config = Objects.requireNonNull(config);
    }

    /**
     * Build and return the secret key spec object.
     *
     * @return  javax.crypto.spec.SecretKeySpec
     * @throws  net.jmp.aes256.crypto.CryptographyException
     */
    public SecretKeySpec build(final String password, final String salt) throws CryptographyException {
        this.logger.entry(password, salt);

        Objects.requireNonNull(password);
        Objects.requireNonNull(salt);

        /* Set up the secret key factory */

        SecretKeyFactory secretKeyFactory;

        try {
            secretKeyFactory = SecretKeyFactory.getInstance(this.config.getSecretKeyFactoryInstance());
        } catch (final NoSuchAlgorithmException nsae) {
            throw new CryptographyException("Unable to instantiate secret key factory: " + this.config.getSecretKeyFactoryInstance(), nsae);
        }

        final KeySpec keySpec = new PBEKeySpec(
                password.toCharArray(),
                salt.getBytes(),
                this.config.getPbeKeySpecIterations(),
                this.config.getPbeKeySpecKeyLength()
        );

        /* Set up the secret key */

        SecretKey secretKey;

        try {
            secretKey = secretKeyFactory.generateSecret(keySpec);
        } catch (final InvalidKeySpecException ikse) {
            throw new CryptographyException("Unable to generate secret key", ikse);
        }

        /* Build the secret key spec */

        final SecretKeySpec secretKeySpec = new SecretKeySpec(
                secretKey.getEncoded(),
                this.config.getSecretKeySpecAlgorithm()
        );

        this.logger.exit(secretKeySpec);

        return secretKeySpec;
    }
}

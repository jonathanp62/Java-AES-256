package net.jmp.aes256.config;

/*
 * (#)Config.java   0.5.0   07/20/2024
 * (#)Config.java   0.4.0   07/12/2024
 * (#)Config.java   0.3.0   07/08/2024
 *
 * @author    Jonathan Parker
 * @version   0.5.0
 * @since     0.3.0
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

import com.google.gson.annotations.SerializedName;

import org.slf4j.ext.XLogger;

import java.util.Objects;

/**
 * The configuration class.
 */
public final class Config {
    /** The size of the initialization vector. */
    public static int INITIALIZATION_VECTOR_SIZE = 16;

    /** The cipher component. */
    @SerializedName("cipher")
    private Cipher cipher;

    /** The salter component. */
    @SerializedName("salter")
    private Salter salter;

    /** The minimum password length. @since 0.4.0 */
    @SerializedName("password-minimum-length")
    private int passwordMinimumLength;

    /** The number of iterations for password based encryption. */
    @SerializedName("pbe-key-spec-iterations")
    private int pbeKeySpecIterations;

    /** The key length for password based encryption. */
    @SerializedName("pbe-key-spec-key-length")
    private int pbeKeySpecKeyLength;

    /** The secret key factory instance. */
    @SerializedName("secret-key-factory-instance")
    private String secretKeyFactoryInstance;

    /** The secret key algorithm. */
    @SerializedName("secret-key-spec-algorithm")
    private String secretKeySpecAlgorithm;

    /**
     * Return the cipher component.
     *
     * @return  net.jmp.aes256.config.Cipher
     */
    public Cipher getCipher() {
        return this.cipher;
    }

    /**
     * Set the cipher component.
     *
     * @param   cipher  net.jmp.aes256.config.Cipher
     */
    public void setCipher(final Cipher cipher) {
        this.cipher = cipher;
    }

    /**
     * Return the salter component.
     *
     * @return  net.jmp.aes256.config.Salter
     */
    public Salter getSalter() {
        return this.salter;
    }

    /**
     * Set the salter component.
     *
     * @param   salter  net.jmp.aes256.config.Salter
     */
    public void setSalter(final Salter salter) {
        this.salter = salter;
    }

    /**
     * Return the minimum password length.
     *
     * @return  int
     * @since   0.4.0
     */
    public int getPasswordMinimumLength() {
        return this.passwordMinimumLength;
    }

    /**
     * Set the minimum password length.
     *
     * @param   passwordMinimumLength   int
     * @since                           0.4.0
     */
    public void setPasswordMinimumLength(final int passwordMinimumLength) {
        this.passwordMinimumLength = passwordMinimumLength;
    }

    /**
     * Return the number of iterations for password based encryption.
     *
     * @return  int
     */
    public int getPbeKeySpecIterations() {
        return this.pbeKeySpecIterations;
    }

    /**
     * Set the number of iterations for password based encryption.
     *
     * @param   pbeKeySpecIterations    int
     */
    public void setPbeKeySpecIterations(final int pbeKeySpecIterations) {
        this.pbeKeySpecIterations = pbeKeySpecIterations;
    }

    /**
     * Return the key length for password based encryption.
     *
     * @return  int
     */
    public int getPbeKeySpecKeyLength() {
        return this.pbeKeySpecKeyLength;
    }

    /**
     * Set the key length for password based encryption.
     *
     * @param   pbeKeySpecKeyLength int
     */
    public void setPbeKeySpecKeyLength(final int pbeKeySpecKeyLength) {
        this.pbeKeySpecKeyLength = pbeKeySpecKeyLength;
    }

    /**
     * Return the secret key factory instance.
     *
     * @return  java.lang.String
     */
    public String getSecretKeyFactoryInstance() {
        return this.secretKeyFactoryInstance;
    }

    /**
     * Set the secret key factory instance.
     *
     * @param   secretKeyFactoryInstance    java.lang.String
     */
    public void setSecretKeyFactoryInstance(final String secretKeyFactoryInstance) {
        this.secretKeyFactoryInstance = secretKeyFactoryInstance;
    }

    /**
     * Return the secret key algorithm.
     *
     * @return  java.lang.String
     */
    public String getSecretKeySpecAlgorithm() {
        return this.secretKeySpecAlgorithm;
    }

    /**
     * Set the secret key algorithm.
     *
     * @param   secretKeySpecAlgorithm  java.lang.String
     */
    public void setSecretKeySpecAlgorithm(final String secretKeySpecAlgorithm) {
        this.secretKeySpecAlgorithm = secretKeySpecAlgorithm;
    }

    /**
     * Validate the configuration. Certain
     * settings cannot be changed without testing.
     *
     * @since   0.4.0
     */
    public void validate() {
        if (this.cipher == null) {
            throw new IllegalArgumentException("No cipher has been provided");
        }

        if (!"UTF-8".equalsIgnoreCase(this.cipher.getCharacterSet())) {
            throw new IllegalArgumentException("The cipher character set must be UTF-8");
        }

        if (!"AES/CBC/PKCS5Padding".equalsIgnoreCase(this.cipher.getInstance())) {
            throw new IllegalArgumentException("The cipher instance must be AES/CBC/PKCS5Padding");
        }

        if (!PBEKeyLengths.getInstance().getKeyLengths().contains(this.pbeKeySpecKeyLength)) {
            throw new IllegalArgumentException("PBE key length " + this.pbeKeySpecKeyLength + " is not supported");
        }

        if (!"AES".equalsIgnoreCase(this.secretKeySpecAlgorithm)) {
            throw new IllegalArgumentException("The secret key spec algorithm must be AES");
        }

        if (!"PBKDF2WithHmacSHA256".equalsIgnoreCase(this.secretKeyFactoryInstance)) {
            throw new IllegalArgumentException("The secret key factory instance must be PBKDF2WithHmacSHA256");
        }

        if (this.salter == null) {
            throw new IllegalArgumentException("No salter has been provided");
        }
    }

    /**
     * Log the cryptography settings.
     *
     * @param   logger  org.slf4j.ext.XLogger
     * @since           0.4.0
     */
    public void logCryptoSettings(final XLogger logger) {
        if (logger.isDebugEnabled()) {
            logger.debug("Secret key factory instance: '{}'", this.secretKeyFactoryInstance);
            logger.debug("Secret key spec algorithm  : '{}'", this.secretKeySpecAlgorithm);
            logger.debug("Cipher instance            : '{}'", this.cipher.getInstance());
            logger.debug("Cipher character set       : '{}'", this.cipher.getCharacterSet());
            logger.debug("PBE key spec iterations    : {}", this.pbeKeySpecIterations);
            logger.debug("PBE key length             : {}", this.pbeKeySpecKeyLength);
        }
    }

    /**
     * The equals method.
     *
     * @param   o   java.lang.Object
     * @return      boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Config config = (Config) o;

        return this.passwordMinimumLength == config.passwordMinimumLength &&
                this.pbeKeySpecIterations == config.pbeKeySpecIterations &&
                this.pbeKeySpecKeyLength == config.pbeKeySpecKeyLength &&
                Objects.equals(this.cipher, config.cipher) &&
                Objects.equals(this.salter, config.salter) &&
                Objects.equals(this.secretKeyFactoryInstance, config.secretKeyFactoryInstance) &&
                Objects.equals(this.secretKeySpecAlgorithm, config.secretKeySpecAlgorithm);
    }

    /**
     * The hash-code method.
     *
     * @return  int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.cipher,
                this.salter,
                this.passwordMinimumLength,
                this.pbeKeySpecIterations,
                this.pbeKeySpecKeyLength,
                this.secretKeyFactoryInstance,
                this.secretKeySpecAlgorithm);
    }

    /**
     * The to-string method.
     *
     * @return  java.lang.String
     */
    @Override
    public String toString() {
        return "Config{" +
                "cipher=" + this.cipher +
                ", salter=" + this.salter +
                ", passwordMinimumLength=" + this.passwordMinimumLength +
                ", pbeKeySpecIterations=" + this.pbeKeySpecIterations +
                ", pbeKeySpecKeyLength=" + this.pbeKeySpecKeyLength +
                ", secretKeyFactoryInstance='" + this.secretKeyFactoryInstance + '\'' +
                ", secretKeySpecAlgorithm='" + this.secretKeySpecAlgorithm + '\'' +
                '}';
    }
}
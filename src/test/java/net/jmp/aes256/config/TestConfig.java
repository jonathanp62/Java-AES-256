package net.jmp.aes256.config;

/*
 * (#)TestConfig.java   0.5.0   07/20/2024
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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestConfig {
    private Config config;
    private Cipher cipher;
    private Salter salter;

    @Before
    public void setUp() {
        this.config = new Config();
        this.cipher = new Cipher();
        this.salter = new Salter();

        this.config.setCipher(this.cipher);
        this.config.setSalter(this.salter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoCipher() {
        final var configuration = new Config();

        configuration.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCipherCharacterSet() {
        this.cipher.setCharacterSet("UTF-16");

        this.config.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCipherInstance() {
        this.cipher.setCharacterSet("UTF-8");
        this.cipher.setInstance("AES/EBC");

        this.config.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPBEKeySpecLength() {
        this.cipher.setCharacterSet("UTF-8");
        this.cipher.setInstance("AES/CBC/PKCS5Padding");

        this.config.setPbeKeySpecKeyLength(193);
        this.config.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSecretKeySpecAlgorithm() {
        cipher.setCharacterSet("UTF-8");
        cipher.setInstance("AES/CBC/PKCS5Padding");

        config.setCipher(cipher);
        config.setPbeKeySpecKeyLength(256);
        config.setSecretKeySpecAlgorithm("EAS");
        config.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSecretKeyFactoryInstance() {
        this.cipher.setCharacterSet("UTF-8");
        this.cipher.setInstance("AES/CBC/PKCS5Padding");

        this.config.setPbeKeySpecKeyLength(256);
        this.config.setSecretKeySpecAlgorithm("AES");
        this.config.setSecretKeyFactoryInstance("PBKDF2WithHmacSHA1");
        this.config.validate();
    }

    @Test
    public void testValidConfig() {
        this.cipher.setCharacterSet("UTF-8");
        this.cipher.setInstance("AES/CBC/PKCS5Padding");

        this.config.setPbeKeySpecKeyLength(256);
        this.config.setSecretKeySpecAlgorithm("AES");
        this.config.setSecretKeyFactoryInstance("PBKDF2WithHmacSHA256");

        assertNotNull(this.config.getCipher());
        assertNotNull(this.config.getSalter());

        this.config.validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSalter() {
        this.config.setSalter(null);

        this.cipher.setCharacterSet("UTF-8");
        this.cipher.setInstance("AES/CBC/PKCS5Padding");

        this.config.setPbeKeySpecKeyLength(256);
        this.config.setSecretKeySpecAlgorithm("AES");
        this.config.setSecretKeyFactoryInstance("PBKDF2WithHmacSHA256");
        this.config.validate();
    }
}

package net.jmp.aes256.crypto;

/*
 * (#)TestEncrypter.java    0.4.0   07/12/2024
 * (#)TestEncrypter.java    0.3.0   07/06/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
 * @since    0.3.0
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

import java.io.File;

import java.net.URL;

import net.jmp.aes256.config.Config;

import net.jmp.aes256.input.Options;

import net.jmp.aes256.utils.Builder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public final class TestEncrypter {
    private Config config;
    private Options fileOptions;
    private Options stringOptions;

    @Before
    public void before() {
        this.setupConfig();
        this.setupFileOptions();
        this.setupStringOptions();
    }

    private void setupConfig() {
        this.config = new Config();

        final var cipher = new net.jmp.aes256.config.Cipher();

        cipher.setCharacterSet("UTF-8");
        cipher.setInstance("AES/CBC/PKCS5Padding");

        final var salter = new net.jmp.aes256.config.Salter();

        salter.setCharacterSet("UTF-8");
        salter.setIterations(3);

        this.config.setSalter(salter);
        this.config.setCipher(cipher);

        this.config.setPasswordMinimumLength(20);
        this.config.setPbeKeySpecIterations(65536);
        this.config.setPbeKeySpecKeyLength(256);
        this.config.setSecretKeyFactoryInstance("PBKDF2WithHmacSHA256");
        this.config.setSecretKeySpecAlgorithm("AES");
    }

    private void setupFileOptions() {
        final URL url = Thread.currentThread().getContextClassLoader().getResource("file-to-encrypt.xml");

        assert url != null;

        final File file = new File(url.getPath());

        this.fileOptions = Builder.of(Options::new)
                .with(Options::setString, null)
                .with(Options::setInputFile, file.getAbsolutePath())
                .with(Options::setOutputFile, "/Users/jonathan/IDEA-Projects/AES-256/out/encrypted-file.bin")
                .with(Options::setUserId, "jonathanp62@gmail.com")
                .with(Options::setPassword, "johann_Sebastian%Bach-6(Partitas)")
                .build();
    }

    private void setupStringOptions() {
        this.stringOptions = Builder.of(Options::new)
                .with(Options::setString, "The quick brown fox jumped over the lazy dog!")
                .with(Options::setInputFile, null)
                .with(Options::setOutputFile, null)
                .with(Options::setUserId, "jonathanp62@gmail.com")
                .with(Options::setPassword, "johann_Sebastian%Bach-6(Partitas)")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullConfig() {
        new Encrypter(null, this.fileOptions);
    }

    @Test(expected = NullPointerException.class)
    public void testNullOptions() {
        new Encrypter(this.config, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCipherCharacterSet() {
        this.config.getCipher().setCharacterSet("us-ascii");

        new Encrypter(this.config, this.stringOptions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCipherInstance() {
        this.config.getCipher().setInstance("invalid");

        new Encrypter(this.config, this.stringOptions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPBEKeyLength() {
        this.config.setPbeKeySpecKeyLength(127);

        new Encrypter(this.config, this.stringOptions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSecretKeySpecAlgorithm() {
        this.config.setSecretKeySpecAlgorithm("invalid");

        new Encrypter(this.config, this.stringOptions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSecretKeyFactoryInstance() {
        this.config.setSecretKeyFactoryInstance("invalid");

        new Encrypter(this.config, this.stringOptions);
    }

    @Test
    public void testDoesInputFileExist() throws Exception {
        final var encrypter = new Encrypter(this.config, this.fileOptions);
        final var method = Encrypter.class.getDeclaredMethod("doesInputFileExist");

        method.setAccessible(true);

        Boolean result = (Boolean) method.invoke(encrypter);

        assertTrue(result);

        this.fileOptions.setInputFile("/Users/jonathan/does-not-exist.txt");

        result = (Boolean) method.invoke(encrypter);

        assertFalse(result);
    }

    @Test
    public void testEncryptString() throws Exception {
        final var encrypter = new Encrypter(this.config, this.stringOptions);
        final var encrypted = encrypter.encrypt();

        assertTrue(encrypted.isPresent());

        this.stringOptions.setString(encrypted.get());  // Decrypt the value return by encrypt

        final var decrypter = new Decrypter(this.config, this.stringOptions);
        final var decrypted = decrypter.decrypt();

        assertTrue(decrypted.isPresent());

        assertEquals("The quick brown fox jumped over the lazy dog!", decrypted.get());
    }
}

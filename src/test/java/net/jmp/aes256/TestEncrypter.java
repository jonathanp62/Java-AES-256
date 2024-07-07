package net.jmp.aes256;

/*
 * (#)TestEncrypter.java    0.3.0   07/06/2024
 *
 * @author   Jonathan Parker
 * @version  0.3.0
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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class TestEncrypter {
    private Options fileOptions;

    @Before
    public void before() {
        final URL url = Thread.currentThread().getContextClassLoader().getResource("file-to-encrypt.xml");

        assert url != null;

        final File file = new File(url.getPath());

        this.fileOptions = Builder.of(Options::new)
                .with(Options::setString, null)
                .with(Options::setInputFile, file.getAbsolutePath())
                .with(Options::setOutputFile, "/Users/jonathan/IDEA-Projects/AES-256/out/encrypted-file.bin")
                .with(Options::setUserId, "jonathanp62@gmail.com")
                .with(Options::setPassword, "test-password")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testNull() {
        new Encrypter(null);
    }

    @Test
    public void testDoesInputFileExist() throws Exception {
        final var encrypter = new Encrypter(this.fileOptions);
        final var method = Encrypter.class.getDeclaredMethod("doesInputFileExist");

        method.setAccessible(true);

        Boolean result = (Boolean) method.invoke(encrypter);

        assertTrue(result);

        this.fileOptions.setInputFile("/Users/jonathan/does-not-exist.txt");

        result = (Boolean) method.invoke(encrypter);

        assertFalse(result);
    }
}

package net.jmp.aes256.utils;

/*
 * (#)TestSalter.java   0.4.0   07/12/2024
 * (#)TestSalter.java   0.3.0   07/07/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
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

import net.jmp.aes256.config.Config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSalter {
    private Config config;

    @Before
    public void before() {
        this.config = new Config();

        final var salter = new net.jmp.aes256.config.Salter();

        salter.setCharacterSet("UTF-8");
        salter.setIterations(3);

        this.config.setSalter(salter);
    }

    @Test
    public void testGetSalt1() {
        final var salter = new net.jmp.aes256.utils.Salter(this.config);

        final var expected = "WVcwNWRWbFlVbTlaVnpWMFdWaEtNR0ZYTlhkWldFcHlXbGhLUVdNeU9YUmFWMUoyWWxkR2NHSnBOV3BpTWpBOQ==";
        final var result = salter.getSalt("jonathanmartinparker@somedomain.com");

        assertEquals(expected, result);
    }

    @Test
    public void testGetSalt2() {
        final var salter = new net.jmp.aes256.utils.Salter(this.config);

        final var expected = "WVcwNWRWbFlVbTlaVnpWM1RtcEtRVm95TVdoaFYzZDFXVEk1ZEE9PQ==";
        final var result = salter.getSalt("jonathanp62@gmail.com");

        assertEquals(expected, result);
    }

    @Test(expected = NullPointerException.class)
    public void testGetSaltWithNull() {
        new net.jmp.aes256.utils.Salter(null);
    }

    @Test(expected = SalterException.class)
    public void testUnsupportedCharacterSet() {
        final var salter = this.config.getSalter();

        salter.setCharacterSet("Not-Supported");

        new net.jmp.aes256.utils.Salter(this.config);
    }

    @Test
    public void testLowercaseCharacterSet() {
        final var salterConfig = this.config.getSalter();

        salterConfig.setCharacterSet("utf-8");

        new net.jmp.aes256.utils.Salter(this.config);

        final var salter = new net.jmp.aes256.utils.Salter(this.config);

        final var expected = "WVcwNWRWbFlVbTlaVnpWM1RtcEtRVm95TVdoaFYzZDFXVEk1ZEE9PQ==";
        final var result = salter.getSalt("jonathanp62@gmail.com");

        assertEquals(expected, result);
    }
}

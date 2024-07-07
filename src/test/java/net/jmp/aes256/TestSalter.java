package net.jmp.aes256;

/*
 * (#)TestSalter.java   0.3.0   07/07/2024
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

import org.junit.Test;

import static org.junit.Assert.*;

public class TestSalter {
    @Test
    public void testGetSalt1() throws Exception {
        final var salter = new Salter("jonathanmartinparker@somedomain.com");

        final var expected = "WVcwNWRWbFlVbTlaVnpWMFdWaEtNR0ZYTlhkWldFcHlXbGhLUVdNeU9YUmFWMUoyWWxkR2NHSnBOV3BpTWpBOQ==";
        final var result = salter.getSalt();

        assertEquals(expected, result);
    }

    @Test
    public void testGetSalt2() throws Exception {
        final var salter = new Salter("jonathanp62@gmail.com");

        final var expected = "WVcwNWRWbFlVbTlaVnpWM1RtcEtRVm95TVdoaFYzZDFXVEk1ZEE9PQ==";
        final var result = salter.getSalt();

        assertEquals(expected, result);
    }

    @Test(expected = NullPointerException.class)
    public void testGetSaltWithNull() throws Exception {
        new Salter(null);
    }
}

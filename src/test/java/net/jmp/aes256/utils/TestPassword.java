package net.jmp.aes256.utils;

/*
 * (#)TestPassword.java 0.4.0   07/10/2024
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

public class TestPassword {
    private final String strongPassword = "someUppercaseWith123and()End";
    private final int minimumLength = 20;

    @Test
    public void testValidPassword() {
        try {
            Password.validate(this.strongPassword, this.minimumLength);
        } catch (final PasswordException pe) {
            fail(pe.getMessage());
        }
    }

    @Test
    public void testForUppercaseLetters() {
        try {
            Password.validate(this.strongPassword, this.minimumLength);
        } catch (final PasswordException pe) {
            fail(pe.getMessage());
        }
    }

    @Test(expected = PasswordException.class)
    public void testInvalidPasswordNoUppercaseLetters() throws PasswordException {
        Password.validate(this.strongPassword.toLowerCase(), this.minimumLength);
    }

    @Test
    public void testForLowercaseLetters() {
        try {
            Password.validate(this.strongPassword, this.minimumLength);
        } catch (final PasswordException pe) {
            fail(pe.getMessage());
        }
    }

    @Test(expected = PasswordException.class)
    public void testInvalidPasswordNoLowercaseLetters() throws PasswordException {
        Password.validate(this.strongPassword.toUpperCase(), this.minimumLength);
    }

    @Test
    public void testForNumbers() {
        try {
            Password.validate(this.strongPassword, this.minimumLength);
        } catch (final PasswordException pe) {
            fail(pe.getMessage());
        }
    }

    @Test(expected = PasswordException.class)
    public void testInvalidPasswordNoNumbers() throws PasswordException {
        Password.validate("someUppercaseWith[]and()End", this.minimumLength);
    }

    @Test
    public void testForSpecialCharacters() {
        try {
            Password.validate(this.strongPassword, this.minimumLength);
        } catch (final PasswordException pe) {
            fail(pe.getMessage());
        }

        try {
            Password.validate("sU1~`!@#$%^&*()_-+={}[]<>,.?/", this.minimumLength);
        } catch (final PasswordException pe) {
            fail(pe.getMessage());
        }

    }

    @Test(expected = PasswordException.class)
    public void testInvalidPasswordNoSpecialCharacters() throws PasswordException {
        Password.validate("someUppercaseWithand12345End", this.minimumLength);
    }

    @Test
    public void testPasswordIsLongEnough() {
        try {
            Password.validate(this.strongPassword, this.minimumLength);
        } catch (final PasswordException pe) {
            fail(pe.getMessage());
        }
    }

    @Test(expected = PasswordException.class)
    public void testInvalidPasswordNotLongEnough() throws PasswordException {
        Password.validate("someUpper'&%8", this.minimumLength);
    }
}

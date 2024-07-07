package net.jmp.aes256;

/*
 * (#)TestOptionsHandler.java   0.2.0   07/02/2024
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

public final class TestOptionsHandler {
    @Test(expected = NullPointerException.class)
    public void testNull() {
        new OptionsHandler(null);
    }

    @Test
    public void testIsHandled() {
        final var args = new String[] {"decrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.DECRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        assertFalse(optionsHandler.isHandled());
    }

    @Test
    public void testDecryptStringNoUser() {
        final var args = new String[] {"decrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.DECRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertTrue(optionsHandler.containsString());
        assertFalse(optionsHandler.containsInputFile());
        assertFalse(optionsHandler.containsOutputFile());
        assertFalse(optionsHandler.containsUserId());
    }

    @Test
    public void testDecryptStringUser() {
        final var args = new String[] {"decrypt", "--string", "The quick brown fox jumped over the lazy dog!", "--user", "jonathanp62@gmail.com"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.DECRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertTrue(optionsHandler.containsString());
        assertFalse(optionsHandler.containsInputFile());
        assertFalse(optionsHandler.containsOutputFile());
        assertTrue(optionsHandler.containsUserId());
    }

    @Test
    public void testDecryptFileNoUser() {
        final var args = new String[] {"decrypt", "--input-file", "/usr/local/input-file.encrypted", "--output-file", "/usr/local/output-file.txt"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.DECRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertFalse(optionsHandler.containsString());
        assertTrue(optionsHandler.containsInputFile());
        assertTrue(optionsHandler.containsOutputFile());
        assertFalse(optionsHandler.containsUserId());
    }

    @Test
    public void testDecryptFileUser() {
        final var args = new String[] {"decrypt", "--input-file", "/usr/local/input-file.encrypted", "--output-file", "/usr/local/output-file.txt", "--user", "jonathanp62@gmail.com"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.DECRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertFalse(optionsHandler.containsString());
        assertTrue(optionsHandler.containsInputFile());
        assertTrue(optionsHandler.containsOutputFile());
        assertTrue(optionsHandler.containsUserId());
    }

    @Test
    public void testEncryptStringNoUser() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.ENCRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertTrue(optionsHandler.containsString());
        assertFalse(optionsHandler.containsInputFile());
        assertFalse(optionsHandler.containsOutputFile());
        assertFalse(optionsHandler.containsUserId());
    }

    @Test
    public void testEncryptStringUser() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!", "--user", "jonathanp62@gmail.com"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.ENCRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertTrue(optionsHandler.containsString());
        assertFalse(optionsHandler.containsInputFile());
        assertFalse(optionsHandler.containsOutputFile());
        assertTrue(optionsHandler.containsUserId());
    }

    @Test
    public void testEncryptFileNoUser() {
        final var args = new String[] {"encrypt", "--input-file", "/usr/local/input-file.txt", "--output-file", "/usr/local/output-file.encrypted"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.ENCRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertFalse(optionsHandler.containsString());
        assertTrue(optionsHandler.containsInputFile());
        assertTrue(optionsHandler.containsOutputFile());
        assertFalse(optionsHandler.containsUserId());
    }

    @Test
    public void testEncryptFileUser() {
        final var args = new String[] {"encrypt", "--input-file", "/usr/local/input-file.txt", "--output-file", "/usr/local/output-file.encrypted", "--user", "jonathanp62@gmail.com"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.ENCRYPT, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertFalse(optionsHandler.containsString());
        assertTrue(optionsHandler.containsInputFile());
        assertTrue(optionsHandler.containsOutputFile());
        assertTrue(optionsHandler.containsUserId());
    }

    @Test
    public void testUnknown() {
        final var args = new String[] {"unknown", "--input-file", "/usr/local/input-file.txt", "--output-file", "/usr/local/output-file.encrypted", "--user", "jonathanp62@gmail.com"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.UNRECOGNIZED, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertTrue(optionsHandler.isHandled());
        assertFalse(optionsHandler.containsString());
        assertTrue(optionsHandler.containsInputFile());
        assertTrue(optionsHandler.containsOutputFile());
        assertTrue(optionsHandler.containsUserId());
    }

    @Test
    public void testOnlyUser() {
        final var args = new String[] {"unknown", "--user", "jonathanp62@gmail.com"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.UNRECOGNIZED, handler.getCommandOperation());
        assertTrue(handler.getCommandLine().isPresent());

        final var optionsHandler = new OptionsHandler(handler.getCommandLine().get());

        optionsHandler.handle();

        assertFalse(optionsHandler.isHandled());
    }
}

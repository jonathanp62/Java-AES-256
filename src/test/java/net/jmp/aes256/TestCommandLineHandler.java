package net.jmp.aes256;

/*
 * (#)TestCommandLineHandler.java   0.2.0   07/02/2024
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

public final class TestCommandLineHandler {
    @Test
    public void testIsHandled() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertTrue(handler.isHandled());
    }

    @Test
    public void testIsNotHandled() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        assertFalse(handler.isHandled());
    }

    @Test
    public void testGetCommandOperationWhenHandled() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertNotNull(handler.getCommandOperation());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetCommandOperationWhenNotHandled() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.getCommandOperation();
    }

    @Test
    public void testGetCommandLineWhenHandled() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertTrue(handler.getCommandLine().isPresent());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetCommandLineWhenNotHandled() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.getCommandLine();
    }

    @Test
    public void testIsolateCommandOperationDecrypt() {
        final var args = new String[] {"decrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.DECRYPT, handler.getCommandOperation());
    }

    @Test
    public void testIsolateCommandOperationEncrypt() {
        final var args = new String[] {"encrypt", "--string", "The quick brown fox jumped over the lazy dog!"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.ENCRYPT, handler.getCommandOperation());
    }

    @Test
    public void testIsolateCommandOperationHelp() {
        var args = new String[] {"--help"};
        var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.HELP, handler.getCommandOperation());

        args = new String[] {"-h"};
        handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.HELP, handler.getCommandOperation());
        args = new String[] {"help"};
        handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.HELP, handler.getCommandOperation());
    }

    @Test
    public void testIsolateCommandOperationUnrecognized() {
        final var args = new String[] {"unknown"};
        final var handler = new CommandLineHandler(args);

        handler.handle();

        assertEquals(CommandOperation.UNRECOGNIZED, handler.getCommandOperation());
    }
}

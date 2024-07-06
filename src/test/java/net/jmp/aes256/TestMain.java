package net.jmp.aes256;

/*
 * (#)TestMain.java 0.2.0   07/06/2024
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

import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.*;

public final class TestMain {
    @Test
    public void testPromptForUserId() throws Exception {
        final var main = new Main();
        final var method = Main.class.getDeclaredMethod("promptForUserId", String.class);

        method.setAccessible(true);

        final String userId = "jonathanp62@gmail.com";

        @SuppressWarnings("unchecked")
        Optional<String> result = (Optional<String>) method.invoke(main, userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get());

        // As of Gradle 8.8 it is not possible in the :test task to set the standard input
    }
}

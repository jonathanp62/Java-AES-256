package net.jmp.aes256.utils;

/*
 * (#)SHA256.java   0.5.0   07/14/2024
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.util.Objects;

/**
 * A utility class for computing SHA256 checksums.
 */
public final class SHA256 {
    private static final byte[] HEX_CHARACTER_TABLE = {
            (byte)'0', (byte)'1', (byte)'2', (byte)'3',
            (byte)'4', (byte)'5', (byte)'6', (byte)'7',
            (byte)'8', (byte)'9', (byte)'a', (byte)'b',
            (byte)'c', (byte)'d', (byte)'e', (byte)'f'
    };

    /**
     * The default constructor.
     */
    private SHA256() {
        super();
    }

    /**
     * Get the SHA256 of the specified file.
     *
     * @param   fileName    java.lang.String
     * @return              java.lang.String
     * @throws              java.lang.String
     */
    public static String getFileSHA256(final String fileName) throws Exception {
        Objects.requireNonNull(fileName);

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] buffer = new byte[8192];

        int count;

        try (final BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            while ((count = inputStream.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
        }

        return bytesToHexString(digest.digest());
    }

    /**
     * Convert an array of bytes to a hexadecimal string.
     *
     * @param   bytes   byte[]
     * @return          java.lang.String
     */
    private static String bytesToHexString(final byte[] bytes) {
        Objects.requireNonNull(bytes);

        final byte[] hex = new byte[2 * bytes.length];

        int index = 0;

        for (byte b : bytes) {
            int v = b & 0xFF;

            hex[index++] = HEX_CHARACTER_TABLE[v >>> 4];
            hex[index++] = HEX_CHARACTER_TABLE[v & 0xF];
        }

        return new String(hex, StandardCharsets.US_ASCII);
    }
}

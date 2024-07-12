package net.jmp.aes256.config;

/*
 * (#)CharacterSets.java    0.4.0   07/12/2024
 *
 * @author    Jonathan Parker
 * @version   0.4.0
 * @since     0.4.0
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

import java.util.List;

/**
 * A singleton class used to encapsulate
 * the list supported character sets for
 * creating salt and performing cryptography.
 */
public final class CharacterSets {
    /** The single instance of this class. */
    private static final CharacterSets instance = new CharacterSets();

    /** The list of supported character sets. */
    private final List<String> characterSetList;

    /**
     * The default constructor.
     */
    private CharacterSets() {
        super();

        this.characterSetList = List.of(
                "ISO-8859-1",
                "US_ASCII",
                "UTF-8",
                "UTF-16",
                "UTF-16BE",
                "UTF-16LE"
        );
    }

    /**
     * Return the single instance of this class.
     *
     * @return  net.jmp.aes256.config.CharacterSets
     */
    public static CharacterSets getInstance() {
        return instance;
    }

    /**
     * Return the list of support character sets.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    public List<String> getCharacterSets() {
        return this.characterSetList;
    }
}

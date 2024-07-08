package net.jmp.aes256.config;

/*
 * (#)Cipher.java   0.3.0   07/08/2024
 *
 * @author    Jonathan Parker
 * @version   0.3.0
 * @since     0.3.0
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

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * The cipher component of the configuration class.
 */
public final class Cipher {
    /** The character set. */
    @SerializedName("character-set")
    private String characterSet;

    /** The instance. */
    @SerializedName("instance")
    private String instance;

    /**
     * Return the character set.
     *
     * @return  java.lang.String
     */
    public String getCharacterSet() {
        return this.characterSet;
    }

    /**
     * Set the character set.
     *
     * @param   characterSet    java.lang.String
     */
    public void setCharacterSet(final String characterSet) {
        this.characterSet = characterSet;
    }

    /**
     * Return the instance.
     *
     * @return  java.lang.String
     */
    public String getInstance() {
        return this.instance;
    }

    /**
     * Set the instance.
     *
     * @param   instance    java.lang.String
     */
    public void setInstance(final String instance) {
        this.instance = instance;
    }

    /**
     * The equals method.
     *
     * @param   o   java.lang.Object
     * @return      boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Cipher cipher = (Cipher) o;

        return Objects.equals(this.characterSet, cipher.characterSet) && Objects.equals(this.instance, cipher.instance);
    }

    /**
     * The hash-code method.
     *
     * @return  int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.characterSet, this.instance);
    }

    /**
     * The to-string method.
     *
     * @return  java.lang.String
     */
    @Override
    public String toString() {
        return "Cipher{" +
                "characterSet='" + this.characterSet + '\'' +
                ", instance='" + this.instance + '\'' +
                '}';
    }
}

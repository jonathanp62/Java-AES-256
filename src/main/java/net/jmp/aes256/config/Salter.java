package net.jmp.aes256.config;

/*
 * (#)Salter.java   0.3.0   07/08/2024
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
 * The salter component of the configuration class.
 */
public class Salter {
    /** The character set. */
    @SerializedName("character-set")
    private String characterSet;

    /** The number of iterations. */
    @SerializedName("iterations")
    private int iterations;

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
     * Return the number of iterations.
     *
     * @return  int
     */
    public int getIterations() {
        return this.iterations;
    }

    /**
     * Set the number of iterations.
     *
     * @param   iterations  int
     */
    public void setIterations(final int iterations) {
        this.iterations = iterations;
    }

    /**
     * The equals method.
     *
     * @param   o   java.lang.Object
     * @return      boolean
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Salter salter = (Salter) o;

        return this.iterations == salter.iterations && Objects.equals(this.characterSet, salter.characterSet);
    }

    /**
     * The hash-code method.
     *
     * @return  int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.characterSet, this.iterations);
    }

    /**
     * The to-string method.
     *
     * @return  java.lang.String
     */
    @Override
    public String toString() {
        return "Salter{" +
                "characterSet='" + this.characterSet + '\'' +
                ", iterations=" + this.iterations +
                '}';
    }
}

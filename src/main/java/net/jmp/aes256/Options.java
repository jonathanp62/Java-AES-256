package net.jmp.aes256;

/*
 * (#)Options.java  0.2.0   07/05/2024
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

/**
 * The final options class.
 */
public final class Options {
    /** The string value, if specified. */
    private String string;

    /** The input file name value, if specified. */
    private String inputFile;

    /** The output file name value, if specified. */
    private String outputFile;

    /** The user ID value. */
    private String userId;

    /** The password value. */
    private String password;

    /**
     * The default constructor.
     */
    public Options() {
        super();
    }

    /**
     * Return the string value or null.
     *
     * @return  java.lang.String
     */
    public String getString() {
        return this.string;
    }

    /**
     * Set the string value.
     *
     * @param   string  java.lang.String
     */
    public void setString(final String string) {
        this.string = string;
    }

    /**
     * Return the input file name value or null.
     *
     * @return  java.lang.String
     */
    public String getInputFile() {
        return this.inputFile;
    }

    /**
     * Set the input file name value.
     *
     * @param   inputFile   java.lang.String
     */
    public void setInputFile(final String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Return the output file name value or null.
     *
     * @return  java.lang.String
     */
    public String getOutputFile() {
        return this.outputFile;
    }

    /**
     * Set the output file name value value.
     *
     * @param   outputFile  java.lang.String
     */
    public void setOutputFile(final String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Return the user ID value.
     *
     * @return  java.lang.String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Set the user ID value.
     *
     * @param   userId  java.lang.String
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Return the password value.
     *
     * @return  java.lang.String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set the password value.
     *
     * @param   password    java.lang.String
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * The to-string method.
     *
     * @return  java.lang.String
     */
    @Override
    public String toString() {
        return "Options{" +
                "string='" + string + '\'' +
                ", inputFile='" + inputFile + '\'' +
                ", outputFile='" + outputFile + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

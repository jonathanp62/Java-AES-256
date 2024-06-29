package net.jmp.aes256;

/*
 * (#)Main.java 0.2.0   06/29/2024
 * (#)Main.java 0.1.0   06/27/2024
 *
 * @author   Jonathan Parker
 * @version  0.2.0
 * @since    0.1.0
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

import org.slf4j.LoggerFactory;

import org.slf4j.ext.XLogger;

public final class Main {
    /** The default configuration file name. */
    private static final String DEFAULT_APP_CONFIG_FILE = "config/config.json";

    /** The logger. */
    private final XLogger logger = new XLogger(LoggerFactory.getLogger(this.getClass().getName()));

    /**
     * The default constructor.
     */
    private Main() {
        super();
    }

    /**
     * The go method.
     */
    private void go() {
        this.logger.entry();

        this.logger.info("{} {}", Name.NAME_STRING, Version.VERSION_STRING);

        /*
         * 1. Check the command line arguments for a) --string | -s or b) --input-file | -i and retain the string or file name or prompt for them
         * 2. If an input file is provided an output file (--output-file | -o) will be needed
         * 3. Prompt the user for a user identifier, e.g. email address if not provided on the command line (--user | -u)
         * 4. Turn the Base 64 encoded UID into salt for the forthcoming encryption (3 iterations) (See the NetBease Base64Encoder project)
         * 5. Always prompt for the password; this avoids storing it in a script (a unit test is ok)
         * 6. The password provided serves as the secret key
         * 7. The Apache Commons CLI dependency will be needed; commons-cli:commons-cli:1.8.0; the MyWordle projects are my latest to use it
         * 8. https://stackoverflow.com/questions/13329282/test-java-programs-that-read-from-stdin-and-write-to-stdout
         * 9. First feature branch will be features/input; personal branches can align with steps 1-5
         */

        this.logger.exit();
    }

    /**
     * The main method.
     *
     * @param   args    java.lang.String[]
     */
    public static void main(final String[] args) {
        new Main().go();
    }
}

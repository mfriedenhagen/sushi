/*
 * Copyright 1&1 Internet AG, http://www.1and1.org
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.ui.sushi.fs;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import de.ui.sushi.io.OS;
import de.ui.sushi.util.Strings;

/**
 * <p>Immutable. </p>
 */
public class Settings {
    public static final String UTF_8 = "UTF-8";
    public static final String ISO8859_1 = "ISO8859_1";

    public static final String DEFAULT_LINESEPARATOR = OS.CURRENT.lineSeparator;

    private static final byte[] BYTES = { 65 };
    
    public final String encoding;
    public final String lineSeparator;

    /** Create a Buffer with UTF-8 encoding */
    public Settings() {
        this(UTF_8);
    }

    public Settings(String encoding) {
        this(encoding, DEFAULT_LINESEPARATOR);
    }

    public Settings(String encoding, String lineSeparator) {
        try {
            new String(BYTES, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(encoding, e);
        }
        this.encoding = encoding;
        this.lineSeparator = lineSeparator;
    }
    
    public String join(String ... lines) {
        return Strings.join(lineSeparator, lines);
    }

    public String string(byte[] bytes) {
        try {
            return new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String string(ByteArrayOutputStream stream) {
        try {
            return stream.toString(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        
    }

    public byte[] bytes(String str) {
        try {
            return str.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

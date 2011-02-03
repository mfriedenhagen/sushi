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

package com.oneandone.sushi.fs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SettingsTest {
    @Test(expected=IllegalArgumentException.class)
    public void invalidEncoding() {
         new Settings("nosuchencoding");
    }

    @Test
    public void encoding() {
        encode("");
        encode("abc");
        encode("\u0000\u0001");
    }

    private void encode(String str) {
        Settings settings;
        
        settings = new Settings();
        assertEquals(str, settings.string(settings.bytes(str)));
    }
}
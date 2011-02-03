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

package com.oneandone.sushi.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Misc static utility methods.
 */
public class Util {
    public static File absoluteFile(File dir, String fileName) {
        File file;

        file = new File(fileName);
        if (file.isAbsolute()) {
            return file;
        }
        return new File(dir, fileName);
    }

    public static String[] split(String str, char c) {
        List<String> lst;
        int idx;
        int prev;
        String[] ar;

        lst = new ArrayList<String>();
        idx = str.indexOf(c);
        prev = 0;
        while (idx != -1) {
            lst.add(str.substring(prev, idx));
            prev = idx + 1;
            idx = str.indexOf(c, prev);
        }
        lst.add(str.substring(prev));
        ar = new String[lst.size()];
        lst.toArray(ar);
        return ar;
    }

    public static int find(List<?>[] as, Object a) {
        int i;
        int j;
        int max;
        List<?> current;

        for (i = 0; i < as.length; i++) {
            current = as[i];
            max = current.size();
            for (j = 0; j < max; j++) {
                if (current.get(j) == a) {
                    return i;
                }
            }
        }
        return -1;
    }

}
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

package com.oneandone.sushi.metadata.reflect;

import org.junit.Test;
import static org.junit.Assert.*;

import com.oneandone.sushi.fs.IO;
import com.oneandone.sushi.fs.Node;
import com.oneandone.sushi.fs.file.FileNode;
import com.oneandone.sushi.metadata.Type;

public class ReflectSchemaTest {
    @Test
    public void normal() {
        Type type;
        
        type = new ReflectSchema().type(IO.class);
        assertEquals("iO", type.getName());
    }
    
    @Test
    public void nodes() {
        new ReflectSchema().type(IO.class);
        new ReflectSchema().type(Node.class);
        new ReflectSchema().type(FileNode.class);
    }
}
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

package de.ui.sushi.fs.webdav;

import java.io.IOException;
import java.util.List;

import de.ui.sushi.TestProperties;
import de.ui.sushi.fs.Node;
import de.ui.sushi.fs.NodeTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class WebdavReadWriteFullTest extends NodeTest {
    static {
        WebdavFilesystem.wireLog(IO.guessProjectHome(WebdavNodeFullBase.class).getAbsolute() + "/target/webdav-readwrite.log");
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return TestProperties.getParameterList("webdav.readwrite.uri");
    }

    private final String uri;

    public WebdavReadWriteFullTest(String uri) {
        this.uri = uri;
    }

    @Override
    protected Node createWork() throws IOException {
        return IO.node(uri).deleteOpt().mkdir();
    }

    @Test
    public void attributeFile() throws IOException {
        WebdavNode node;

        node = (WebdavNode) work.join("file");
        try {
            node.getAttribute("foo");
            fail();
        } catch (WebdavException e) {
            // ok
        }
        node.writeBytes();
        attrib(node, "foo");
    }

    @Test
    public void attributeDir() throws IOException {
        WebdavNode node;

        node = (WebdavNode) work.join("dir");
        node.mkdir();
        attrib(node, "foo");
    }

    private void attrib(WebdavNode node, String name) throws IOException {
        WebdavNode second;

        assertNull(node.getAttribute(name));
        node.setAttribute(name, "bar");
        assertEquals("bar", node.getAttribute(name));
        node.setAttribute(name, "baz");
        assertEquals("baz", node.getAttribute(name));
        second = (WebdavNode) node.getParent().join("copy");
        node.copy(second);
        assertEquals("baz", node.getAttribute(name));
        assertNull(second.getAttribute(name));
        second = (WebdavNode) node.getParent().join("moved");
        node.move(second);
        assertEquals("baz", second.getAttribute(name));
    }
}

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

package de.ui.sushi.fs.webdav.methods;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import de.ui.sushi.fs.webdav.MovedException;
import de.ui.sushi.fs.webdav.StatusException;
import de.ui.sushi.fs.webdav.WebdavConnection;
import de.ui.sushi.fs.webdav.WebdavRoot;

public class MoveMethod extends WebdavMethod<Void> {
    public MoveMethod(WebdavRoot root, String path, String destination) {
        super(root, "MOVE", path);
        setRequestHeader("Destination", destination);
        setRequestHeader("Overwrite", "F");
    }
    
    @Override
    public Void processResponse(WebdavConnection conection, HttpResponse response) throws IOException {
    	switch (response.getStatusLine().getStatusCode()) {
    	case HttpStatus.SC_NO_CONTENT:
    	case HttpStatus.SC_CREATED:
    		return null;
    	case HttpStatus.SC_MOVED_PERMANENTLY:
    		throw new MovedException();
    	default:
        	throw new StatusException(response.getStatusLine());
        }
    }
}
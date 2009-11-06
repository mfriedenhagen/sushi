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

package de.ui.sushi.metadata.simpletypes;

import de.ui.sushi.metadata.Schema;
import de.ui.sushi.metadata.SimpleType;

public class CharacterType extends SimpleType {
    public CharacterType(Schema schema) {
        super(schema, Character.class, "char");
    }
    
    @Override
    public Object newInstance() {
        return 0;
    }

    @Override
    public String valueToString(Object obj) {
        return ((Character) obj).toString();
    }
    
    @Override
    public Object stringToValue(String str) {
        return str.charAt(0); // TODO
    }
}

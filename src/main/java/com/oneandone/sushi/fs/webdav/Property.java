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

package com.oneandone.sushi.fs.webdav;

import com.oneandone.sushi.xml.Dom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Property {
    public static Property fromXml(Element propertyElement) {
        Name name;
        Object value;
        List<?> content;
        
        name = Name.fromXml(propertyElement);
        content = getChildElementsOrTexts(propertyElement);
        switch (content.size()) {
            case 0:
            	value = null;
            	break;
            case 1:
                Node n = (Node) content.get(0);
                if (n instanceof Element) {
                    value = n;
                } else {
                    value = n.getNodeValue();
                }
                break;
            default:
                value = content;
                break;
        }
        return new Property(name, value);
    }

    private static List<Node> getChildElementsOrTexts(Node parent) {
        List<Node> content = new ArrayList<Node>();
        Node child;

        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            child = children.item(i);
            if ((child instanceof Element) || (child instanceof Text)) {
                content.add(child);
            }
        }
        return content;
    }

    private final Name name;
    private final Object value;

    public Property(Name name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hashCode = getName().hashCode();
        if (getValue() != null) {
            hashCode += getValue().hashCode();
        }
        return hashCode % Integer.MAX_VALUE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Property) {
            Property prop = (Property) obj;
            boolean equalName = getName().equals(prop.getName());
            boolean equalValue = (getValue() == null) ? prop.getValue() == null : getValue().equals(prop.getValue());
            return equalName && equalValue;
        }
        return false;
    }

    public Element addXml(Element parent) {
    	Document document = parent.getOwnerDocument();
        Element elem = getName().addXml(parent);
        Object value = getValue();
        if (value != null) {
            if (value instanceof Node) {
                Node n = document.importNode((Node)value, true);
                elem.appendChild(n);
            } else if (value instanceof Node[]) {
                for (int i = 0; i < ((Node[])value).length; i++) {
                    Node n = document.importNode(((Node[])value)[i], true);
                    elem.appendChild(n);
                }
            } else if (value instanceof Collection<?>) {
                for (Object entry : (Collection<?>) value) {
                    if (entry instanceof Node) {
                        Node n = document.importNode((Node)entry, true);
                        elem.appendChild(n);
                    } else {
                        Dom.addTextOpt(elem, entry.toString());
                    }
                }
            } else {
                Dom.addTextOpt(elem, value.toString());
            }
        }
        return elem;
    }

    public Name getName() {
        return name;
    }
}
/**
 * Copyright 2025 J&#246;rgen Lundgren
 * 
 * This file is part of SFV4J.
 * 
 * SFV4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SFV4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with SFV4J. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.sfv4j.file.openexr;

import java.util.Objects;

public abstract class Attribute {
	private final String name;
	private final String type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected Attribute(final String name, final String type) {
		this.name = Objects.requireNonNull(name, "name == null");
		this.type = Objects.requireNonNull(type, "type == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
}
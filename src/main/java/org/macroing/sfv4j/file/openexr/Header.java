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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Header {
	private List<Attribute> attributes;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Header() {
		this.attributes = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Attribute> getAttributes() {
		return new ArrayList<>(this.attributes);
	}
	
	public Optional<Attribute> getAttribute(final String name) {
		Objects.requireNonNull(name, "name == null");
		
		for(final Attribute attribute : this.attributes) {
			if(attribute.getName().equals(name)) {
				return Optional.of(attribute);
			}
		}
		
		return Optional.empty();
	}
	
	public boolean hasAttribute(final String name) {
		Objects.requireNonNull(name, "name == null");
		
		for(final Attribute attribute : this.attributes) {
			if(attribute.getName().equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasCompressionTypeNone() {
		return getCompressionType() == Constants.COMPRESSION_TYPE_NONE;
	}
	
	public int getCompressionType() {
		for(final Attribute attribute : this.attributes) {
			if(attribute instanceof CompressionAttribute) {
				final CompressionAttribute compressionAttribute = CompressionAttribute.class.cast(attribute);
				
				return compressionAttribute.getCompressionType();
			}
		}
		
		return Constants.COMPRESSION_TYPE_UNKNOWN;
	}
	
	public void addAttribute(final Attribute attribute) {
		this.attributes.add(Objects.requireNonNull(attribute, "attribute == null"));
	}
	
	public void removeAttribute(final Attribute attribute) {
		this.attributes.remove(Objects.requireNonNull(attribute, "attribute == null"));
	}
}
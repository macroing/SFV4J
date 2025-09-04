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
package org.macroing.sfv4j.file.csv;

import java.util.Objects;

public final class Cell {
	private final String string;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Cell(final String string) {
		this.string = Objects.requireNonNull(string, "string == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getString() {
		return this.string;
	}
	
	public boolean isBoolean() {
		return this.string.equals("false") || this.string.equals("true");
	}
	
	@SuppressWarnings("unused")
	public boolean isDouble() {
		try {
			Double.parseDouble(this.string);
			
			return true;
		} catch(final NumberFormatException e) {
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	public boolean isFloat() {
		try {
			Float.parseFloat(this.string);
			
			return true;
		} catch(final NumberFormatException e) {
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	public boolean isInt() {
		try {
			Integer.parseInt(this.string);
			
			return true;
		} catch(final NumberFormatException e) {
			return false;
		}
	}
}
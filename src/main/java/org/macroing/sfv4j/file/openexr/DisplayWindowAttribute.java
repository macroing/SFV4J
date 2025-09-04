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

public final class DisplayWindowAttribute extends Attribute {
	private final int xMax;
	private final int xMin;
	private final int yMax;
	private final int yMin;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public DisplayWindowAttribute(final int xMin, final int yMin, final int xMax, final int yMax) {
		super(Constants.ATTRIBUTE_NAME_DISPLAY_WINDOW, Constants.ATTRIBUTE_TYPE_BOX2I);
		
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("DisplayWindowAttribute(xMax = %s, xMin = %s, yMax = %s, yMin = %s)", Integer.toString(this.xMax), Integer.toString(this.xMin), Integer.toString(this.yMax), Integer.toString(this.yMin));
	}
	
	public int getHeight() {
		return this.yMax - this.yMin + 1;
	}
	
	public int getWidth() {
		return this.xMax - this.xMin + 1;
	}
	
	public int getXMax() {
		return this.xMax;
	}
	
	public int getXMin() {
		return this.xMin;
	}
	
	public int getYMax() {
		return this.yMax;
	}
	
	public int getYMin() {
		return this.yMin;
	}
}
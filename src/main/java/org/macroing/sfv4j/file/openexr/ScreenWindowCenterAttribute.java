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

public final class ScreenWindowCenterAttribute extends Attribute {
	private final float x;
	private final float y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ScreenWindowCenterAttribute(final float x, final float y) {
		super(Constants.ATTRIBUTE_NAME_SCREEN_WINDOW_CENTER, Constants.ATTRIBUTE_TYPE_V2F);
		
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("ScreenWindowCenterAttribute(x = %s, y = %s)", Float.toString(this.x), Float.toString(this.y));
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}
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

public final class ChromaticitiesAttribute extends Attribute {
	private final float blueX;
	private final float blueY;
	private final float greenX;
	private final float greenY;
	private final float redX;
	private final float redY;
	private final float whiteX;
	private final float whiteY;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ChromaticitiesAttribute(final float redX, final float redY, final float greenX, final float greenY, final float blueX, final float blueY, final float whiteX, final float whiteY) {
		super(Constants.ATTRIBUTE_NAME_CHROMATICITIES, Constants.ATTRIBUTE_TYPE_CHROMATICITIES);
		
		this.redX = redX;
		this.redY = redY;
		this.greenX = greenX;
		this.greenY = greenY;
		this.blueX = blueX;
		this.blueY = blueY;
		this.whiteX = whiteX;
		this.whiteY = whiteY;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return String.format("ChromaticitiesAttribute(blueX = %s, blueY = %s, greenX = %s, greenY = %s, redX = %s, redY = %s, whiteX = %s, whiteY = %s)", Float.toString(this.blueX), Float.toString(this.blueY), Float.toString(this.greenX), Float.toString(this.greenY), Float.toString(this.redX), Float.toString(this.redY), Float.toString(this.whiteX), Float.toString(this.whiteY));
	}
	
	public float getBlueX() {
		return this.blueX;
	}
	
	public float getBlueY() {
		return this.blueY;
	}
	
	public float getGreenX() {
		return this.greenX;
	}
	
	public float getGreenY() {
		return this.greenY;
	}
	
	public float getRedX() {
		return this.redX;
	}
	
	public float getRedY() {
		return this.redY;
	}
	
	public float getWhiteX() {
		return this.whiteX;
	}
	
	public float getWhiteY() {
		return this.whiteY;
	}
}
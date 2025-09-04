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

public final class Constants {
	public static final String ATTRIBUTE_NAME_CAP_DATE = "capDate";
	public static final String ATTRIBUTE_NAME_CHANNELS = "channels";
	public static final String ATTRIBUTE_NAME_CHROMATICITIES = "chromaticities";
	public static final String ATTRIBUTE_NAME_COMPRESSION = "compression";
	public static final String ATTRIBUTE_NAME_DATA_WINDOW = "dataWindow";
	public static final String ATTRIBUTE_NAME_DISPLAY_WINDOW = "displayWindow";
	public static final String ATTRIBUTE_NAME_EXIF_IMAGE_HISTORY = "Exif:ImageHistory";
	public static final String ATTRIBUTE_NAME_GENERATED_BY = "generatedBy";
	public static final String ATTRIBUTE_NAME_LINE_ORDER = "lineOrder";
	public static final String ATTRIBUTE_NAME_NAME = "name";
	public static final String ATTRIBUTE_NAME_ORIENTATION = "Orientation";
	public static final String ATTRIBUTE_NAME_ORIGINAL_DATA_WINDOW = "originalDataWindow";
	public static final String ATTRIBUTE_NAME_PIXEL_ASPECT_RATIO = "pixelAspectRatio";
	public static final String ATTRIBUTE_NAME_SCREEN_WINDOW_CENTER = "screenWindowCenter";
	public static final String ATTRIBUTE_NAME_SCREEN_WINDOW_WIDTH = "screenWindowWidth";
	public static final String ATTRIBUTE_NAME_SOFTWARE = "Software";
	public static final String ATTRIBUTE_NAME_TYPE = "type";
	public static final String ATTRIBUTE_NAME_VERSION = "version";
	public static final String ATTRIBUTE_TYPE_BOX2I = "box2i";
	public static final String ATTRIBUTE_TYPE_CHLIST = "chlist";
	public static final String ATTRIBUTE_TYPE_CHROMATICITIES = "chromaticities";
	public static final String ATTRIBUTE_TYPE_COMPRESSION = "compression";
	public static final String ATTRIBUTE_TYPE_FLOAT = "float";
	public static final String ATTRIBUTE_TYPE_INT = "int";
	public static final String ATTRIBUTE_TYPE_LINE_ORDER = "lineOrder";
	public static final String ATTRIBUTE_TYPE_STRING = "string";
	public static final String ATTRIBUTE_TYPE_V2F = "v2f";
	public static final String PIXEL_TYPE_NAME_FLOAT = "FLOAT";
	public static final String PIXEL_TYPE_NAME_HALF = "HALF";
	public static final String PIXEL_TYPE_NAME_U_INT = "UINT";
	public static final String PIXEL_TYPE_NAME_UNKNOWN = "UNKNOWN";
	public static final int COMPRESSION_TYPE_NONE = 0;
	public static final int COMPRESSION_TYPE_PIZ = 4;
	public static final int COMPRESSION_TYPE_R_L_E = 1;
	public static final int COMPRESSION_TYPE_UNKNOWN = -1;
	public static final int COMPRESSION_TYPE_ZIP = 3;
	public static final int COMPRESSION_TYPE_ZIPS = 2;
	public static final int MAGIC_NUMBER = 20000630;
	public static final int PIXEL_TYPE_FLOAT = 2;
	public static final int PIXEL_TYPE_HALF = 1;
	public static final int PIXEL_TYPE_U_INT = 0;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Constants() {
		
	}
}
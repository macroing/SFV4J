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
package org.macroing.sfv4j.java.io;

import java.io.File;
import java.io.IOException;

public final class Files {
	private Files() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getFileExtension(final File file) {
		final String fileName = file.getName();
		
		final int lastIndexOf = fileName.lastIndexOf(".");
		
		if(lastIndexOf == -1 || lastIndexOf + 1 == fileName.length()) {
			return "";
		}
		
		return fileName.substring(lastIndexOf + 1);
	}
	
	public static String getFileName(final File file) {
		return file.getName();
	}
	
	public static String getFileNameWithoutExtension(final File file) {
		final String fileName = file.getName();
		
		final int lastIndexOf = fileName.lastIndexOf(".");
		
		if(lastIndexOf == -1) {
			return fileName;
		}
		
		if(lastIndexOf == 0) {
			return "";
		}
		
		return fileName.substring(0, lastIndexOf);
	}
	
	@SuppressWarnings("unused")
	public static String getFilePath(final File file) {
		try {
			return file.getCanonicalPath();
		} catch(final IOException e) {
			return file.getAbsolutePath();
		}
	}
}
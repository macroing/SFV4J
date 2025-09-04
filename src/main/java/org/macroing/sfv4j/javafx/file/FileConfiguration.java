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
package org.macroing.sfv4j.javafx.file;

import java.io.File;
import java.util.Objects;

import org.macroing.sfv4j.java.io.Files;

public final class FileConfiguration {
	public static final String CHARACTER_ENCODING_I_S_O_8859_1 = "ISO-8859-1";
	public static final String CHARACTER_ENCODING_U_T_F_8 = "UTF-8";
	public static final String CHARACTER_ENCODING_WINDOWS_1252 = "Windows-1252";
	public static final String DELIMITER_COMMA = ",";
	public static final String DELIMITER_COMMA_NAME = "Comma";
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_SEMICOLON_NAME = "Semicolon";
	public static final String DELIMITER_SPACE = " ";
	public static final String DELIMITER_SPACE_NAME = "Space";
	public static final String DELIMITER_TAB = "\t";
	public static final String DELIMITER_TAB_NAME = "Tab";
	public static final String FILE_FORMAT_BINARY = "Binary";
	public static final String FILE_FORMAT_C_S_V = "CSV";
	public static final String FILE_FORMAT_OPEN_E_X_R = "OpenEXR";
	public static final String FILE_FORMAT_PLAIN_TEXT = "Plain Text";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private File file;
	private String characterEncoding;
	private String delimiter;
	private String fileExtension;
	private String fileFormat;
	private String fileName;
	private String filePath;
	private boolean hasHeader;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public FileConfiguration(final File file) {
		this.file = Objects.requireNonNull(file, "file == null");
		this.characterEncoding = CHARACTER_ENCODING_U_T_F_8;
		this.delimiter = DELIMITER_COMMA;
		this.fileExtension = Files.getFileExtension(this.file);
		this.fileFormat = FILE_FORMAT_PLAIN_TEXT;
		this.fileName = Files.getFileName(this.file);
		this.filePath = Files.getFilePath(this.file);
		this.hasHeader = false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public File getFile() {
		return this.file;
	}
	
	public String getCharacterEncoding() {
		return this.characterEncoding;
	}
	
	public String getDelimiter() {
		return this.delimiter;
	}
	
	public String getFileExtension() {
		return this.fileExtension;
	}
	
	public String getFileFormat() {
		return this.fileFormat;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public boolean hasHeader() {
		return this.hasHeader;
	}
	
	public boolean isFileFormatBinary() {
		return this.fileFormat.equals(FILE_FORMAT_BINARY);
	}
	
	public boolean isFileFormatCSV() {
		return this.fileFormat.equals(FILE_FORMAT_C_S_V);
	}
	
	public boolean isFileFormatOpenEXR() {
		return this.fileFormat.equals(FILE_FORMAT_OPEN_E_X_R);
	}
	
	public boolean isFileFormatPlainText() {
		return this.fileFormat.equals(FILE_FORMAT_PLAIN_TEXT);
	}
	
	public void setCharacterEncoding(final String characterEncoding) {
		this.characterEncoding = Objects.requireNonNull(characterEncoding, "characterEncoding == null");
	}
	
	public void setDelimiter(final String delimiter) {
		this.delimiter = Objects.requireNonNull(delimiter, "delimiter == null");
	}
	
	public void setFileFormat(final String fileFormat) {
		this.fileFormat = Objects.requireNonNull(fileFormat, "fileFormat == null");
	}
	
	public void setHeader(final boolean hasHeader) {
		this.hasHeader = hasHeader;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getDelimiterByName(final String delimiterName) {
		Objects.requireNonNull(delimiterName, "delimiterName == null");
		
		switch(delimiterName) {
			case DELIMITER_COMMA_NAME:
				return DELIMITER_COMMA;
			case DELIMITER_SEMICOLON_NAME:
				return DELIMITER_SEMICOLON;
			case DELIMITER_SPACE_NAME:
				return DELIMITER_SPACE;
			case DELIMITER_TAB_NAME:
				return DELIMITER_TAB;
			default:
				throw new IllegalArgumentException("Unsupported delimiter name!");
		}
	}
}
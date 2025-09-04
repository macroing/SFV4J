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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;

public final class CSVFileReader {
	public CSVFileReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings({ "static-method", "unused" })
	public Optional<CSVFile> readCSVFile(final File file, final String characterEncoding, final String delimiter, final boolean hasHeader) {
		Objects.requireNonNull(file, "file == null");
		Objects.requireNonNull(characterEncoding, "characterEncoding == null");
		Objects.requireNonNull(delimiter, "delimiter == null");
		
		try(final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), characterEncoding))) {
			final CSVFile cSVFile = new CSVFile(hasHeader);
			
			for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				final Row row = new Row();
				
				final String[] cellStrings = line.split(delimiter);
				
				for(final String cellString : cellStrings) {
					row.addCell(new Cell(cellString));
				}
				
				cSVFile.addRow(row);
			}
			
			return Optional.of(cSVFile);
		} catch(final IOException e) {
			return Optional.empty();
		}
	}
}
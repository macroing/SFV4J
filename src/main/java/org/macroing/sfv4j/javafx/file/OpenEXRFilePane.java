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

import java.util.Objects;

import org.macroing.sfv4j.file.openexr.OpenEXRFile;
import org.macroing.sfv4j.file.openexr.OpenEXRFileReader;

import javafx.scene.layout.BorderPane;

public final class OpenEXRFilePane extends BorderPane {
	private final FileConfiguration fileConfiguration;
	private final OpenEXRFile openEXRFile;
	private final OpenEXRFileTreeView openEXRFileTreeView;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public OpenEXRFilePane(final FileConfiguration fileConfiguration) {
		this.fileConfiguration = Objects.requireNonNull(fileConfiguration, "fileConfiguration == null");
		this.openEXRFile = new OpenEXRFileReader().readOpenEXRFile(this.fileConfiguration.getFile()).orElse(null);
		this.openEXRFileTreeView = this.openEXRFile != null ? new OpenEXRFileTreeView(this.openEXRFile) : null;
		
		if(this.openEXRFileTreeView != null) {
			setLeft(this.openEXRFileTreeView);
		}
	}
}
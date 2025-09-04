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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public final class FilePropertyViewer extends ScrollPane {
	private final FileConfiguration fileConfiguration;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public FilePropertyViewer(final FileConfiguration fileConfiguration) {
		this.fileConfiguration = Objects.requireNonNull(fileConfiguration, "fileConfiguration == null");
		
		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		final LocalDateTime lastModified = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.fileConfiguration.getFile().lastModified()), ZoneId.systemDefault());
		
		final long lengthBytes = this.fileConfiguration.getFile().length();
		final long lengthKBytes = lengthBytes / 1024L;
		final long lengthMBytes = lengthKBytes / 1024L;
		final long lengthGBytes = lengthMBytes / 1024L;
		
		final Label labelFilePathTitle = new Label("File path:");
		final Label labelFilePathValue = new Label(this.fileConfiguration.getFilePath());
		
		final Label labelFileNameTitle = new Label("File name:");
		final Label labelFileNameValue = new Label(this.fileConfiguration.getFileName());
		
		final Label labelFileExtensionTitle = new Label("File extension:");
		final Label labelFileExtensionValue = new Label(this.fileConfiguration.getFileExtension());
		
		final Label labelLastModifiedTitle = new Label("Last modified:");
		final Label labelLastModifiedValue = new Label(lastModified.format(dateTimeFormatter));
		
		final Label labelLengthTitle = new Label("Length:");
		final Label labelLengthValue = new Label(lengthGBytes > 0 ? lengthGBytes + " GB" : lengthMBytes > 0 ? lengthMBytes + " MB" : lengthKBytes > 0 ? lengthKBytes + " kB" : lengthBytes + " b");
		
		labelFilePathTitle.setStyle("-fx-font-weight: bold;");
		labelFileNameTitle.setStyle("-fx-font-weight: bold;");
		labelFileExtensionTitle.setStyle("-fx-font-weight: bold;");
		labelLastModifiedTitle.setStyle("-fx-font-weight: bold;");
		labelLengthTitle.setStyle("-fx-font-weight: bold;");
		
		final GridPane gridPane = new GridPane();
		
		gridPane.add(labelFilePathTitle, 0, 0);
		gridPane.add(labelFilePathValue, 1, 0);
		
		gridPane.add(labelFileNameTitle, 0, 1);
		gridPane.add(labelFileNameValue, 1, 1);
		
		gridPane.add(labelFileExtensionTitle, 0, 2);
		gridPane.add(labelFileExtensionValue, 1, 2);
		
		gridPane.add(labelLastModifiedTitle, 0, 3);
		gridPane.add(labelLastModifiedValue, 1, 3);
		
		gridPane.add(labelLengthTitle, 0, 4);
		gridPane.add(labelLengthValue, 1, 4);
		
		gridPane.setHgap(10.0F);
		gridPane.setVgap(10.0F);
		
		setContent(gridPane);
		setMaxWidth(300.0D);
		setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
	}
}
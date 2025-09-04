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

import java.util.List;
import java.util.Objects;

import org.macroing.sfv4j.file.csv.CSVFile;
import org.macroing.sfv4j.file.csv.Cell;
import org.macroing.sfv4j.file.csv.Row;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public final class CSVFileTableView extends TableView<Row> {
	public CSVFileTableView(final CSVFile cSVFile) {
		Objects.requireNonNull(cSVFile, "cSVFile == null");
		
		final List<Row> rows = cSVFile.getRows();
		
		final ObservableList<Row> items = FXCollections.observableArrayList(cSVFile.hasHeader() ? rows.subList(1, rows.size()) : rows);
		
		setItems(items);
		
		for(final Row row : cSVFile.getRows()) {
			final List<Cell> cells = row.getCells();
			
			for(int i = 0; i < cells.size(); i++) {
				final int index = i;
				
				final
				TableColumn<Row, String> tableColumn = new TableColumn<>(cSVFile.hasHeader() ? cells.get(i).getString() : "Column #" + (i + 1));
				tableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().hasCell(index) ? p.getValue().getCell(index).get().getString() : ""));
				
				getColumns().add(tableColumn);
			}
			
			break;
		}
	}
}
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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Objects;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableListBase;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.util.Callback;

public final class FileHexViewer extends TableView<Integer> {
	private static final char EMP = '\u0020';
	private static final char PNT = '\u00B7';
	private static final char PRIV = '\uE000';
	private static final char[] CONTROL_CHARACTERS = {'\u0020', '\u263A', '\u263B', '\u2665', '\u2666', '\u2663', '\u2660', '\u2022', '\u25D8', '\u25CB', '\u25D9', '\u2642', '\u2640', '\u266A', '\u266B', '\u263C', '\u25BA', '\u25C4', '\u2195', '\u203C', '\u00B6', '\u00A7', '\u25AC', '\u21A8', '\u2191', '\u2193', '\u2192', '\u2190', '\u221F', '\u2194', '\u25B2', '\u25BC'};
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final FileConfiguration fileConfiguration;
	private final RandomAccessFile randomAccessFile;
	private final StringBuilder stringBuilder;
	private final byte[] bytes;
	private final char[] charTable;
	private final int size;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public FileHexViewer(final FileConfiguration fileConfiguration) {
		this.fileConfiguration = Objects.requireNonNull(fileConfiguration, "fileConfiguration == null");
		this.randomAccessFile = doCreateRandomAccessFile(this.fileConfiguration.getFile());
		this.stringBuilder = new StringBuilder();
		this.bytes = new byte[16];
		this.charTable = new char[224];
		this.size = this.randomAccessFile != null ? doGetSize(this.randomAccessFile) : 0;
		
		doInitializeCharTable(doGetCharset(fileConfiguration.getCharacterEncoding()));
		
		setItems(new ObservableListBase<Integer>() {
			private Integer value = Integer.valueOf(-1);
			
			////////////////////////////////////////////////////////////////////////////////////////////////////
			
			@Override
			public Integer get(final int index) {
				if(index < 0 || index >= size()) {
					throw new IndexOutOfBoundsException();
				}
				
				if(index != this.value.intValue()) {
					this.value = Integer.valueOf(index);
				}
				
				return this.value;
			}
			
			@Override
			public int size() {
				return FileHexViewer.this.size;
			}
		});
		
		final TableColumn<Integer, String> tableColumn0 = new TableColumn<>("Offset");
		final TableColumn<Integer, String> tableColumn1 = new TableColumn<>("Byte Block #1");
		final TableColumn<Integer, String> tableColumn2 = new TableColumn<>("Byte Block #2");
		final TableColumn<Integer, String> tableColumn3 = new TableColumn<>("Text");
		
		tableColumn0.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(doCreateItem0(cellData.getValue().intValue())));
		tableColumn1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(doCreateItem1(cellData.getValue().intValue())));
		tableColumn2.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(doCreateItem2(cellData.getValue().intValue())));
		tableColumn3.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(doCreateItem3(cellData.getValue().intValue())));
		
		final Font font = Font.font("Monospaced");
		
		final Callback<TableColumn<Integer, String>, TableCell<Integer, String>> tableColumnCellFactory0 = tableColumn0.getCellFactory();
		final Callback<TableColumn<Integer, String>, TableCell<Integer, String>> tableColumnCellFactory1 = tableColumn1.getCellFactory();
		final Callback<TableColumn<Integer, String>, TableCell<Integer, String>> tableColumnCellFactory2 = tableColumn2.getCellFactory();
		final Callback<TableColumn<Integer, String>, TableCell<Integer, String>> tableColumnCellFactory3 = tableColumn3.getCellFactory();
		
		tableColumn0.setCellFactory(column -> doCreateTableCell(font, tableColumn0, tableColumnCellFactory0));
		tableColumn1.setCellFactory(column -> doCreateTableCell(font, tableColumn1, tableColumnCellFactory1));
		tableColumn2.setCellFactory(column -> doCreateTableCell(font, tableColumn2, tableColumnCellFactory2));
		tableColumn3.setCellFactory(column -> doCreateTableCell(font, tableColumn3, tableColumnCellFactory3));
		
		getColumns().add(tableColumn0);
		getColumns().add(tableColumn1);
		getColumns().add(tableColumn2);
		getColumns().add(tableColumn3);
		
		getSelectionModel().selectFirst();
		
		sortPolicyProperty().set(t -> Boolean.FALSE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	public void handleExitRequest() {
		if(this.randomAccessFile != null) {
			try {
				this.randomAccessFile.close();
			} catch(final IOException e) {
//				Do nothing for now.
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private String doCreateItem0(final int value) {
		this.stringBuilder.setLength(0);
		
		int length = 0;
		
		try { 
			this.randomAccessFile.seek(value * 16);
			
			length = this.randomAccessFile.read(this.bytes);
		} catch(final IOException e) {
			return null;
		}
		
		this.stringBuilder.append("0x");
		
		for(int i = 28; i >= 0; i -= 4) {
			this.stringBuilder.append(HEX_DIGITS[0x0F & value >>> i]);
		}
		
		return this.stringBuilder.toString();
	}
	
	@SuppressWarnings("unused")
	private String doCreateItem1(final int value) {
		this.stringBuilder.setLength(0);
		
		int length = 0;
		
		try { 
			this.randomAccessFile.seek(value * 16);
			
			length = this.randomAccessFile.read(this.bytes);
		} catch(final IOException e) {
			return null;
		}
		
		for(int i = 0; i < 8; i++) {
			if(i > 0) {
				this.stringBuilder.append(EMP);
			}
			
			this.stringBuilder.append(HEX_DIGITS[0x0F & this.bytes[i] >> 4]);
			this.stringBuilder.append(HEX_DIGITS[0x0F & this.bytes[i]]);
		}
		
		return this.stringBuilder.toString();
	}
	
	@SuppressWarnings("unused")
	private String doCreateItem2(final int value) {
		this.stringBuilder.setLength(0);
		
		int length = 0;
		
		try { 
			this.randomAccessFile.seek(value * 16);
			
			length = this.randomAccessFile.read(this.bytes);
		} catch(final IOException e) {
			return null;
		}
		
		for(int i = 8; i < length; i++) {
			if(i > 8) {
				this.stringBuilder.append(EMP);
			}
			
			this.stringBuilder.append(HEX_DIGITS[0x0F & this.bytes[i] >> 4]);
			this.stringBuilder.append(HEX_DIGITS[0x0F & this.bytes[i]]);
		}
		
		return this.stringBuilder.toString();
	}
	
	@SuppressWarnings("unused")
	private String doCreateItem3(final int value) {
		this.stringBuilder.setLength(0);
		
		int length = 0;
		
		try { 
			this.randomAccessFile.seek(value * 16);
			
			length = this.randomAccessFile.read(this.bytes);
		} catch(final IOException e) {
			return null;
		}
		
		for(int i = 0; i < 16; i++) {
			if(i >= length) {
				this.stringBuilder.append(EMP);
				
				continue;
			}
			
			final int code = 0xFF & this.bytes[i];
			
			if(code >= 0x00 && code < 0x20) {
				this.stringBuilder.append(CONTROL_CHARACTERS[code]);
				
				continue;
			}
			
			final char uc = this.charTable[code - 0x20];
			
			if(uc >= '\u0000' && uc < '\u0020') {
				this.stringBuilder.append(CONTROL_CHARACTERS[uc]);
			} else if(uc == '\u0085' || uc == '\u2028' || uc == '\u2029') {
				this.stringBuilder.append(CONTROL_CHARACTERS[0x0A]);
			} else if(uc == PRIV) {
				this.stringBuilder.append(PNT);
			} else {
				this.stringBuilder.append(uc);
			}
		}
		
		return this.stringBuilder.toString();
	}
	
	@SuppressWarnings("unused")
	private boolean doInitializeCharTable(final Charset charset) {
		CharsetDecoder charsetDecoder = charset.newDecoder();
		
		final boolean isSuitable = charsetDecoder.maxCharsPerByte() == 1.0F && charsetDecoder.averageCharsPerByte() == 1.0F;
		
		if(!isSuitable) {
			charsetDecoder = Charset.forName("Windows-1252").newDecoder();
		}
		
		final ByteBuffer byteBuffer = ByteBuffer.allocate(1);
		
		final CharBuffer charBuffer = CharBuffer.allocate(1);
		
		for(int i = 0; i < this.charTable.length; i++) {
			try {
				byteBuffer.put(0, (byte)(i + 0x20));
				byteBuffer.position(0);
				
				charBuffer.position(0);
				
				charsetDecoder.reset();
				
				if(charsetDecoder.decode(byteBuffer, charBuffer, true).isError()) {
					this.charTable[i] = PRIV;
				} else {
					this.charTable[i] = charBuffer.get(0);
				}
			} catch(final CoderMalfunctionError | IllegalStateException | IndexOutOfBoundsException | ReadOnlyBufferException e) {
				this.charTable[i] = PRIV;
			}
		}
		
		return isSuitable;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Charset doGetCharset(final String charsetName) {
		try {
			return Charset.forName(charsetName);
		} catch(final IllegalCharsetNameException | UnsupportedCharsetException e) {
			return Charset.defaultCharset();
		}
	}
	
	@SuppressWarnings("unused")
	private static RandomAccessFile doCreateRandomAccessFile(final File file) {
		try {
			return new RandomAccessFile(file, "rw");
		} catch(final IOException e) {
			return null;
		}
	}
	
	private static TableCell<Integer, String> doCreateTableCell(final Font font, final TableColumn<Integer, String> tableColumn, final Callback<TableColumn<Integer, String>, TableCell<Integer, String>> tableColumnCellFactory) {
		final
		TableCell<Integer, String> tableCell = tableColumnCellFactory.call(tableColumn);
		tableCell.setFont(font);
		
		return tableCell;
	}
	
	@SuppressWarnings("unused")
	private static int doGetSize(final RandomAccessFile randomAccessFile) {
		try {
			return (int)((randomAccessFile.length() - 1L) / 16L + 1L);
		} catch(final IOException e) {
			return 0;
		}
	}
}
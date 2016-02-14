package com.tempestasludi.java.p17_webProtocols.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Header represents the header of a HTTP request.
 * 
 * @author Arnoud van der Leer
 */
public class Header {
	/**
	 * The HTTP request-line.
	 */
	private HeaderLine headerLine;

	/**
	 * The fields of the header.
	 */
	private List<HeaderField> fields = new ArrayList<HeaderField>();
	 
	/**
	 * Class constructor.
	 */
	public Header() {
		this(new ResponseLine("HTTP/1.1", "200", "OK"));
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param headerLine the HTTP header line
	 */
	public Header(HeaderLine headerLine) {
		this.headerLine = headerLine;
		if (headerLine instanceof ResponseLine) {
			this.addField(new HeaderField("Connection", "close"));
		}
	}

	/**
	 * Class constructor.
	 * 
	 * @param headerLine the HTTP request-line
	 * @param headers the fields of the header
	 */
	public Header(RequestLine headerLine, List<HeaderField> fields) {
		this.headerLine = headerLine;
		for(int i=0; i<fields.size();i++){
			this.addField(fields.get(i));
		}
	}

	/**
	 * Gets the header fields.
	 * 
	 * @return the header fields
	 */
	public HeaderField[] getFields(){
		HeaderField[] fieldsArray = new HeaderField[this.fields.size()];
		this.fields.toArray(fieldsArray);
		return fieldsArray;
	}

	/**
	 * Gets a field with a certain name.
	 * 
	 * @param name the name of the field to get
	 * @return if found, the field with the specified name, otherwise null
	 */
	public HeaderField getField(String name){
		for (int i = 0; i < this.fields.size(); i++) {
			if (this.fields.get(i).getName().equals(name)) {
				return this.fields.get(i);
			}
		}
		return null;
	}

	/**
	 * Gets the header line.
	 * 
	 * @return the header line
	 */
	public HeaderLine getHeaderLine(){
		return this.headerLine;
	}

	/**
	 * Adds a field to the header fields.
	 * 
	 * @param field the field to add
	 */
	public void addField(HeaderField field){
		HeaderField existing = this.getField(field.getName());
		if (existing == null) {
			this.fields.add(field);
		} else {
			existing.setValue(field.getValue());
		}
	}

	/**
	 * Changes the header line.
	 * 
	 * @param headerLine the header line
	 */
	public void setHeaderLine(HeaderLine headerLine){
		this.headerLine = headerLine;
	}

	/**
	 * Merges this header with another one.
	 * 
	 * Replaces the requestLine, adds/overwrites all headers.
	 * 
	 * @param header the header to merge with
	 */
	public void merge(Header header){
		if (header.getHeaderLine() != null) {
			this.headerLine = header.getHeaderLine();
		}
		HeaderField[] newFields = header.getFields();
		for (int i = 0; i < newFields.length; i++) {
			this.addField(newFields[i]);
		}
	}
	
	/**
	 * Generates a HTTP header string.
	 * 
	 * @return the HTTP header string
	 */
	@Override
	public String toString(){
		String result = this.headerLine.toString();
		for (int i = 0; i < this.fields.size(); i++) {
			result += this.fields.get(i).toString();
		}
		return result;
	}
	
	/**
	 * Equals method to compare object with this instance
	 * @param other Object to compare this instance to
	 * @return true if equals and false if not
	 */
	@Override
	public boolean equals(Object other){
		if(other instanceof Header){
			Header that = (Header) other;
			
			return this.getHeaderLine().equals(that.getHeaderLine());
		}
		
		return false;
	}

}

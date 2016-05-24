package com.tempestasludi.java.p17_webProtocols.http;

/**
 * @author Arnoud van der Leer
 * 
 *         HeaderField represents a field in the header of an HTTP message
 */
public class HeaderField {

	/**
	 * The name of the field.
	 */
	private String name;

	/**
	 * The value of the field.
	 */
	private String value;

	/**
	 * Constructor, reads the field from a line.
	 * 
	 * @param line
	 *            a line from the HTTP request header
	 */
	public HeaderField(String line) {
		String[] parts = line.split(":");
		name = parts[0].trim();
		value = parts[1].trim();
	}

	/**
	 * Constructor, takes the name and value as parameters.
	 * 
	 * @param name
	 *            the name of the field
	 * @param value
	 *            the value of the field
	 */
	public HeaderField(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Gets the name of the field.
	 * 
	 * @return the name of the field
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the value of the field.
	 * 
	 * @return the value of the field
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Changes the name of the field.
	 * 
	 * @param name
	 *            the name of the field
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Changes the value of the field.
	 * 
	 * @param value
	 *            the value of the field
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.name + ": " + this.value + "\r\n";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof HeaderField) {
			HeaderField that = (HeaderField) other;

			return this.getName().equals(that.getName()) && this.getValue().equals(that.getValue());
		}
		return false;
	}
}

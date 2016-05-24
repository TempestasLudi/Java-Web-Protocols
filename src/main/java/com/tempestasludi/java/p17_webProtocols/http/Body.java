package com.tempestasludi.java.p17_webProtocols.http;

import java.util.Arrays;

/**
 * Body represents the body of an HTTP message.
 * 
 * @author Arnoud van der Leer
 */
public class Body {

	/**
	 * The bytes composing the content of the body.
	 */
	private byte[] content;

	/**
	 * Class constructor.
	 * 
	 * @param content
	 *            a string representing the content of the body
	 */
	public Body(String content) {
		if (content != null) {
			this.content = content.getBytes();
		} else {
			this.content = null;
		}
	}

	/**
	 * Class constructor.
	 * 
	 * @param content
	 *            the bytes composing the content of the body
	 */
	public Body(byte[] content) {
		this.content = Arrays.copyOf(content, content.length);
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContentString() {
		return new String(this.content);
	}
	
	public byte[] getContent() {
		return Arrays.copyOf(this.content, this.content.length);
	}

	/**
	 * Changes the content.
	 * 
	 * @param content
	 *            a string representing the content to change to
	 */
	public void setContent(String content) {
		this.content = content.getBytes();
	}

	/**
	 * Changes the content bytes.
	 * 
	 * @param content
	 *            the content to change to
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * Represents the body by a string.
	 * 
	 * @return a string representation of the body
	 */
	@Override
	public String toString() {
		return new String(this.content);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] toBytes() {
		return this.content;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Body) {
			Body that = (Body) other;
			return Arrays.equals(this.content, that.getContent());
		}
		return false;

	}

}

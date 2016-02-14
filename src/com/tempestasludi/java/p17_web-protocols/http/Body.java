package com.tempestasludi.java.P12_site.http;

/**
 * Body represents an HTTP message body.
 * 
 * @author Arnoud van der Leer
 */
public class Body {

	/**
	 * The content of the body.
	 */
	private byte[] content;

	/**
	 * Class constructor.
	 * 
	 * @param content the content of the body
	 */
	public Body(String content) {
		this.content = content.getBytes();
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContent(){
		return new String(this.content);
	}

	/**
	 * Changes the content.
	 * 
	 * @param content the content
	 */
	public void setContent(String content){
		this.content = content.getBytes();
	}

	/**
	 * Changes the content.
	 * 
	 * @param content the content
	 */
	public void setContent(byte[] content){
		this.content = content;
	}

	/**
	 * Represents the body by a string.
	 * 
	 * @return a string representation of the body
	 */
	@Override
	public String toString(){
		return new String(this.content);
	}

	/**
	 * Represents the body by a byte array.
	 * 
	 * @return a string representation of the body
	 */
	public byte[] toBytes(){
		return this.content;
	}
	
	/**
	 * Equals method to compare object with this instance
	 */
	@Override
	public boolean equals(Object other){
		if(other instanceof Body){
			Body that = (Body) other;
			return this.getContent().equals(that.getContent());
		}
		return false;
		
	}

}

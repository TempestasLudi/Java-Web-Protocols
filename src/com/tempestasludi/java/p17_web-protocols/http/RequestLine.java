package com.tempestasludi.java.P12_site.http;

/**
 * RequestLine represents the request-line of an HTTP request.
 * 
 * @author Arnoud van der Leer
 */
public class RequestLine implements HeaderLine {
	/**
	 * The request method.
	 */
	private String method;

	/**
	 * The request URI.
	 */
	private String URI;

	/**
	 * The HTTP version.
	 */
	private String version;

	/**
	 * Class constructor, reads the data from a line from a HTTP request header.
	 * 
	 * @param line the line from the HTTP request header
	 */
	public RequestLine(String line) {
		String[] parts = line.split(" ");
		this.method = parts[0].trim().toUpperCase();
		this.URI = parts[1].trim();
		this.version = parts[2].trim();
	}

	/**
	 * Class constructor, takes the data as params.
	 * 
	 * @param method the request method
	 * @param URI the request URI
	 * @param version the HTTP version
	 */
	public RequestLine(String method, String URI, String version) {
		this.method = method;
		this.URI = URI;
		this.version = version;
	}

	/**
	 * Gets the request method.
	 * 
	 * @return the request method
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * Gets the request URI.
	 * 
	 * @return the request URI
	 */
	public String getUri() {
		return this.URI;
	}

	/**
	 * Gets the HTTP version.
	 * 
	 * @return the HTTP version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * Changes the request method.
	 * 
	 * @return the request method
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * Changes the request URI.
	 * 
	 * @param URI the request URI
	 */
	public void setUri(String URI) {
		this.URI = URI;
	}
	
	/**
	 * Changes the HTTP version.
	 * 
	 * @param version the HTTP version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Generates the request-line in string format.
	 * 
	 * @return the request-line in string format
	 */
	@Override
	public String toString(){
		return this.method + " " + this.URI + " " + this.version + "\r\n";
	}
	
	
	/**
	 * Compares two objects to see if they are equal
	 * @param other Object to compare this instance to
	 * @return true if equal false if not
	 */
	@Override
	public boolean equals(Object other) {
		if(other instanceof RequestLine){
			RequestLine that = (RequestLine) other;
			return this.getMethod().equals(that.getMethod()) && 
					this.getUri().equals(that.getUri()) && 
					this.getVersion().equals(that.getVersion());
		}
		
		return false;
	}
}

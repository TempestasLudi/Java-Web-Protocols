package com.tempestasludi.java.p17_webProtocols.http;

/**
 * ResponseLine represents the response line of an HTTP response.
 * 
 * @author Arnoud van der Leer
 */
public class ResponseLine implements HeaderLine {

	/**
	 * The HTTP version.
	 */
	private String version;

	/**
	 * The response code.
	 */
	private String code;

	/**
	 * The response status.
	 */
	private String status;

	/**
	 * Class constructor, reads the data from a line from a HTTP response
	 * header.
	 * 
	 * @param line
	 *            the line from the HTTP response header
	 */
	public ResponseLine(String line) {
		String[] parts = line.split(" ");
		this.version = parts[0].trim().toUpperCase();
		this.code = parts[1].trim();
		this.status = parts[2].trim();
	}

	/**
	 * Class constructor, takes the data as individual params.
	 * 
	 * @param version
	 *            the HTTP version
	 * @param code
	 *            the response code
	 * @param status
	 *            the response status
	 */
	public ResponseLine(String version, String code, String status) {
		this.version = version;
		this.code = code;
		this.status = status;
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
	 * Gets the response code.
	 * 
	 * @return the response code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Gets the response status.
	 * 
	 * @return the response status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Changes the HTTP version.
	 * 
	 * @param version
	 *            the HTTP version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Changes the response code.
	 * 
	 * @param code
	 *            the response code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Changes the response status.
	 * 
	 * @param status
	 *            the response status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.version + " " + this.code + " " + this.status + "\r\n";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof ResponseLine) {
			ResponseLine that = (ResponseLine) other;
			return this.getVersion().equals(that.getVersion()) && this.getCode().equals(that.getCode())
					&& this.getStatus().equals(that.getStatus());
		}

		return false;
	}
}

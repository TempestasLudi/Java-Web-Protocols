package com.tempestasludi.java.p17_webProtocols.http;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Date;

/**
 * Message represents an HTTP message.
 * 
 * @author Arnoud van der Leer
 */
public class Message {

	/**
	 * The message header.
	 */
	private Header header;

	/**
	 * The message body.
	 */
	private Body body;

	/**
	 * A flag determining whether the message should include auto-headers.
	 */
	private boolean autoHeaders = true;

	/**
	 * Class constructor.
	 * 
	 * @param header
	 *            the message header
	 * @param body
	 *            the message body
	 */
	public Message(Header header, Body body) {
		this.header = header;
		this.body = body;
	}

	/**
	 * Class constructor.
	 * 
	 * @param header
	 *            the message header
	 * @param body
	 *            the message body
	 * @param autoHeaders
	 *            a boolean determining whether the message should create some
	 *            default headers
	 */
	public Message(Header header, Body body, boolean autoHeaders) {
		this.header = header;
		this.body = body;
		this.autoHeaders = autoHeaders;
	}

	/**
	 * Gets the message header.
	 * 
	 * @return the message header
	 */
	public Header getHeader() {
		return this.header;
	}

	/**
	 * Gets the message body.
	 * 
	 * @return the message body
	 */
	public Body getBody() {
		return this.body;
	}

	/**
	 * Changes the message header.
	 * 
	 * @param header
	 *            the message header
	 */
	public void setHeader(Header header) {
		this.header = header;
	}

	/**
	 * Changes the message body.
	 * 
	 * @param body
	 *            the message body
	 */
	public void setBody(Body body) {
		this.body = body;
	}

	/**
	 * Merges this message with another one.
	 * 
	 * Merges the header, replaces the body.
	 * 
	 * @param message
	 *            the message to merge with
	 */
	public void merge(Message message) {
		this.header.merge(message.getHeader());
		this.body = message.getBody();
	}

	/**
	 * Creates a new message from a data input stream.
	 * 
	 * @param in
	 *            the input stream to read from
	 * @param request
	 *            whether the message is a request or not
	 * @return a new message based on the data from the input stream
	 */
	public static Message read(DataInputStream in, boolean request) {
		String requestLineString = readLine(in).trim();
		if (requestLineString.split(" ").length != 3) {
			return null;
		}
		HeaderLine headerLine;
		if (request) {
			headerLine = new RequestLine(requestLineString);
		} else {
			headerLine = new ResponseLine(requestLineString);
		}

		Header header = new Header(headerLine);
		boolean read = true;
		while (read) {
			String line = readLine(in);
			if ("\r\n".equals(line)) {
				read = false;
				continue;
			}
			if (line.contains(":")) {
				header.addField(new HeaderField(line));
			}
		}
		HeaderField contentLength = header.getField("Content-Length");
		Body body = new Body("");
		if (contentLength != null && Integer.valueOf(contentLength.getValue()) > 0) {
			body = new Body(readBytes(in, Integer.valueOf(contentLength.getValue())));
		}
		return new Message(header, body);
	}

	/**
	 * Reads one line from the input stream (including \r\n).
	 * 
	 * @param in
	 *            the input stream
	 * @return one line from the input stream
	 */
	private static String readLine(DataInputStream in) {
		String line = "";
		boolean lineBreak = false;
		while (!lineBreak) {
			try {
				char character = (char) in.readByte();
				line += Character.toString(character);
				if (line.length() >= 2 && "\r\n".equals(line.substring(line.length() - 2))) {
					lineBreak = true;
				}
			} catch (EOFException e) {
				System.out.println("Another end of file.");
				return line;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return line;
			}
		}
		return line;
	}

	/**
	 * Reads n bytes from the input stream.
	 * 
	 * @param in
	 *            the input stream to read from
	 * @param n
	 *            the number of bytes to read
	 * @return a string of n bytes from the input stream
	 */
	private static String readBytes(DataInputStream in, int n) {
		String line = "";
		for (int i = 0; i < n; i++) {
			try {
				char character = (char) in.readByte();
				line += character;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return line;
	}

	/**
	 * Generates the HTTP message in string format.
	 * 
	 * @return the HTTP message in string format
	 */
	@Override
	public String toString() {
		if (this.autoHeaders) {
			if (!(this.header.getHeaderLine() instanceof ResponseLine)
					|| "200".equals(((ResponseLine) this.header.getHeaderLine()).getCode())) {
				this.header.addField(new HeaderField("Content-Length", String.valueOf(body.getContent().length())));
			}
			this.header.addField(new HeaderField("Date", new Date().toString()));
		}
		String result = this.header.toString() + "\r\n" + this.body.toString();
		if (this.body.toString().length() > 0) {
			result += "\r\n\r\n";
		}
		return result;
	}

	/**
	 * Generates the HTTP message in byte format.
	 * 
	 * @return the HTTP message in byte format
	 */
	public byte[] toBytes() {
		if (this.autoHeaders) {
			if (!(this.header.getHeaderLine() instanceof ResponseLine)
					|| "200".equals(((ResponseLine) this.header.getHeaderLine()).getCode())) {
				this.header.addField(new HeaderField("Content-Length", String.valueOf(this.body.toBytes().length)));
			}
			this.header.addField(new HeaderField("Date", new Date().toString()));
		}
		byte[] header = (this.header.toString() + "\r\n").getBytes();
		byte[] body = this.body.toBytes();
		byte[] result = new byte[header.length + body.length];
		for (int i = 0; i < header.length; i++) {
			result[i] = header[i];
		}
		for (int i = 0; i < body.length; i++) {
			result[header.length + i] = body[i];
		}
		return result;
	}

}

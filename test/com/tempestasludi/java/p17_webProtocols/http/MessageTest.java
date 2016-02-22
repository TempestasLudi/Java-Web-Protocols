package com.tempestasludi.java.p17_webProtocols.http;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

public class MessageTest {

	@Test
	public void testConstructorHeader() {
		Header header = new Header();
		Body body = new Body("Content");
		Message testMessage = new Message(header, body);

		assertEquals(header, testMessage.getHeader());
	}

	@Test
	public void testConstructorBody() {
		Header header = new Header();
		Body body = new Body("Content");
		Message testMessage = new Message(header, body);

		assertEquals(body, testMessage.getBody());
	}

	@Test
	public void testConstructorAutoHeaders() {
		Header header = new Header();
		Body body = new Body("Content");
		Message testMessage = new Message(header, body, false);

		assertEquals(false, testMessage.isAutoHeaders());
	}
	
	@Test
	public void testGetSetAutoHeaders(){
		Header header = new Header();
		Body body = new Body("Content");
		Message testMessage = new Message(header, body, false);
		testMessage.setAutoHeaders(true);
		assertEquals(true, testMessage.isAutoHeaders());
	}
	
	@Test
	public void testGetSetHeader() {
		Header header = new Header();
		Header header2 = new Header();
		Body body = new Body("Content");
		Message testMessage = new Message(header, body);

		testMessage.setHeader(header2);
		assertEquals(header2, testMessage.getHeader());
	}

	@Test
	public void testGetSetBody() {
		Header header = new Header();
		Body body = new Body("Content");
		Body body2 = new Body("Content 2");
		Message testMessage = new Message(header, body);

		testMessage.setBody(body2);
		assertEquals(body2, testMessage.getBody());
	}

	@Test
	public void testMerge() {
		RequestLine reqLine = new RequestLine("GET /uri HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();

		Header header = new Header(reqLine, hField);

		Header header2 = new Header(reqLine, hField);
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);
		Message testMessage2 = new Message(header2, body);

		testMessage1.merge(testMessage2);
		assertEquals(testMessage1.getHeader(), header2);

	}

	@Test
	public void testRead1() {

		String message = "GET /chat HTTP/1.1\r\n" + "Content-Length: 12\r\n" + "Content-Type: text/json\r\n" + "Date: "
				+ new Date().toString() + "\r\n\r\n" + "User details\r\n\r\n";
		InputStream is = new ByteArrayInputStream(message.getBytes());
		DataInputStream in = new DataInputStream(is);

		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		Header head = new Header(reqLine);
		Body body = new Body("User details");
		Message msg = new Message(head, body);

		assertEquals(msg, Message.read(in, true));

	}

	@Test
	public void testRead2() {

		String message = "GET /chatHTTP/1.1\r\n" + "Content-Length: 12\r\n" + "Content-Type: text/json\r\n" + "Date: "
				+ new Date().toString() + "\r\n\r\n" + "User details\r\n\r\n";
		InputStream is = new ByteArrayInputStream(message.getBytes());
		DataInputStream in = new DataInputStream(is);

		assertEquals(null, Message.read(in, true));

	}

	@Test
	public void testRead3() {

		String message = "HTTP/1.1 200 OK\r\n" + "Connection: close\r\n" + "Content-Length: 12\r\n"
				+ "Content-Type: text/json\r\n" + "Date: " + new Date().toString() + "\r\n\r\n"
				+ "User details\r\n\r\n";
		InputStream is = new ByteArrayInputStream(message.getBytes());
		DataInputStream in = new DataInputStream(is);

		ResponseLine resLine = new ResponseLine("HTTP/1.1 200 OK");
		Header header = new Header(resLine);
		Body body = new Body("User details");
		Message msg = new Message(header, body);

		assertEquals(msg, Message.read(in, false));

	}

	@Test
	public void testRead4() {

		String message = "GET /chat HTTP/1.1\r\n" + "Content-Length: 0\r\n" + "Content-Type: text/json\r\n" + "Date: "
				+ new Date().toString() + "\r\n\r\n";
		InputStream is = new ByteArrayInputStream(message.getBytes());
		DataInputStream in = new DataInputStream(is);

		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		Header head = new Header(reqLine);
		Body body = new Body("");
		Message msg = new Message(head, body);

		assertEquals(msg, Message.read(in, true));

	}

	@Test
	public void testRead5() {

		String message = "GET /chat HTTP/1.1\r\n" + "\r\n\r\n";// +
																// "Content-Length:
																// 0\r\n" +
																// "Content-Type:
																// text/json\r\n"
																// + "Date: "
		// + new Date().toString() + "\r\n\r\n";
		InputStream is = new ByteArrayInputStream(message.getBytes());
		DataInputStream in = new DataInputStream(is);

		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		Header head = new Header(reqLine);
		Body body = new Body("");
		Message msg = new Message(head, body);

		assertEquals(msg, Message.read(in, true));

	}

	@Test
	public void testRead6() {

		String message = "GET /chat HTTP/1.1\r\n" + "Content-Length: 22\r\n" + "Connection\r\n"
				+ "Content-Type: text/json\r\n" + "Date: " + new Date().toString() + "\r\n\r\n"
				+ "User details: cholland\r\n\r\n";
		InputStream is = new ByteArrayInputStream(message.getBytes());
		DataInputStream in = new DataInputStream(is);

		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		Header head = new Header(reqLine);
		Body body = new Body("User details: cholland");
		Message msg = new Message(head, body);

		assertEquals(msg, Message.read(in, true));

	}

	@Test
	public void testToString1() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		hField.add(new HeaderField("Content-Type", "text/json"));
		Header header = new Header(reqLine, hField);
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);

		assertEquals("GET /chat HTTP/1.1\r\n" + "Content-Type: text/json\r\n" + "Content-Length: 7\r\n" + "Date: "
				+ new Date().toString() + "\r\n\r\n" + "Content\r\n\r\n", testMessage1.toString());
	}

	@Test
	public void testToString2() {
		ResponseLine resLine = new ResponseLine("HTTP/1.1 200 OK");
		Header header = new Header(resLine);
		header.addField(new HeaderField("Content-Type", "text/json"));
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);

		assertEquals(
				"HTTP/1.1 200 OK\r\n" + "Connection: close\r\n" + "Content-Type: text/json\r\n"
						+ "Content-Length: 7\r\n" + "Date: " + new Date().toString() + "\r\n\r\n" + "Content\r\n\r\n",
				testMessage1.toString());

	}

	@Test
	public void testToString3() {
		ResponseLine resLine = new ResponseLine("HTTP/1.1", "404", "File Not Found");
		Header header = new Header(resLine);
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);
		assertEquals("HTTP/1.1 404 File Not Found\r\n" + "Connection: close\r\n" + "Date: " + new Date().toString()
				+ "\r\n\r\n" + "Content\r\n\r\n", testMessage1.toString());

	}

	@Test
	public void testToString4() {
		ResponseLine resLine = new ResponseLine("HTTP/1.1 200 OK");
		Header header = new Header(resLine);
		header.addField(new HeaderField("Content-Type", "text/json"));
		Body body = new Body("");
		Message testMessage1 = new Message(header, body);

		assertEquals(
				"HTTP/1.1 200 OK\r\n" + "Connection: close\r\n" + "Content-Type: text/json\r\n"
						+ "Content-Length: 0\r\n" + "Date: " + new Date().toString() + "\r\n\r\n",
				testMessage1.toString());

	}

	@Test
	public void testEquals1() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);

		assertEquals(testMessage1, testMessage1);
	}

	@Test
	public void testEquals2() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);

		assertFalse(testMessage1.equals(null));
	}

	@Test
	public void testEquals3() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);

		assertFalse(testMessage1.equals(header));
	}

	@Test
	public void testEquals4() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Body body = new Body("Content");
		Body body2 = null;
		Message testMessage1 = new Message(header, body);
		Message testMessage2 = new Message(header, body2);

		assertFalse(testMessage2.equals(testMessage1));
	}

	@Test
	public void testEquals4_2() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		RequestLine reqLine2 = new RequestLine("GET /test HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Header header2 = new Header(reqLine2, hField);
		Body body2 = null;
		Message testMessage1 = new Message(header, body2);
		Message testMessage2 = new Message(header2, body2);

		assertFalse(testMessage2.equals(testMessage1));
	}

	@Test
	public void testEquals5() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Body body = new Body("Content");
		Body body2 = new Body("User details");
		Message testMessage1 = new Message(header, body);
		Message testMessage2 = new Message(header, body2);

		assertFalse(testMessage1.equals(testMessage2));
	}

	@Test
	public void testEquals6() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Header nullHeader = null;
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);
		Message testMessage2 = new Message(nullHeader, body);

		assertFalse(testMessage2.equals(testMessage1));
	}

	@Test
	public void testEquals7() {
		Header header = null;
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);
		Message testMessage2 = new Message(header, body);

		assertTrue(testMessage2.equals(testMessage1));
	}

	@Test
	public void testEquals8() {
		RequestLine reqLine = new RequestLine("GET /chat HTTP/1.1");
		ArrayList<HeaderField> hField = new ArrayList<>();
		Header header = new Header(reqLine, hField);
		Header header2 = null;
		Body body = new Body("Content");
		Message testMessage1 = new Message(header, body);
		Message testMessage2 = new Message(header2, body);

		assertFalse(testMessage1.equals(testMessage2));
	}

}

package com.tempestasludi.java.p17_webProtocols.ws;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.tempestasludi.java.p17_webProtocols.http.Body;
import com.tempestasludi.java.p17_webProtocols.http.Header;
import com.tempestasludi.java.p17_webProtocols.http.HeaderField;
import com.tempestasludi.java.p17_webProtocols.http.Message;
import com.tempestasludi.java.p17_webProtocols.http.RequestLine;
import com.tempestasludi.java.p17_webProtocols.http.ResponseLine;

public class HandshakerTest {
	
	@Test
	public void testMakeServerHandshakeWithHeaders() throws NoSuchAlgorithmException {
		Message inputMessage = new Message(new Header(new RequestLine("GET", "/chat", "HTTP/1.1")), new Body(""),
				false);
		Header inputHeader = inputMessage.getHeader();
		inputHeader.addField(new HeaderField("Host", "server.example.com"));
		inputHeader.addField(new HeaderField("Upgrade", "websocket"));
		inputHeader.addField(new HeaderField("Connection", "Upgrade"));
		inputHeader.addField(new HeaderField("Sec-WebSocket-Key", "dGhlIHNhbXBsZSBub25jZQ=="));
		inputHeader.addField(new HeaderField("Origin", "http://example.com"));
		inputHeader.addField(new HeaderField("Sec-WebSocket-Protocol", "chat, superchat"));
		inputHeader.addField(new HeaderField("Sec-WebSocket-Version", "13"));

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintWriter out = new PrintWriter(stream, true);

		Handshaker.makeServerHandshake(inputMessage, out);

		Message testMessage = new Message(new Header(new ResponseLine("HTTP/1.1", "101", "Switching Protocols")),
				new Body(""), false);
		Header testHeader = testMessage.getHeader();
		testHeader.addField(new HeaderField("Upgrade", "websocket"));
		testHeader.addField(new HeaderField("Connection", "Upgrade"));
		testHeader.addField(new HeaderField("Sec-WebSocket-Accept", "s3pPLMBiTxaQ9kYGzzhZRbK+xOo="));
		testHeader.addField(new HeaderField("Sec-WebSocket-Protocol", "chat"));

		ByteArrayOutputStream testStream = new ByteArrayOutputStream();
		PrintWriter testWriter = new PrintWriter(testStream);
		testWriter.write(testMessage.toString());
		testWriter.flush();

		assertArrayEquals(testStream.toByteArray(), stream.toByteArray());
	}

	@Test
	public void testMakeServerHandshakeWithoutHeaders() throws NoSuchAlgorithmException {
		Message inputMessage = new Message(new Header(new RequestLine("GET", "/chat", "HTTP/1.1")), new Body(""),
				false);
		Header inputHeader = inputMessage.getHeader();
		inputHeader.addField(new HeaderField("Host", "server.example.com"));
		inputHeader.addField(new HeaderField("Upgrade", "websocket"));
		inputHeader.addField(new HeaderField("Connection", "Upgrade"));
		inputHeader.addField(new HeaderField("Origin", "http://example.com"));
		inputHeader.addField(new HeaderField("Sec-WebSocket-Version", "13"));

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintWriter out = new PrintWriter(stream, true);

		Handshaker.makeServerHandshake(inputMessage, out);

		Message testMessage = new Message(new Header(new ResponseLine("HTTP/1.1", "101", "Switching Protocols")),
				new Body(""), false);
		Header testHeader = testMessage.getHeader();
		testHeader.addField(new HeaderField("Upgrade", "websocket"));
		testHeader.addField(new HeaderField("Connection", "Upgrade"));
		testHeader.addField(new HeaderField("Sec-WebSocket-Accept", ""));

		ByteArrayOutputStream testStream = new ByteArrayOutputStream();
		PrintWriter testWriter = new PrintWriter(testStream);
		testWriter.write(testMessage.toString());
		testWriter.flush();

		assertArrayEquals(testStream.toByteArray(), stream.toByteArray());
	}

}

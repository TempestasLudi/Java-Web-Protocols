package com.tempestasludi.java.p17_webProtocols.ws;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.tempestasludi.java.p17_webProtocols.http.Body;
import com.tempestasludi.java.p17_webProtocols.http.Header;
import com.tempestasludi.java.p17_webProtocols.http.HeaderField;
import com.tempestasludi.java.p17_webProtocols.http.Message;
import com.tempestasludi.java.p17_webProtocols.http.ResponseLine;

/**
 * Handshaker is designed respond to a client websocket handshake request.
 *
 * @author Tempestas Ludi
 */
public class Handshaker {

	/**
	 * Responds to a HTTP webSocket handshake request from a client.
	 *
	 * @param request
	 *            the request to respond to
	 * @param out
	 *            the outputStream to write to
	 */
	public static void makeServerHandshake(Message request, PrintWriter out) throws NoSuchAlgorithmException {
		String clientKey = "";
		if (request.getHeader().getField("Sec-WebSocket-Key") != null) {
			clientKey = generateKey(request.getHeader().getField("Sec-WebSocket-Key").getValue().trim());
		}

		Message response = new Message(new Header(new ResponseLine("HTTP/1.1", "101", "Switching Protocols")),
				new Body(""), false);
		response.getHeader().addField(new HeaderField("Upgrade", "websocket"));
		response.getHeader().addField(new HeaderField("Connection", "Upgrade"));
		response.getHeader().addField(new HeaderField("Sec-WebSocket-Accept", clientKey));
		if (request.getHeader().getField("Sec-WebSocket-Protocol") != null) {
			response.getHeader().addField(new HeaderField("Sec-WebSocket-Protocol", "chat"));
		}
		out.write(response.toString());
		out.flush();
	}

	/**
	 * Generates a key based on a key sent by the client.
	 *
	 * @param clientKey
	 *            the key sent from the client
	 * @return a key based on clientKey
	 */
	private static String generateKey(String clientKey) throws NoSuchAlgorithmException {
		clientKey += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		byte[] serverKey;
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		serverKey = md.digest(clientKey.getBytes(StandardCharsets.UTF_8));
		clientKey = new String(Base64.getEncoder().encode(serverKey));
		return clientKey;
	}

}

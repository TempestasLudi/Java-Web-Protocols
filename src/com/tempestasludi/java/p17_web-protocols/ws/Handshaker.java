package com.tempestasludi.java.P12_site.ws;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.tempestasludi.java.P12_site.http.Body;
import com.tempestasludi.java.P12_site.http.Header;
import com.tempestasludi.java.P12_site.http.HeaderField;
import com.tempestasludi.java.P12_site.http.Message;
import com.tempestasludi.java.P12_site.http.ResponseLine;

public class Handshaker {
	
	public static void makeServerHandshake(Message request, PrintWriter out) {
		String clientKey = "";
		if (request.getHeader().getField("Sec-WebSocket-Key") != null) {
			clientKey = generateKey(request.getHeader().getField("Sec-WebSocket-Key").getValue().trim());
		}

		Message response = new Message(new Header(new ResponseLine("HTTP/1.1", "101", "Switching Protocols")), new Body(""),
				false);
		response.getHeader().addField(new HeaderField("Upgrade", "websocket"));
		response.getHeader().addField(new HeaderField("Connection", "Upgrade"));
		response.getHeader().addField(new HeaderField("Sec-WebSocket-Accept", clientKey));
		if (request.getHeader().getField("Sec-WebSocket-Protocol") != null) {
			response.getHeader().addField(new HeaderField("Sec-WebSocket-Protocol", "chat"));
		}
		out.write(response.toString());
		out.flush();
	}

	private static String generateKey(String clientKey) {
//		clientKey = new String(Base64.getDecoder().decode(clientKey.getBytes(StandardCharsets.UTF_8))).trim();
		clientKey += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		clientKey = new String(Base64.getEncoder().encode(md.digest(clientKey.getBytes(StandardCharsets.UTF_8))));
		return clientKey;
	}
	
}

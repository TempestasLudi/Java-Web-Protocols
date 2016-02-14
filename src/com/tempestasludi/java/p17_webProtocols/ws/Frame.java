package com.tempestasludi.java.p17_webProtocols.ws;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Frame {
	
	private boolean lastFrame;
	private int opcode;
	private boolean masked;
	private byte[] mask;
	private String payload;
	
	public Frame(boolean lastFrame, int opcode, boolean masked, byte[] mask, String payload) {
		this.lastFrame = lastFrame;
		this.opcode = opcode;
		this.masked = masked;
		this.mask = mask;
		this.payload = payload;
	}
	
	public boolean isLastFrame() {
		return this.lastFrame;
	}
	
	public int getOpcode() {
		return this.opcode;
	}
	
	public boolean isMasked() {
		return this.masked;
	}
	
	public byte[] getMask() {
		return this.mask;
	}
	
	public String getPayload() {
		return this.payload;
	}
	
	public static Frame read(DataInputStream in) {
		try {
			int buffer = in.read();
			boolean lastFrame = buffer / 128 == 1;
			int opcode = (buffer % 16);
			buffer = in.read();
			boolean masked = buffer / 128 == 1;
			long length = (buffer % 128);
			if (length >= 126) {
				byte[] lengthBuffer;
				if (length == 126) {
					lengthBuffer = new byte[2];
					in.read(lengthBuffer);
					length = ByteBuffer.allocate(2).put(lengthBuffer).getShort();
				} else if (length == 127) {
					lengthBuffer = new byte[8];
					in.read(lengthBuffer);
					length = ByteBuffer.allocate(8).put(lengthBuffer).getLong();
				}
			}
			byte[] mask = new byte[4];
			if (masked) {
				for (int i = 0; i < 4; i++) {
					mask[i] = (byte) in.read();
				}
			}
			byte[] payload = new byte[(int) length];
			for (int i = 0; i < length; i++) {
				if (masked) {
					payload[i] = (byte) (in.read() ^ mask[i % 4]);
				}
				else {
					payload[i] = (byte) (in.read());
				}
			}
			return new Frame(lastFrame, opcode, masked, mask, new String(payload));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return new Frame(true, 1, false, new byte[0], "");
	}
	
	public void write(DataOutputStream out) {
		try {
			if (this.lastFrame) {
				out.write((byte) 128 + opcode);
			}
			else {
				out.write((byte) opcode);
			}
			byte[] dataBytes = this.payload.getBytes(StandardCharsets.UTF_8);
			long dataLength = dataBytes.length;
			int maskedValue = 0;
			if (this.masked) {
				maskedValue = 128;
			}
			if (dataLength < 126) {
				out.write((byte) dataLength + maskedValue);
			} else if (dataLength < Integer.MAX_VALUE) {
				out.write((byte) 126 + maskedValue);
				out.write(ByteBuffer.allocate(2).putShort((short) dataLength).array());
			} else {
				out.write((byte) 127 + maskedValue);
				out.write(ByteBuffer.allocate(8).putLong(dataLength).array());
			}
			if (this.masked) {
				out.write(this.mask);
			}
			out.write(dataBytes);
			out.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}

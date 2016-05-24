package com.tempestasludi.java.p17_webProtocols.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Fram represents a data frame in an web socket communication session.
 *
 * @author Tempestas Ludi
 */
public class Frame {

	/**
	 * Whether this frame is the last one or not.
	 */
	private boolean lastFrame;

	/**
	 * The opcode, the type, of the frame.
	 */
	private int opcode;

	/**
	 * Whether this frame is masked (encrypted) or not.
	 */
	private boolean masked;

	/**
	 * If masked, the mask with which it is masked.
	 */
	private byte[] mask;

	/**
	 * The actual data of the frame.
	 */
	private String payload;

	/**
	 * Class constructor.
	 *
	 * @param lastFrame
	 *            whether the frame is the last one or not
	 * @param opcode
	 *            the opcode of the frame
	 * @param masked
	 *            whether the frame is masked or not
	 * @param mask
	 *            if masked, the mask
	 * @param payload
	 *            the content of the frame
	 */
	public Frame(boolean lastFrame, int opcode, boolean masked, byte[] mask, String payload) {
		this.lastFrame = lastFrame;
		this.opcode = opcode;
		this.masked = masked;
		this.mask = mask;
		this.payload = payload;
	}

	/**
	 * Check whether this is the last frame.
	 *
	 * @return lastFrame
	 */
	public boolean isLastFrame() {
		return lastFrame;
	}

	/**
	 * Get the opcode.
	 *
	 * @return the opcode
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Check whether the frame is masked or not.
	 *
	 * @return masked
	 */
	public boolean isMasked() {
		return masked;
	}

	/**
	 * Get the mask.
	 *
	 * @return the mask
	 */
	public byte[] getMask() {
		return mask;
	}

	/**
	 * Get the payload.
	 *
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * Change whether the frame is the last one or not.
	 *
	 * @param lastFrame
	 *            the state to change to
	 */
	public void setLastFrame(boolean lastFrame) {
		this.lastFrame = lastFrame;
	}

	/**
	 * Change the opcode.
	 *
	 * @param opcode
	 *            the opcode to change to
	 */
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	/**
	 * Change whether the frame is masked or not.
	 *
	 * @param masked
	 *            the status to change to
	 */
	public void setMasked(boolean masked) {
		this.masked = masked;
	}

	/**
	 * Change the mask.
	 *
	 * @param mask
	 *            the mask to change to
	 */
	public void setMask(byte[] mask) {
		this.mask = mask;
	}

	/**
	 * Change the payload.
	 *
	 * @param payload
	 *            the payload to change to
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	/**
	 * Reads a frame from an input stream.
	 *
	 * @param in
	 *            the stream to read from
	 * @return a frame based on the data from the input stream
	 */
	public static Frame read(InputStream in) throws IOException {
		// First byte: lastFrame(1) + opCode(4)
		int buffer = in.read();
		boolean lastFrame = buffer / 128 == 1;
		int opcode = (buffer % 16);
		
		// Second byte: masked(1) + length(7)
		buffer = in.read();
		boolean masked = buffer / 128 == 1;
		long length = (buffer % 128);
		
		// Maybe some additional length bytes:
		if (length >= 126) {
			byte[] lengthBuffer;
			if (length == 126) {
				lengthBuffer = new byte[2];
				in.read(lengthBuffer);
				ByteBuffer byteBuffer = ByteBuffer.allocate(2).put(lengthBuffer);
				byteBuffer.rewind();
				length = byteBuffer.getShort();
//			} else if (length == 127) {
//				lengthBuffer = new byte[8];
//				in.read(lengthBuffer);
//				length = ByteBuffer.allocate(8).put(lengthBuffer).getLong();
			}
		}
		
		// The next four bytes: mask
		byte[] mask = new byte[4];
		if (masked) {
			for (int i = 0; i < 4; i++) {
				mask[i] = (byte) in.read();
			}
		}
		
		// The remainder of the bytes form the payload/content of the frame
		byte[] payload = new byte[(int) length];
		for (int i = 0; i < length; i++) {
			if (masked) {
				payload[i] = (byte) (in.read() ^ mask[i % 4]);
			} else {
				payload[i] = (byte) (in.read());
			}
		}
		return new Frame(lastFrame, opcode, masked, mask, new String(payload, StandardCharsets.UTF_8));
	}

	/**
	 * Writes the frame to an output stream.
	 *
	 * @param out
	 *            the stream to write to
	 */
	public void write(OutputStream out) throws IOException {
		if (this.lastFrame) {
			out.write((byte) 128 + opcode);
		} else {
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
//		} else if (dataLength >= Integer.MAX_VALUE) {
//			out.write((byte) 127 + maskedValue);
//			out.write(ByteBuffer.allocate(8).putLong(dataLength).array());
		} else {
			out.write((byte) 126 + maskedValue);
			out.write(ByteBuffer.allocate(2).putShort((short) dataLength).array());
		} 
		if (this.masked) {
			out.write(this.mask);
		}
		out.write(dataBytes);
		out.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Frame)) {
			return false;
		}
		Frame other = (Frame) obj;
		if (lastFrame != other.lastFrame) {
			return false;
		}
		if (masked && !Arrays.equals(mask, other.mask)) {
			return false;
		}
		if (masked != other.masked) {
			return false;
		}
		if (opcode != other.opcode) {
			return false;
		}
		if (payload == null) {
			if (other.payload != null) {
				return false;
			}
		} else if (!payload.equals(other.payload)) {
			return false;
		}
		return true;
	}

}

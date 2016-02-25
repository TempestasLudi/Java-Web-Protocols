package com.tempestasludi.java.p17_webProtocols.ws;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class FrameTest {

	private Frame generate() {
		return generate(generateMask());
	}
	
	private Frame generate(byte[] mask) {
		return new Frame(true, 1, true, mask, "Payload, ya know?");
	}
	
	private byte[] generateMask() {
		return new byte[]{99, -19, 40, -123};
	}
	
	@Test
	public void testFrameLast() {
		Frame frame = generate();
		
		assertTrue(frame.isLastFrame());
	}
	
	@Test
	public void testFrameOpcode() {
		Frame frame = generate();
		
		assertEquals(1, frame.getOpcode());
	}
	
	@Test
	public void testFrameMasked() {
		Frame frame = generate();
		
		assertTrue(frame.isMasked());
	}
	
	@Test
	public void testFrameMask() {
		Frame frame = generate();
		
		assertArrayEquals(generateMask(), frame.getMask());
	}
	
	@Test
	public void testFramePayload() {
		Frame frame = generate();
		
		assertEquals("Payload, ya know?", frame.getPayload());
	}

	@Test
	public void testIsSetLastFrame() {
		Frame frame = generate();
		frame.setLastFrame(false);
		
		assertFalse(frame.isLastFrame());
	}

	@Test
	public void testGetSetOpcode() {
		Frame frame = generate();
		frame.setOpcode(5);
		
		assertEquals(5, frame.getOpcode());
	}

	@Test
	public void testIsSetMasked() {
		Frame frame = generate();
		frame.setMasked(false);
		
		assertFalse(frame.isMasked());
	}

	@Test
	public void testGetSetMask() {
		byte[] mask = {18, 32, -90, 125};
		Frame frame = generate();
		frame.setMask(mask);
		
		assertArrayEquals(mask, frame.getMask());
	}

	@Test
	public void testGetSetPayload() {
		String payload = "ASDF";
		Frame frame = generate();
		frame.setPayload(payload);
		
		assertEquals(payload, frame.getPayload());
	}

	@Test
	public void testReadShort() throws IOException {
		ByteArrayOutputStream testBytes = new ByteArrayOutputStream();
		testBytes.write((byte) 129);
		testBytes.write((byte) 128 + 17);
		byte[] origPayload = "Payload, ya know?".getBytes(StandardCharsets.UTF_8);
		byte[] payload = new byte[origPayload.length];
		byte[] mask = generateMask();
		for (int i = 0; i < origPayload.length; i++) {
			payload[i] = (byte) (origPayload[i] ^ mask[i % 4]);
		}
		testBytes.write(mask);
		testBytes.write(payload);
		
		ByteArrayInputStream stream = new ByteArrayInputStream(testBytes.toByteArray());
		Frame frame = Frame.read(stream);
		
		Frame testFrame = generate();
		
		assertEquals(testFrame, frame);
	}

	@Test
	public void testReadMedium() throws IOException {
		String payload = "aetyuio;.lkjuytfdcxsdrewqwasdtyukjytdkdikkdiaáawe afás/zxcxv[wa[wesnxvawfw38*3aasfjvoiasfjlwaejio443489l9dfsdio344344234[erdfgs34/sd/  334sjgi09w34";
		ByteArrayOutputStream testBytes = new ByteArrayOutputStream();
		testBytes.write((byte) 1);
		testBytes.write((byte) 126);
		testBytes.write(ByteBuffer.allocate(2).putShort((short) payload.getBytes(StandardCharsets.UTF_8).length).array());
		testBytes.write(payload.getBytes(StandardCharsets.UTF_8));
		
		ByteArrayInputStream stream = new ByteArrayInputStream(testBytes.toByteArray());
		Frame frame = Frame.read(stream);

		Frame testFrame = generate();
		testFrame.setPayload(payload);
		testFrame.setLastFrame(false);
		testFrame.setMasked(false);
		
		assertEquals(testFrame, frame);
	}

	@Test
	public void testWriteShort() throws IOException {
		Frame frame = generate();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		frame.write(stream);
		ByteArrayOutputStream testBytes = new ByteArrayOutputStream();
		testBytes.write((byte) 129);
		testBytes.write((byte) 128 + 17);
		testBytes.write(generateMask());
		testBytes.write("Payload, ya know?".getBytes());
		
		assertArrayEquals(testBytes.toByteArray(), stream.toByteArray());
	}

	@Test
	public void testWriteMedium() throws IOException {
		Frame frame = generate();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		String payload = "aetyuio;.lkjuytfdcxsdrewqwasdtyukjytdkdikkdiaáawe afás/zxcxv[wa[wesnxvawfw38*3aasfjvoiasfjlwaejio443489l9dfsdio344344234[erdfgs34/sd/  334sjgi09w34";
		frame.setPayload(payload);
		frame.setLastFrame(false);
		frame.setMasked(false);
		frame.write(stream);
		ByteArrayOutputStream testBytes = new ByteArrayOutputStream();
		testBytes.write((byte) 1);
		testBytes.write((byte) 126);
		testBytes.write(ByteBuffer.allocate(2).putShort((short) payload.getBytes(StandardCharsets.UTF_8).length).array());
		testBytes.write(payload.getBytes(StandardCharsets.UTF_8));
		
		assertArrayEquals(testBytes.toByteArray(), stream.toByteArray());
	}

	@Test
	public void testEqualsSelf() {
		Frame frame = generate();

		assertEquals(frame, frame);
	}
	
	@Test
	public void testEqualsCopy() {
		Frame frame = generate();
		Frame copy = generate();
		
		assertEquals(frame, copy);
	}
	
	@Test
	public void testEqualsNull() {
		Frame frame = generate();
		
		assertNotEquals(frame, null);
	}
	
	@Test
	public void testEqualsString() {
		Frame frame = generate();
		String string = "Payload, ya know?";
		
		assertNotEquals(frame, string);
	}
	
	@Test
	public void testEqualsLastFrame() {
		Frame frame = generate();
		Frame otherFrame = generate();
		otherFrame.setLastFrame(false);
		
		assertNotEquals(frame, otherFrame);
	}
	
	@Test
	public void testEqualsMask() {
		Frame frame = generate();
		Frame otherFrame = generate();
		otherFrame.setMask(new byte[]{4, 10, 53, -59});
		
		assertNotEquals(frame, otherFrame);
	}
	
	@Test
	public void testEqualsMasked() {
		Frame frame = generate();
		Frame otherFrame = generate();
		otherFrame.setMasked(false);
		
		assertNotEquals(frame, otherFrame);
	}
	
	@Test
	public void testEqualsOpcode() {
		Frame frame = generate();
		Frame otherFrame = generate();
		otherFrame.setOpcode(6);
		
		assertNotEquals(frame, otherFrame);
	}
	
	@Test
	public void testEqualsPayloadNull() {
		Frame frame = generate();
		frame.setPayload(null);
		Frame otherFrame = generate();
		otherFrame.setPayload(null);
		
		assertEquals(frame, otherFrame);
	}
	
	@Test
	public void testNotEqualsPayloadNull() {
		Frame frame = generate();
		frame.setPayload(null);
		Frame otherFrame = generate();
		
		assertNotEquals(frame, otherFrame);
	}
	
	@Test
	public void testEqualsPayload() {
		Frame frame = generate();
		Frame otherFrame = generate();
		otherFrame.setPayload("Other payload, ya know?");
		
		assertNotEquals(frame, otherFrame);
	}
	
}

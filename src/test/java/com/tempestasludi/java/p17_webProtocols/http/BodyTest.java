package com.tempestasludi.java.p17_webProtocols.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class BodyTest {

	@Test
	public void testConstructorString() {
		Body body = new Body("Hello, World!");
		assertEquals("Hello, World!", body.getContentString());
	}
	
	@Test
	public void testConstructorBytes() {
		byte[] bytes = {1, 2, 6, 12, -6, 8, 127};
		Body body = new Body(bytes);
		assertArrayEquals(bytes, body.getContent());
	}

	@Test
	public void testGetSetContentString() {
		Body body = new Body((String) null);
		body.setContent("Hello, World!");
		assertEquals("Hello, World!", body.getContentString());
	}
	
	@Test
	public void testGetSetContent() {
		byte[] bytes = {1, 2, 6, 12, -6, 8, 127};
		Body body = new Body("");
		body.setContent(bytes);
		assertArrayEquals(bytes, body.getContent());
	}

	@Test
	public void testToString() {
		Body body = new Body("Hello, World!");
		assertEquals("Hello, World!", body.toString());
	}
	
	@Test
	public void testToBytes() {
		byte[] bytes = {1, 2, 6, 12, -6, 8, 127};
		Body body = new Body(bytes);
		assertArrayEquals(bytes, body.toBytes());
	}

	@Test
	public void testEqualsPositive() {
		Body body1 = new Body("Hello, World!");
		Body body2 = new Body("Hello, World!");
		assertTrue(body1.equals(body2));
	}

	@Test
	public void testEqualsNegative1() {
		Body body1 = new Body("Hello, World!");
		Body body2 = new Body("Hello, world!");
		assertFalse(body1.equals(body2));
	}

	@Test
	public void testEqualsNegative2() {
		Body body = new Body("Hello, World!");
		assertFalse(body.equals(21));
	}

}

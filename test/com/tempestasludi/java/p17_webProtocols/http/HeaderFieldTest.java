package com.tempestasludi.java.p17_webProtocols.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HeaderFieldTest {

	@Test
	public void testConstructorHeaderField1() {
		HeaderField headerfield = new HeaderField("Content-length: 10 ");
		assertEquals("Content-length", headerfield.getName());
	}

	@Test
	public void testConstructorHeaderField2() {
		HeaderField headerfield = new HeaderField("Content-length: 10 ");
		assertEquals("10", headerfield.getValue());
	}

	@Test
	public void testConstructorHeaderField3() {
		HeaderField headerfield = new HeaderField("Content-length", "10");
		assertEquals("Content-length", headerfield.getName());
	}

	@Test
	public void testConstructorHeaderField4() {
		HeaderField headerfield = new HeaderField("Content-length", "10");
		assertEquals("10", headerfield.getValue());
	}

	@Test
	public void testGetSetName() {
		HeaderField headerfield = new HeaderField(null, "10");
		headerfield.setName("Content-length");
		assertEquals("Content-length", headerfield.getName());
	}

	@Test
	public void testGetSetValue() {
		HeaderField headerfield = new HeaderField("Content-length", null);
		headerfield.setValue("10");
		assertEquals("10", headerfield.getValue());
	}

	@Test
	public void testToString() {
		HeaderField headerfield = new HeaderField("Content-length: 10 ");
		assertEquals("Content-length: 10\r\n", headerfield.toString());
	}

	@Test
	public void testEqualsPositive() {
		HeaderField headerfield1 = new HeaderField("Content-length", "10");
		HeaderField headerfield2 = new HeaderField("Content-length", "10");
		assertTrue(headerfield1.equals(headerfield2));
	}

	@Test
	public void testEqualsNegative1() {
		HeaderField headerfield1 = new HeaderField("Content-length", "10");
		HeaderField headerfield2 = new HeaderField("Content-length", "11");
		assertFalse(headerfield1.equals(headerfield2));
	}

	@Test
	public void testEqualsNegative2() {
		HeaderField headerfield1 = new HeaderField("Content-type", "10");
		HeaderField headerfield2 = new HeaderField("Content-length", "10");
		assertFalse(headerfield1.equals(headerfield2));
	}

	@Test
	public void testEqualsNegative3() {
		HeaderField headerfield = new HeaderField("Content-length", "10");
		assertFalse(headerfield.equals(76));
	}

}

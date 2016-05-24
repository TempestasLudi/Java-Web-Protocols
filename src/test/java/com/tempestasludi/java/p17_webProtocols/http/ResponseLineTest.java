package com.tempestasludi.java.p17_webProtocols.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ResponseLineTest {

	@Test
	public void testConstructorVersion1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("HTTP/1.1", testLine.getVersion());
	}
	
	@Test
	public void testConstructorVersion2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1 200 OK");
		assertEquals("HTTP/1.1", testLine.getVersion());
	}
	
	@Test
	public void testConstructorCode1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("200", testLine.getCode());
	}
	
	@Test
	public void testConstructorCode2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1 200 OK");
		assertEquals("200", testLine.getCode());
	}
	
	@Test
	public void testConstructorStatus1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("OK", testLine.getStatus());
	}
	
	@Test
	public void testConstructorStatus2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1 200 OK");
		assertEquals("OK", testLine.getStatus());
	}
	
	@Test
	public void testGetVersion1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("HTTP/1.1", testLine.getVersion());
		}
	
	@Test
	public void testGetVersion2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertFalse("HTTP/1.2".equals(testLine.getVersion()));
		}
	
	@Test
	public void testGetCode1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("200", testLine.getCode());
		}
	
	@Test
	public void testGetCode2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertFalse("201".equals(testLine.getCode()));
		}
	
	@Test
	public void testGetStatus1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("OK", testLine.getStatus());
		}
	
	@Test
	public void testGetStatus2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertFalse("OK ".equals(testLine.getStatus()));
		}
	
	@Test
	public void testSetVersion1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("HTTP/1.1", testLine.getVersion());
		
		testLine.setVersion("HTTP/1.2");
		assertEquals("HTTP/1.2", testLine.getVersion());
		}
	
	@Test
	public void testSetVersion2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("HTTP/1.1", testLine.getVersion());
		testLine.setVersion("HTTP/1.2");
		assertFalse("HTTP/1.1".equals(testLine.getVersion()));
		}
	
	@Test
	public void testSetCode1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("200", testLine.getCode());
		
		testLine.setCode("201");
		assertEquals("201", testLine.getCode());
		}
	
	@Test
	public void testSetCode2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("200", testLine.getCode());
		testLine.setCode("201");
		assertFalse("200".equals(testLine.getCode()));
		}
	
	@Test
	public void testSetStatus1() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("OK", testLine.getStatus());
		
		testLine.setVersion("Permanently Moved");
		assertEquals("Permanently Moved", testLine.getVersion());
		}
	
	@Test
	public void testSetStatus2() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("OK", testLine.getStatus());
		testLine.setStatus("Permanently moved");
		assertFalse("OK ".equals(testLine.getStatus()));
		}
	
	@Test
	public void testToString() {
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals("HTTP/1.1 200 OK\r\n", testLine.toString());
	}
	
	@Test
	public void testEqualsTrue(){
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		ResponseLine testLine2 = new ResponseLine("HTTP/1.1", "200", "OK");
		assertEquals(testLine, testLine2);
	}
	
	@Test
	public void testEqualsFalse1(){
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		ResponseLine testLine2 = new ResponseLine("HTTP/1.2 200 OK");		
		assertFalse(testLine.equals(testLine2));				
	}
	
	@Test
	public void testEqualsFalse2(){
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		ResponseLine testLine2 = new ResponseLine("HTTP/1.1 201 OK");		
		assertFalse(testLine.equals(testLine2));				
	}
	
	@Test
	public void testEqualsFalse3(){
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		ResponseLine testLine2 = new ResponseLine("HTTP/1.1 200 KK");		
		assertFalse(testLine.equals(testLine2));				
	}
	
	@Test
	public void testEqualsNotRL(){
		ResponseLine testLine = new ResponseLine("HTTP/1.1", "200", "OK");
		RequestLine testLine2 = new RequestLine("HTTP/1.1 201 OK");	
		assertFalse(testLine.equals(testLine2));
		
	}
}

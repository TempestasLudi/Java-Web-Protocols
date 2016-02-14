package com.tempestasludi.java.p17_webProtocols.http;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HeaderTest {

	@Test
	public void DefaultConstructorTest() {
		ResponseLine r = new ResponseLine("HTTP/1.1", "200", "OK");
		String rString = r.toString();
		HeaderField rHeaderField = new HeaderField("Connection", "close");
		String rHeaderFieldString = rHeaderField.toString();

		Header h = new Header();
		String hHeaderLine = h.getHeaderLine().toString();
		String hHeaderField = h.getField("Connection").toString();
		assertTrue(rString.equals(hHeaderLine));
		assertTrue(rHeaderFieldString.equals(hHeaderField));
	}

	@Test
	public void testFirstConstructor1() {
		HeaderField hf = new HeaderField("Connection", "close");
		String hfString = hf.toString();

		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		String hlString = hl.toString();

		Header h = new Header(hl);
		String hHeaderLine = h.getHeaderLine().toString();
		String hHeaderField = h.getField("Connection").toString();

		assertTrue(hlString.equals(hHeaderLine));
		assertTrue(hfString.equals(hHeaderField));
	}

	@Test
	public void testFirstConstructor2() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		HeaderField hf = new HeaderField("Connection", "close");
		Header h = new Header(hl);

		assertTrue(hl.equals(h.getHeaderLine()));
		assertTrue(hf.equals(h.getField("Connection")));
	}

	@Test
	public void testFirstConstructor3() {

		HeaderLine hl = new RequestLine("method", "URI", "version");
		String hlString = hl.toString();

		Header h = new Header(hl);
		String hHeaderLine = h.getHeaderLine().toString();

		boolean evaluate = hlString.equals(hHeaderLine);
		assertTrue(evaluate);
	}

	@Test
	public void testFirstConstructor4() {
		HeaderLine hl = new RequestLine("method", "URI", "version");
		Header h = new Header(hl);

		boolean evaluate = hl.equals(h.getHeaderLine());
		assertTrue(evaluate);

	}

	@Test
	public void testSecondConstructor1() {
		RequestLine rl = new RequestLine("method", "URI", "version");

		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);

		Header h = new Header(rl, fields);
		assertTrue(h.getHeaderLine().equals(rl));

		for (int i = 0; i < fields.size(); i++) {
			assertTrue(fields.get(i).toString().equals(h.getFields()[i].toString()));
		}
	}

	@Test
	public void testSecondConstructor2() {
		RequestLine rl = new RequestLine("method", "URI", "version");

		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);

		Header h = new Header(rl, fields);
		assertTrue(h.getHeaderLine().equals(rl));

		for (int i = 0; i < fields.size(); i++) {
			assertTrue(fields.get(i).equals(h.getFields()[i]));
		}
	}

	@Test
	public void testGetFields1() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		HeaderField hf = new HeaderField("Connection", "close");
		Header h = new Header(hl);

		HeaderField[] hfs1 = { hf };
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
		for (int i = 0; i < hfs2.length; i++) {
			assertTrue(hfs2[i].toString().equals(hfs1[i].toString()));
		}
	}

	@Test
	public void testGetFields2() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		HeaderField hf = new HeaderField("Connection", "close");
		Header h = new Header(hl);

		HeaderField[] hfs1 = { hf };
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
		for (int i = 0; i < hfs2.length; i++) {
			assertTrue(hfs2[i].equals(hfs1[i]));
		}
	}

	@Test
	public void testGetFields3() {
		HeaderLine hl = new RequestLine("method", "URI", "version");
		Header h = new Header(hl);

		HeaderField[] hfs1 = new HeaderField[h.getFields().length];
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
	}

	@Test
	public void testGetFields4() {
		HeaderLine hl = new RequestLine("method", "URI", "version");
		Header h = new Header(hl);

		HeaderField[] hfs1 = new HeaderField[h.getFields().length];
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
	}

	@Test
	public void testGetFields5() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		HeaderField hf3 = new HeaderField("Thread", "lazy");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf3);

		Header h = new Header(rl, fields);

		HeaderField[] hfs1 = { hf1, hf2, hf3 };
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
		for (int i = 0; i < hfs2.length; i++) {
			assertTrue(hfs2[i].toString().equals(hfs1[i].toString()));
		}

	}

	@Test
	public void testGetFields6() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		HeaderField hf3 = new HeaderField("Socket", "lazy");
		HeaderField hf4 = new HeaderField("ServerSocket", "wrong");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf4);

		Header h = new Header(rl, fields);

		HeaderField[] hfs1 = { hf1, hf2, hf3 };
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
		assertTrue(hfs2[0].toString().equals(hfs1[0].toString()));
		assertTrue(hfs2[1].toString().equals(hfs1[1].toString()));
		assertFalse(hfs2[2].toString().equals(hfs1[2].toString()));
	}

	@Test
	public void testGetFields7() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		HeaderField hf3 = new HeaderField("Thread", "lazy");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf3);

		Header h = new Header(rl, fields);

		HeaderField[] hfs1 = { hf1, hf2, hf3 };
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
		for (int i = 0; i < hfs2.length; i++) {
			assertTrue(hfs2[i].equals(hfs1[i]));
		}
	}

	@Test
	public void testGetFields8() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		HeaderField hf3 = new HeaderField("Socket", "lazy");
		HeaderField hf4 = new HeaderField("Thread", "wrong");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf4);

		Header h = new Header(rl, fields);

		HeaderField[] hfs1 = { hf1, hf2, hf3 };
		HeaderField[] hfs2 = h.getFields();

		assertEquals(hfs1.length,hfs2.length);
		assertTrue(hfs2[0].toString().equals(hfs1[0].toString()));
		assertTrue(hfs2[1].toString().equals(hfs1[1].toString()));
		assertFalse(hfs2[2].toString().equals(hfs1[2].toString()));
	}

	@Test
	public void testGetFields9() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		HeaderField hf3 = new HeaderField("Socket", "lazy");
		HeaderField hf4 = new HeaderField("Thread", "wrong");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf3);
		fields.add(hf4);

		Header h = new Header(rl, fields);

		HeaderField[] hfs1 = { hf1, hf2, hf3, hf4, hf4 };
		HeaderField[] hfs2 = h.getFields();

		assertNotEquals(hfs1.length,hfs2.length);
	}

	@Test
	public void testGetFields10() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Socket", "working");
		HeaderField hf3 = new HeaderField("Thread", "lazy");
		HeaderField hf4 = new HeaderField("Server", "wrong");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf4);

		Header h = new Header(rl, fields);

		HeaderField[] hfs1 = { hf1, hf2, hf3, hf4, hf4, hf1 };
		HeaderField[] hfs2 = h.getFields();

		assertNotEquals(hfs1.length,hfs2.length);
	}

	@Test
	public void testGetField1() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		Header h = new Header(hl);

		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = h.getField("Connection");

		assertEquals(hf1.toString(),hf2.toString());
	}

	@Test
	public void testGetField2() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		Header h = new Header(hl);

		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = h.getField("Connection");

		assertTrue(hf1.equals(hf2));
	}

	@Test
	public void testGetField3() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		Header h = new Header(hl);

		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = h.getField("Network");

		assertFalse(hf1.toString().equals(hf2));
	}

	@Test
	public void testGetField4() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		Header h = new Header(hl);

		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = h.getField("Network");

		assertFalse(hf1.equals(hf2));
	}

	@Test
	public void testGetField5() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");
		Header h = new Header(hl);

		HeaderField hf1 = h.getField("Random");

		assertNull(hf1);
	}

	@Test
	public void testGetField6() {
		HeaderLine hl = new RequestLine("method", "URI", "version");
		Header h = new Header(hl);

		HeaderField hf1 = h.getField("Random");

		assertNull(hf1);
	}

	@Test
	public void testGetField7() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		HeaderField hf3 = new HeaderField("Socket", "lazy");
		HeaderField hf4 = new HeaderField("Thread", "wrong");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf3);
		fields.add(hf4);

		Header h = new Header(rl, fields);

		HeaderField hfs1 = h.getField("Connection");
		HeaderField hfs2 = h.getField("Server");
		HeaderField hfs3 = h.getField("Socket");
		HeaderField hfs4 = h.getField("Thread");

		assertTrue(hf1.equals(hfs1));
		assertTrue(hf2.equals(hfs2));
		assertTrue(hf3.equals(hfs3));
		assertTrue(hf4.equals(hfs4));
	}

	@Test
	public void testGetField8() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		HeaderField hf3 = new HeaderField("Socket", "lazy");
		HeaderField hf4 = new HeaderField("Thread", "wrong");

		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);
		fields.add(hf3);

		Header h = new Header(rl, fields);

		HeaderField hfs1 = h.getField("Connection");
		HeaderField hfs2 = h.getField("Server");
		HeaderField hfs3 = h.getField("Socket");
		HeaderField hfs4 = h.getField("Thread");

		assertTrue(hf1.equals(hfs1));
		assertTrue(hf2.equals(hfs2));
		assertTrue(hf3.equals(hfs3));
		assertFalse(hf4.equals(hfs4));
	}

	@Test
	public void testGetHeaderLine1() {
		HeaderLine hl1 = new ResponseLine("HTTP/1.1", "200", "OK");
		Header h = new Header(hl1);

		HeaderLine hl2 = h.getHeaderLine();

		boolean evaluate = hl1.toString().equals(hl2.toString());

		assertTrue(evaluate);
	}

	@Test
	public void testGetHeaderLine2() {
		HeaderLine hl1 = new ResponseLine("HTTP/1.1", "200", "OK");
		Header h = new Header(hl1);

		HeaderLine hl2 = h.getHeaderLine();

		boolean evaluate = hl1.equals(hl2);

		assertTrue(evaluate);
	}

	@Test
	public void testGetHeaderLine3() {
		HeaderLine hl1 = new RequestLine("method", "URI", "version");

		Header h = new Header(hl1);
		HeaderLine hl2 = h.getHeaderLine();

		boolean evaluate = hl1.toString().equals(hl2.toString());

		assertTrue(evaluate);
	}

	@Test
	public void testGetHeaderLine4() {
		HeaderLine hl1 = new RequestLine("method", "URI", "version");

		Header h = new Header(hl1);
		HeaderLine hl2 = h.getHeaderLine();

		boolean evaluate = hl1.equals(hl2);

		assertTrue(evaluate);
	}

	@Test
	public void testGetHeaderLine5() {
		HeaderLine hl1 = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);

		Header h = new Header(hl1);
		HeaderLine hl2 = h.getHeaderLine();

		boolean evaluate = hl1.toString().equals(hl2.toString());

		assertTrue(evaluate);
	}

	@Test
	public void testGetHeaderLine6() {
		RequestLine rl1 = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);

		Header h = new Header(rl1);
		HeaderLine rl2 = h.getHeaderLine();

		boolean evaluate = rl1.toString().equals(rl2.toString());

		assertTrue(evaluate);
	}

	@Test
	public void testGetHeaderLine7() {
		RequestLine rl1 = new RequestLine("method", "URI", "version");
		HeaderField hf1 = new HeaderField("Connection", "close");
		HeaderField hf2 = new HeaderField("Server", "working");
		List<HeaderField> fields = new ArrayList<>();
		fields.add(hf1);
		fields.add(hf2);

		Header h = new Header(rl1);
		HeaderLine rl2 = h.getHeaderLine();

		boolean evaluate = rl1.equals(rl2);

		assertTrue(evaluate);
	}

	@Test
	public void testAddField1() {
		HeaderLine hl = new RequestLine("method", "URI", "version");

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Connection", "open");
		HeaderField hf3 = new HeaderField("Network", "open");

		Header h = new Header(hl);
		h.addField(hf2);
		h.addField(hf3);

		assertTrue(h.getField("Connection").equals(hf2));
		assertFalse(h.getField("Connection").equals(hf1));
		assertTrue(h.getField("Network").equals(hf3));
	}

	@Test
	public void testAddField2() {
		HeaderLine hl = new RequestLine("method", "URI", "version");

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Connection", "open");
		HeaderField hf3 = new HeaderField("Network", "open");
		HeaderField hf4 = new HeaderField("Network", "done");
		HeaderField hf5 = new HeaderField("Network", "revived");

		Header h = new Header(hl);
		h.addField(hf2);
		h.addField(hf3);

		assertTrue(h.getField("Network").equals(hf3));

		h.addField(hf4);
		h.addField(hf1);

		assertTrue(h.getField("Connection").equals(hf1));
		assertTrue(h.getField("Network").equals(hf4));

		h.addField(hf5);
		h.addField(hf2);

		assertTrue(h.getField("Connection").equals(hf2));
		assertTrue(h.getField("Network").equals(hf5));

		h.addField(hf3);

		assertTrue(h.getField("Network").equals(hf3));
	}

	@Test
	public void testAddField3() {
		HeaderLine hl = new ResponseLine("HTTP/1.1", "200", "OK");

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Connection", "open");
		HeaderField hf3 = new HeaderField("Network", "open");
		HeaderField hf4 = new HeaderField("Network", "done");
		HeaderField hf5 = new HeaderField("Network", "revived");

		Header h = new Header(hl);

		h.addField(hf2);
		h.addField(hf3);

		assertTrue(h.getField("Network").equals(hf3));

		h.addField(hf4);
		h.addField(hf1);

		assertTrue(h.getField("Connection").equals(hf1));
		assertTrue(h.getField("Network").equals(hf4));

		h.addField(hf5);
		h.addField(hf2);

		assertTrue(h.getField("Connection").equals(hf2));
		assertTrue(h.getField("Network").equals(hf5));

		h.addField(hf3);

		assertTrue(h.getField("Network").equals(hf3));
	}

	@Test
	public void testAddField4() {
		RequestLine rl = new RequestLine("method", "URI", "version");
		List<HeaderField> fields = new ArrayList<>();

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Network", "open");
		fields.add(hf1);
		fields.add(hf2);

		Header h = new Header(rl, fields);

		HeaderField hf3 = new HeaderField("Connection", "open");
		HeaderField hf4 = new HeaderField("Network", "done");
		HeaderField hf5 = new HeaderField("Network", "revived");

		assertTrue(h.getField("Connection").equals(hf1));

		h.addField(hf3);
		h.addField(hf4);

		assertTrue(h.getField("Network").equals(hf4));

		h.addField(hf5);
		h.addField(hf1);

		assertTrue(h.getField("Connection").equals(hf1));
		assertTrue(h.getField("Network").equals(hf5));

		h.addField(hf1);
		h.addField(hf2);

		assertTrue(h.getField("Connection").equals(hf1));
		assertTrue(h.getField("Network").equals(hf2));

		h.addField(hf3);

		assertTrue(h.getField("Connection").equals(hf3));
	}

	@Test
	public void testSetHeaderLine1() {
		HeaderLine hl1 = new RequestLine("method", "URI", "version");
		Header h = new Header(hl1);

		HeaderLine hl2 = new RequestLine("table", "URL", "vista");
		h.setHeaderLine(hl2);

		assertTrue(h.getHeaderLine().equals(hl2));
	}

	@Test
	public void testSetHeaderLine2() {
		HeaderLine hl1 = new RequestLine("method", "URI", "version");
		Header h = new Header(hl1);

		HeaderLine hl2 = new ResponseLine("1", "200", "okay");
		h.setHeaderLine(hl2);

		assertTrue(h.getHeaderLine().equals(hl2));
	}

	@Test
	public void testSetHeaderLine3() {
		HeaderLine hl1 = new ResponseLine("method", "URI", "version");
		Header h = new Header(hl1);

		HeaderLine hl2 = new ResponseLine("1", "200", "okay");
		h.setHeaderLine(hl2);

		assertTrue(h.getHeaderLine().equals(hl2));
	}

	@Test
	public void testSetHeaderLine4() {
		HeaderLine hl1 = new ResponseLine("method", "URI", "version");
		Header h = new Header(hl1);

		HeaderLine hl2 = new RequestLine("1", "200", "okay");
		h.setHeaderLine(hl2);

		assertTrue(h.getHeaderLine().equals(hl2));
	}

	// To do --> Andreas
	@Test
	public void testMerge1() {
		HeaderLine hl1 = new ResponseLine("method", "URI", "version");
		Header h1 = new Header(hl1);

		HeaderLine hl2 = new ResponseLine("method", "URI", "version");
		Header h2 = new Header(hl2);

		//HeaderField hf1 = new HeaderField("Connection", "closed");
		h1.merge(h2);

		assertTrue(h1.getHeaderLine().equals(hl2));

	}

	/*// To do --> Andreas
	@Test
	public void testMerge2() {
		HeaderLine hl1 = new ResponseLine("method", "URI", "version");
		Header h1 = new Header(hl1);

		HeaderLine hl2 = new RequestLine("method", "URI", "version");
		Header h2 = new Header(hl2);

	}

	// To do --> Andreas
	@Test
	public void testMerge3() {
		HeaderLine hl1 = new ResponseLine("method", "URI", "version");
		Header h1 = new Header(hl1);

		RequestLine rl1 = new RequestLine("method", "URI", "version");
		List<HeaderField> fields = new ArrayList<>();

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Network", "open");
		fields.add(hf1);
		fields.add(hf2);

		Header h2 = new Header(rl1, fields);
	}*/

	// To do --> Andreas
	@Test
	public void testMerge4() {
		HeaderLine hl1 = new RequestLine("method", "URI", "version");
		Header h1 = new Header(hl1);

		HeaderLine hl2 = new ResponseLine("method", "URI", "version");
		Header h2 = new Header(hl2);

		//HeaderField hf1 = new HeaderField("Connection", "closed");

		h1.merge(h2);

		assertTrue(h1.getHeaderLine().equals(hl2));
	}

	@Test
	public void testMerge5() {
		Header h1 = new Header(null);
		Header h2 = new Header(null);
		
		h1.merge(h2);
		
		assertNull(h1.getHeaderLine());
	}

	/*// To do --> Andreas
	@Test
	public void testMerge6() {
		HeaderLine hl1 = new RequestLine("method", "URI", "version");
		Header h1 = new Header(hl1);

		RequestLine rl1 = new RequestLine("method", "URI", "version");
		List<HeaderField> fields = new ArrayList<>();

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Network", "open");
		fields.add(hf1);
		fields.add(hf2);

		Header h2 = new Header(rl1, fields);

	}

	// To do --> Andreas
	@Test
	public void testMerge7() {
		RequestLine rl1 = new RequestLine("method", "URI", "version");
		List<HeaderField> fields = new ArrayList<>();

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Network", "open");
		fields.add(hf1);
		fields.add(hf2);

		Header h1 = new Header(rl1, fields);

		HeaderLine hl2 = new ResponseLine("method", "URI", "version");
		Header h2 = new Header(hl2);

	}

	// To do --> Andreas
	@Test
	public void testMerge8() {
		RequestLine rl1 = new RequestLine("method", "URI", "version");
		List<HeaderField> fields = new ArrayList<>();

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Network", "open");
		fields.add(hf1);
		fields.add(hf2);

		Header h1 = new Header(rl1, fields);

		HeaderLine hl2 = new RequestLine("method", "URI", "version");
		Header h2 = new Header(hl2);

	}

	// To do --> Andreas
	@Test
	public void testMerge9() {
		RequestLine rl1 = new RequestLine("method", "URI", "version");
		List<HeaderField> fields1 = new ArrayList<>();

		HeaderField hf1 = new HeaderField("Connection", "closed");
		HeaderField hf2 = new HeaderField("Network", "open");
		fields1.add(hf1);
		fields1.add(hf2);

		Header h1 = new Header(rl1, fields1);

		RequestLine rl2 = new RequestLine("method", "URI", "version");
		List<HeaderField> fields2 = new ArrayList<>();

		HeaderField hf3 = new HeaderField("Connection", "closed");
		HeaderField hf4 = new HeaderField("Network", "open");
		HeaderField hf5 = new HeaderField("Socket", "working");
		fields2.add(hf3);
		fields2.add(hf4);
		fields2.add(hf5);

		Header h2 = new Header(rl2, fields2);
	}*/

	@Test
	public void testEquals1() 
	{
		Header header1 = new Header();
		Header header2 = new Header();
		assertTrue(header1.equals(header2));
	}

	@Test
	public void testEquals2() 
	{
		Header header1 = new Header();
		assertFalse(header1.equals(null));
	}

	@Test
	public void testEquals3() 
	{
		Header header = new Header();
		assertFalse(header.equals(76));
	}
}

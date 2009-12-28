package net.collegeman.geshi4j;

import junit.framework.*;

public class GeshiTest extends TestCase {
	
	public void testGeshi() {
		Geshi geshi = new Geshi("System.out.println(\"Hello, world!\");");
		assertEquals("<pre class=\"java\" style=\"font-family:monospace;\"><a href=\"http://www.google.com/search?hl=en&amp;q=allinurl%3Asystem+java.sun.com&amp;btnI=I%27m%20Feeling%20Lucky\"><span style=\"color: #003399;\">System</span></a>.<span style=\"color: #006633;\">out</span>.<span style=\"color: #006633;\">println</span><span style=\"color: #009900;\">&#40;</span><span style=\"color: #0000ff;\">&quot;Hello, world!&quot;</span><span style=\"color: #009900;\">&#41;</span><span style=\"color: #339933;\">;</span></pre>", geshi.parseCode());
		
		geshi.setSource("Integer myInt = 4;");
		assertEquals("<pre class=\"java\" style=\"font-family:monospace;\"><a href=\"http://www.google.com/search?hl=en&amp;q=allinurl%3Ainteger+java.sun.com&amp;btnI=I%27m%20Feeling%20Lucky\"><span style=\"color: #003399;\">Integer</span></a> myInt <span style=\"color: #339933;\">=</span> <span style=\"color: #cc66cc;\">4</span><span style=\"color: #339933;\">;</span></pre>", geshi.parseCode());
	}
	
}


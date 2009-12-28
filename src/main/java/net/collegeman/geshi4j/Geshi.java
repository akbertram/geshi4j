package net.collegeman.geshi4j;

/**
 * GeSHi4J Generic Syntax Highlighter for Java
 * Copyright (C) 2009-2010 Collegeman.net, LLC.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

import net.collegeman.phpinjava.*;

import java.io.*;

/**
 * <p>GeSHi, or <em>Generic Syntax Highlighter</em>, is a PHP library that transforms
 * source code (a list of <a href="http://qbnz.com/highlighter/faq.php" target="_top">supported languages</a> and markup syntax are listed on the
 * GeSHi Web site) into properly annotated and color-coded, standards-compliant HTML.</p>
 *
 * <p>You read correctly: GeSHi is a <a href="http://php.net" target="_top">PHP</a> library.
 * GeSHi4J is a Java wrapper made possible by the <a href="http://github.com/collegeman/php-in-java" target="_top">PHP-in-Java</a>
 * project and <a href="http://caucho.com" target="_top">Caucho's</a> Quercus PHP interpter for Java.
 * (That's a really long way of saying we didn't have to rewrite GeSHi into Java, but you
 * can use it in Java, Groovy, and Grails, without needing to have anything other than
 * a handful of Java libraries.)</p>
 *
 * <h3>Using GeSHi4J</h3>
 * <p>A complete user guide is available in the project <a href="http://github.com/collegeman/geshi4j" target="_top">README on GitHub</a>.</p>
 */ 
public class Geshi {
	
	private static Integer GESHI_HEADER_PRE_TABLE = 4;
	
	private static Integer GESHI_HEADER_PRE = 2;
	
	private PHP php = new PHP("classpath:/net/collegeman/geshi4j/php/geshi.php");
	
	private PHPObject instance;
	
	private String sourceCode;
	
	private String lang;
	
	/**
	 * Create uninitialized instance of <code>GeSHi</code>
	 */
	public Geshi() {
		init();
	}
	
	/**
	 * Create an instance of <code>GeSHi</code> to highlight Java <code>sourceCode</code>.
	 */
	public Geshi(String sourceCode) {
		this(sourceCode, "java");
	}
	
	/**
	 * Create an instance of <code>GeSHi</code> to highlight Java 
	 * source code read from the file <code>file</code>.
	 */
	public Geshi(File file) {
		this(file, "java");
	}
	
	/**
	 * Create an instance of <code>GeSHi</code> to highlight source 
	 * code of language <code>lang</code> read from the file <code>file</code>. 
	 */
	public Geshi(File file, String lang) {
		this.sourceCode = readFile(file);
		this.lang = lang;
		init();
	}
	
	/**
	 * Read file <code>file</code>
	 * @return The content of <code>file</code>
	 */
	private String readFile(File file) {
		try {
			StringBuilder sourceCode = new StringBuilder(1024);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			char[] buffer = new char[1024];
			int read = 0;
			while ((read = reader.read(buffer)) != -1)
				sourceCode.append(buffer, 0, read);
			reader.close();
			return sourceCode.toString();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Create an instance of <code>GeSHi</code> to highlight <code>sourceCode</code> written in language <code>lang</code>.
	 */
	public Geshi(String sourceCode, String lang) {
		this.sourceCode = sourceCode;
		this.lang = lang;
		init();
	}
	
	private void init() {
		instance = php.newInstance("GeSHi", sourceCode, lang);
	}
	
	public Geshi useTableLayout() {
		return setHeaderType(GESHI_HEADER_PRE_TABLE);
	}
	
	public Geshi usePreLayout() {
		return setHeaderType(GESHI_HEADER_PRE);
	}
	
	public Geshi setHeaderType(Integer type) {
		instance.invokeMethod("set_header_type", type);
		return this;
	}
	
	/**
	 * CSS classes should be used to highlight the source, instead
	 * of inline CSS using the HTML <code>style</code> attribute.
	 */
	public Geshi enableClasses() {
		instance.invokeMethod("enable_classes", true);
		return this;
	}
	
	/**
	 * Inline CSS using the HTML <code>style</code> attribute should
	 * be used to highlight the source, instead of CSS classes.
	 */
	public Geshi disableClasses() {
		instance.invokeMethod("enable_classes", false);
		return this;
	}
	
	/**
	 * Enable line numbering, highlighting every 5th line.
	 */
	public Geshi enableLineNumbers() {
		enableLineNumbers(5);
		return this;
	}
	
	/**
	 * Enable line numbering, highlighting every <code>nthRow</code> row.
	 */
	public Geshi enableLineNumbers(Integer nthRow) {
		instance.invokeMethod("enable_line_numbers", true, nthRow);
		return this;
	}
	
	/**
	 * Disable line numbering.
	 */
	public Geshi disableLineNumbers() {
		instance.invokeMethod("enable_line_numbers", false);
		return this;
	}
	
	/**
	 * Sets the tab width equivalent to quantity of spaces. The default is 8.
	 */	
	public Geshi setTabWidth(Integer width) {
		instance.invokeMethod("set_tab_width", width);
		return this;
	}
	
	/**
	 * Place new source code <code>sourceCode</code> into an existing instance
	 * of <code>Geshi</code>.
	 */
	public Geshi setSource(String sourceCode) {
		instance.invokeMethod("set_source", sourceCode);
		return this;
	}
	
	/**
	 * Change languages to <code>lang</code>
	 */
	public Geshi setLanguage(String lang) {
		instance.invokeMethod("set_language", lang);
		return this;
	}
	
	public String parseCode() {
		php.getEnv().resetTimeout();
		PHPObject output = instance.invokeMethod("parse_code");
		String error = instance.invokeMethod("error").toString();
		return (error != null && error.length() > 0) ? error : output.toString();
	}
	
	/**
	 TODO: This will require some additional functional added to PHP-in-Java project...
	public Geshi parseCode(Writer writer) {
		
		return this;
	}
	*/
}
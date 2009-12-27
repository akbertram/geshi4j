package net.collegeman.grails.geshi;

import com.caucho.quercus.*;
import com.caucho.vfs.*;
import org.springframework.mock.web.*;
import com.caucho.util.*;
import com.caucho.quercus.env.*;
import java.lang.reflect.*;
import com.caucho.quercus.page.*;

public class Geshi {

	private static Quercus quercus = new Quercus();
	
	private String toHighlight;
	
	private String lang;
	
	private String highlighted;
	
	private Path source = new FilePath(Geshi.class.getClassLoader().getResource("net/collegeman/grails/geshi/php").getPath());
	
	private boolean enableLineNumbers = false;
	
	public Geshi(String source, String lang) {
		this.toHighlight = source;
		this.lang = lang;
	}
	
	public Geshi enableLineNumbers() {
		enableLineNumbers = true;
		return this;
	}
	
	public Geshi disableLineNumbers() {
		enableLineNumbers = false;
		return this;
	}
	
	private void parse() throws Exception {
		QuercusPage page = quercus.parse(source.lookup("geshi.php"));
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		WriterStreamImpl writer = new WriterStreamImpl();
		writer.setWriter(response.getWriter());
		WriteStream ws = new WriteStream(writer);
		ws.setNewlineString("\n");
		
		Env env = quercus.createEnv(page, ws, request, response);
		
		env.start();
		env.setGlobalValue("toHighlight", env.wrapJava(toHighlight));
		env.setGlobalValue("lang", env.wrapJava(lang));
		env.setGlobalValue("enableLineNumbers", env.wrapJava(enableLineNumbers));
		
		page.executeTop(env);
		
		env.close();
		ws.close();
		
		highlighted = response.getContentAsString();
	}
	
	public void reset() {
		clear();
	}
	
	private void clear() {
		highlighted = null;
	}
	
	public String getString() throws Exception {
		if (highlighted == null) {
			parse();
		}
		return highlighted;
	}
	
}
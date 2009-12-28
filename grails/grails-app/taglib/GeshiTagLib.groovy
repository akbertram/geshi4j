import net.collegeman.geshi4j.*

class GeshiTagLib {
	
	def geshi = { attrs, body ->
		if (!request.getAttribute('geshi.instance')) {
			request.setAttribute('geshi.instance', new Geshi())
		}
		
		def geshi = request.getAttribute('geshi.instance')
		
		if (attrs['linenumbers'] == 'false') {
			geshi.disableLineNumbers()
			geshi.usePreLayout()
		}
		else {
			geshi.enableLineNumbers()
			geshi.useTableLayout()
		}
			
		if (attrs['classes'] == 'true')
			geshi.enableClasses()
		else 
			geshi.disableClasses()
			
		if (attrs['lang'])
			geshi.setLanguage(attrs['lang'])
		else
			geshi.setLanguage('java')
			
		if (attrs['tabwidth'])
			geshi.setTabWidth(Integer.parseInt(attrs['tabwidth']))
		else
			geshi.setTabWidth(8) // the default
			
		if (!attrs['nochomp'] || !attrs['inline']) {
			geshi.setSource(body().substring(1));
		}
		else {	
			geshi.setSource(body())
		}
		
		out << geshi.parseCode()
	}

}

import net.collegeman.grails.geshi.*

class GeshiTagLib {

	def geshi = { attrs, body ->
		def geshi = new Geshi(body(), attrs['lang'])
		
		if (attrs['linenumbers'] == 'true')
			geshi.enableLineNumbers()
		
		out << geshi.getString()
	}

}

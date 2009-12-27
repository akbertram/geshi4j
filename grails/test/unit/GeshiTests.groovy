import grails.test.*
import net.collegeman.grails.geshi.*

class GeshiTests extends GrailsUnitTestCase {
    
	// we're shooting for 0 exceptions here...
	void testGeshi() {
		
		println new Geshi("echo 'Hello, world!';", 'php').getString()
		
	}

}

package analysis.RTA;

import org.junit.Test;

import junit.framework.TestCase;

/* A JUnit test that configures the argument array, then launches Soot and RTAAnalysis. 
 * For more information on basic Soot command-line arguments check this site:
 * https://github.com/Sable/soot/wiki/Introduction:-Soot-as-a-command-line-tool
*/
public class RTATests {

	@Test
	public void test1() {
		testHelper("p1", "A");
	}

	@Test
	public void test2() {
		testHelper("p2", "A");
	}

	@Test
	public void test3() {
		testHelper("p3", "Main");
	}

	@Test
	public void test4() {
		testHelper("p4", "pack1.Main");
	}

	// add more tests here !

	private void testHelper(String progPath, String mainClass){
		System.out.println("Testing /" + progPath + " -- " + mainClass + "\n");
		RTA.runRTA(progPath, mainClass);
		System.out.println("\n\n");
	}
	
}

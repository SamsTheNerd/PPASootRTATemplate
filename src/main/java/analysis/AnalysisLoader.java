package analysis;

import soot.G;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.options.Options;

import java.io.File;
import java.net.URL;

public class AnalysisLoader {

    /**
     * Loads soot with the given program and main class.
     * Does not attach analysis hooks.
     */
    public static void loadCompiledProgram(String programPath, String clsName){
        setupSoot(getCompiledProgram(programPath).getPath(), clsName);
    }

    /**
     * Gets the URL of to use as the classpath for a pre-compiled program.
     * @param programPath eg 'p1' would give the classpath for the program in src/main/resources/programs/p1
     */
    public static URL getCompiledProgram(String programPath){
        return ClassLoader.getSystemResource("compiledprograms" + File.separator + programPath );
    }

    /**
     * Loads soot for the given classpath and main class.
     */
    public static void setupSoot(String sourceDirectory, String clsName){
        G.reset(); // clear any existing settings
        Options.v().set_prepend_classpath(true);
        Options.v().set_soot_classpath(sourceDirectory); // tell soot where to look for our classes
        // load starting from our given main class.
        SootClass sc = Scene.v().loadClassAndSupport(clsName);
        sc.setApplicationClass();
        Scene.v().loadNecessaryClasses();
    }
}

package analysis.RTA;


import analysis.Analysis;

import analysis.AnalysisLoader;
import soot.G;
import soot.PackManager;
import soot.Transform;


public class RTA {

    public static void runRTA(String programPath, String clsName) {

        long startTime = System.currentTimeMillis();

        // Code hooks the RTAAnalysis then launches Soot, which traverses
        // all classes and creates and stores the appropriate constraints.
        G.reset();
        Analysis rtaAnalysis = new RTAAnalysis();
        AnalysisLoader.loadCompiledProgram(programPath, clsName);
        PackManager.v().getPack("jtp").add(new Transform("jtp.rta", rtaAnalysis));
        PackManager.v().runPacks();

        // Solves the constraints using _reachability_ analysis
        rtaAnalysis.worklistSolve();

        // Displays final result in format required for autograding
        rtaAnalysis.showResult();

        long endTime   = System.currentTimeMillis();
        System.out.println("INFO: Total running time: " + ((float)(endTime - startTime) / 1000) + " sec");

	}
	
	
}

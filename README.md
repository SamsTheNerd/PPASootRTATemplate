# Soot hw gradle template

This is a modification on the program analysis hw2 starter code that I hope can be useful in the future. 

The main change is moving to a more conventional gradle setup. This allows easier setup with all dependencies being automatically fetched from maven central. Having gradle also allows for an easy preprocessing hook where we can compile our sample programs at compile time instead of making soot do it at runtime (we therefore no longer need the rt.jar). This also lets us sidestep the environment property bug, meaning we can now use newer Java versions!

## Brief tour

The code is now separated into main and test sourcesets. This isn't strictly necessary, but I prefer to keep JUnit out of the main sourceset. For the RTA homework everything stays mostly the same, except that the code is moved into `src/main/java/analysis` to match convention. The single test file is moved into `src/test/java/analysis`.

The sample programs are separated from the homework code and treated more as resources than project code. They're now all in `src/main/resources/programs`. Before the main project code is compiled, a custom gradle task is ran that searches the programs folder and compiles each program separately, dumping them all in `build/resources/main/compiledprograms/`. So `src/../programs/p1/A.java` gets compiled to `build/../compiledprograms/p1/A.class`.

Since they're in the standard resource folder they can be accessed via `ClassLoader.getSystemResource("compiledprograms/p1")`. This should be consistent across all environments, including if they're shoved in jars. 

There's a new helper class, `AnalysisLoader` that does the work of grabbing these compiled classpaths and also setting up soot to read from a classpath/.class files. There's a helper `loadCompiledProgram(..)` that wraps these nicely. If there's a need in the future to read external .class files that weren't compiled via our gradle task, you can simply pass that directory in to the `setupSoot(..)` method, and it should work as well.

## Brief gradle usage summary/reference

Gradle doesn't require any local setup, everything it needs to run is included in the `gradle` wrapper folder, and the gradlew scripts. There is a gradle tool you can install, but it's mostly just used for creating the wrappers, and who needs that when you can just copy files. 

Gradle is based on a graph of tasks, you can run them with `./gradlew [task_name]` (or however windows does it with the `.bat`). Here's some common tasks:
- `./gradlew test`: this runs all the JUnit annotated tests in the test sourceset.
- `./gradlew build`: this just builds the project, probably not going to call this often unless you just want to test compilation for some reason.
- `./gradlew compileSamplePrograms`: this runs the custom compilation task only. 
- `./gradlew run`: not currently used since we don't have a main entrypoint, but would be used to call that. Can add `-- args "args here"` as well.

Note that gradle handles task dependency resolution, so running or testing it will automatically build it and compile the sample programs. Don't know about Eclipse, but atleast IntelliJ will pop up with a helpful gradle menu and attach the run configs to the run buttons, which is quite convenient.


## quirks and whatnot

The build file is currently set to target java 17 for both the project code and the sample programs, this can be adjusted by just changing the values in the appropriate spots. As far as I can tell, the project version doesn't need to match the sample program version, but the project version may need to be newer? When poking at the java version, you may need to change the version for the project in your IDE as well. There's not currently a way to set different java versions for different sample programs but that's probably fine.

I'm not sure how Submitty autograders work and how they feel about gradle, specifically setting everything up over and over. There's a way to use local jars instead of fetching from maven that may be helpful there. 

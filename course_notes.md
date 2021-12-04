# Lecture 1
## type conversion
- `1 + "1"` gives you `"11"`. Note that Java automatically convert integers to strings in string concatenation.
- Use `boolean` instead of control flow with `if...else...` can make your program much cleaner!

# Using a specific .jar file
- compilation: `javac -classpath .:stdlib.jar Average.java`
- running the compiled program: `java -classpath .:stdlib.jar Average`
- Alternatively, set the current `CLASSPATH` to the directory of the `.jar` file [NOT RECOMMENDED]
  - https://www.liaoxuefeng.com/wiki/1252599548343744/1260466914339296
  - https://stackoverflow.com/questions/460364/how-to-use-classes-from-jar-files
  - You can do it in the file `~/.profile`
  - But remember to `source` your config file!!
  - Use `echo $CLASSPATH` to double check that the path is set correctly

# Week 4
- Standard EOF: `Ctrl-D`

# textbook chapter 1: review of Java (how it differs from Matlab)
- Aliasing is super different from matlab!!!!
- as annoying as c, you almost always need to declare the return type of a function / type of any argument or variable…
- method names could be overloaded.
- Java is always `pass-by-value`. Unfortunately, when we deal with objects we are really dealing with object-handles called references which are passed-by-value as well. This terminology and semantics easily confuse many beginners.
- Integer overflow!!!
- Other than static methods, we actually also have OOP methods with `data abstraction`.
- Initialization is required before you assign anything to a variable.
- 0-based indexing!!

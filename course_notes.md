# Some random notes
- Important trade-offs: **resizing array vs. linked list**
- amortization analysis: [https://stackoverflow.com/questions/11102585/what-is-amortized-analysis-of-algorithms](https://stackoverflow.com/questions/11102585/what-is-amortized-analysis-of-algorithms)
- Java class could be nested! ([https://www.w3schools.com/java/java_inner_classes.asp](https://www.w3schools.com/java/java_inner_classes.asp))
    - For example, creating private iterator class within the Queue class.
- **Don’t rely solely on the autograder for debugging.** Write your own unit tests.
- Avoid casting at all your might. Type mismatch errors cannot be detected at compile.
- Best practices for using assertions
    - Use assertions to check i**nternal invariants**; assume assertions will be disabled in production code.
    - Do not use for **external argument checking**
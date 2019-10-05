# Recommender_System
SVD vs CUR DECOMPOSITION \n
It implements both the SVD and the CUR Matrix Decomposition algorithms and compares the efficiency of both these approaches (In terms of space, time etc.). 
It also shows the instances where SVD is better, and instances where CURis better. 
The pseudocode for the algorithms can be found in the slides.

Tips and Tricks:\n
Since CUR Decomposition involves use of random rows and columns for decomposing the matrix (Random Number Generator), a seed has been used to ensure that output remains consistent over multiple runs.
Corpus:
The Rating or Review dataset used is .
1. Amazon Book Reviews
2. Book Crossing
3. LibRec
Programming Language used: Java
Additional Information:
The core of both the SVD and CUR Decomposition algorithms is coded.
We can also output the results of this phase (All the three matrices that the original matrix has been decomposed to). 
The final release contains the following documents:
1. Design Document – This document should contain the description of the application’s
architecture along with the major data structures used in the project. 
2. Precision and Recall, is also calculated.
3. The code is well commented.
4. Documentation – All the classes, functions and modules of the code is documented, done using software that automatically generate such documents, Eclipse for Java etc.
5. README – The README file describes the procedure to compile and run the code for various datasets.

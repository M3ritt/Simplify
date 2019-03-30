# Simplify
# My first project that was created in Clojure
# Code created by Joshua Meritt
This program simplies boolean expressions.
The first statement of the program should only contain an expression
==> (def p2 '(and (and z false) (or x true false)))
Once the expression is defined, you can chose to supply boolean values to the expression
==>(evalexp p2 '{x false, z true})
In this case, it would set the x values within p2 to false and the z values within p2 to true
This would then be simplified to the simplist form and then returned. 

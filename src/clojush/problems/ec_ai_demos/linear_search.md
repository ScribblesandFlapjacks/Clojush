Overview
--------

For our project we chose to try to evolve a program for linear search in vectors.
We chose this program because it has both simple and more complex forms and gives a variety
of space for us to explore. The main two forms we are trying to evolve are linear search that
returns a boolean if it exists in the vector and linear search that returns the index of the target
if it exists in the vector.


Setup
-----

For our setup we used squirrel-play.clj as a template and modified it as follows.
* We added the following test cases:
    *[[7,5,4,8,1,2,3], 4]
    *[[5,5,5,5,5,5,5,5,5], 2]
    *[[8,3,0,5,8,9,4,4], 9]
    *[[8,3,0,5,8,9,4,45,4,8686,454,765,85], 0]
    *[[8,3,0,5,8,9,4,46,3,73,237,67,83,637], 1]
    *[[3,3,3,3,3,3,3], 3]
    *[[87,35,0,55,82,976,5354,674,56,45,24], 56]
    *[[], 8]
* We created two different expected-output functions, one that returns true or false based on the targets existence,
the other returns the index of the first representation of the target element in the vector if it exists.
* Made no changes to make-start-state
* Depending on which expected-output function is used the actual-output function needs to either read from the boolean stack
or the integer stack.
* Currently only using a binary error representation in the all-errors function. We could possibly use a different error
system if we continue to get failed runs.
* In atom generators we only have a set of stacks and in1/in2. For the stacks we always include :integer and :boolean
and we are testing the environment with either the :vector_integer stack by itself or with the :exec stack.
* Our argmap is default, though we have used different population sizes of 100, 500 and 1000.


Results
-------

For our first setup we used the expected-output-old function that is the simple boolean yes no output function with a population
size of 100 and the :vector_integer stack. The run ended after generation 23, with the following successful simplified program.

	program: (in1 in2 vector_integer_contains)
	errors: (0 0 0 0 0 0 0 0)
	total: 0
	size: 4

As can be seen, using the higher order function vector_integer_contains the evolutionary run was able to complete very quickly with
an incredibly simple program. Ultimately this is not a particularly interesting result as it shows that Clojush essentially had the
solution built in.

For awhile we couldn't get the second setup to have a successful run, but then we realized we had been looking at the :boolean stack
rather than the :integer stack so an error of 0 on any test case was impossible. Shwoops

For our second setup we used our expected-output function that returns the indexof the target element if it is present in the vector.
For this we perceived it being a more difficult problem and decided to use the default pop size of 500 with both the :vector_integer and
:exec stack. This is also where we looked for our results in the integer stack rather than the boolean stack. The run ended in 489 generations
with a simplified program as follows

	program: (in1 in2 vector_integer_indexof)
	errors: (0 0 0 0 0 0 0 0)
	total: 0
	size: 4

It is a bit surprising that it took so many more generations to once again find a built in function that completes the entirety of the task.
Our hypothesis is that by looking at the :integer stack we have increased the possible values to explore.


Because both of these resulting programs are kind of boring we decided to try one more setup with a population size of 500, boolean form and only the
:exec stack. At first we were getting a situation where only our 3rd or 7th test case was failing. Both of these test cases have a targeted
element of 8 and so we changed the target in case 3 to 9, returning the same result for that case and the program solved it in 5 generations. In
hindsight we don't think that the program can interact with the input vectors at all and therefore the solutiion that Clojush evolved is just a successful
way of bullshitting tbe relatively few test cases we have.

#Work sessions

I did this project in three work session :
- 1 hour to clone, compile, repair gradle and tests, read the code and think how I will approach the challenge.
- 1 hour 10 minutes for implementing most of the features, refactor the code and the unit tests
- 50 minutes to add the printing format feature (reverse item and price) + writing this report, adding new tests and make a creative extension.

# Steps followed, time tracking and thought process

##Getting started with the project (1h)

I started by cloning the directory and exploring the project without touching a line of code. I took notes on the approaches I was considering and the tasks to be performed.

I then modified build.gradle to make it compatible with my environment (Gradle 7).
I also added a formatting option (decimal separator "." vs ",") to a method so that the unit tests would pass.

All this took me an hour in total, and I almost only had to execute what I had planned to do next.

##Code improvements (20 min)
### Refactoring addItem (less line of code, more readable) (5 min)
Using `getOrDefault` I managed to make the method much more concise but still readable.
### Some improvements of the class (5 min)
- Made fields private final
- Research on a map keeping the insertion ordering, found about `LinkedHashMap`.
Other possible approach would have been to use a `TreeMap` and define ordering myself. But `TreeMap` runs in `O(log(n))` for most operations whereas `LinkedHashMap` runs in `O(1)`.

###Improving printReceipt (10 min)
Less repetition, more readable (stop repeating `contents.get(keys[i])` and `keys[i]`), got rid of Float and Integer, used primitive types instead.


After refactoring, I verified that the code still passed unit tests.

##Adding "total" to the receipt (5 min)
Just a few changes to `printReceipt`.

##Refactoring of unit tests (45 min)
This was definitely the most challenging part because there was no "perfect" solution, and I'm still not 100% sure that I made the best choice.

Thought process :

When writing unit tests, the developer needed to rewrite the exact expected String for each test.
That is clearly a problem because it leads to a lot of duplication, and one change in the class `ShoppingCart` creates the need to modify each test.

Solving this problem isn't simple. The first thing that came to my mind was to directly perform checks on the content of the data structure in `ShoppingCart`.
This would have enabled to decouple a lot the formatting of the receipt from the tests.

However, this is not a good practice. Because the map is related to the inner working of the class and unit tests shouldn't care about that. This map should be made private.

Unit tests should only relate to the public interface of a class, so I had to restrict my testing to the use of the methods `addItem` and `printReceipt`.

Actions : 

I began by refactoring the tests a bit, because many lines were repeated for capturing the output stream and testing its content.
So I created the helper method `assertReceiptHasExpectedContent`.

Then, I choose to mimic a bit the behavior of the `ShoppingCart` class by creating a helper private method in the test class to build the content of the receipt.
It takes three arguments (three primitives array of Strings, floats and ints) which correspond to the expected content of the lines of the receipt (item name, price and quantity).
The methods rebuild the expected content and returns it as a String. 
The big advantage is that each test can now focus on the inner working of the class. If the receipt format changes, a few changes to this helper method avoid rewriting everything.

It offers an easy interface to quickly write new tests (just know what you expect from the receipt and write it as three arrays).
However, it leads to the need of writing twice the print logic.

##Supporting different formatting options (20 min)
I created an ENUM to represent different formatting options and enable to add more in the future
I also had to adapt the test cases,

##Writing this report (30 min)

# Creative extensions
I ran out of time before being able to develop an extension.




Solution
----------------------------
Python 2.7 and 3.4 compatible.

* To run tests:
>python runtests.py

* To run program:
>python main.py

Requirements
----------------------------
* Time is not constrained, feel free to solve it whenever you're able. Just don't forget to communicate with us if you couldn't find a free evening for a couple of weeks :-)
* Feel free to pick your favourite programming language, no constraints here either.
* Solution should match philosophy described above.
* Using additional libraries is prohibited. That constraint is not applied for unit tests and build.
* There should be an easy way to start the solution and tests. (in Ruby case, it could be something like: "rake run input.txt", "rake test")
* Short documentation of design decisions and assumptions can be provided in code itself.

Problem
----------------------------
The local commuter boatyard services a number of suburbs in Venice. Because of monetary concerns, all of the canals are â€˜one-way.â€™ That is, a route from suburb A to suburb B does not imply the existence of a route from suburb B to suburb A. In fact, even if both of these routes do happen to exist, they are distinct and are not necessarily the same distance!

The purpose of this problem is to help the boatyard provide its customers with information about the routes. In particular, you will compute the distance along a certain route, the number of different routes between two suburbs, and the shortest route between two suburbs.

Input: A directed graph where a node represents a suburb and an edge represents a route between two suburbs. The weighting of the edge represents the distance between the two suburbs. A given route will never appear more than once, and for a given route, the starting and ending suburb will not be the same suburb.

Output: For test input 1 through 5, if no such route exists, output â€˜NO SUCH ROUTEâ€™. Otherwise, follow the route as given; do not make any extra stops! For example, the first problem means to start at suburb A, then travel directly to suburb B (a distance of 5), then directly to suburb C (a distance of 4).

1. The distance of the route A-B-C.
2. The distance of the route A-D.
3. The distance of the route A-D-C.
4. The distance of the route A-E-B-C-D.
5. The distance of the route A-E-D.
6. The number of trips starting at C and ending at C with a maximum of 3 stops. In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
7. The number of trips starting at A and ending at C with exactly 4 stops. In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
8. The length of the shortest route (in terms of distance to travel) from A to C.
9. The length of the shortest route (in terms of distance to travel) from B to B.
10. The number of different routes from C to C with a distance of less than 30. In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.

Sample input:

AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
For the test input, the suburbs are named using the first few letters of the alphabet from A to E. A route between two suburbs (A to B) with a distance of 5 is represented as AB5.

Expected output:

```
#1: 9
#2: 5
#3: 13
#4: 22
#5: NO SUCH ROUTE
#6: 2
#7: 3
#8: 9
#9: 9
#10: 7
```
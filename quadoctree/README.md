----

### Mini-project for *Data Structure Algorithms* lecture

The task was to imeplement [Quadtree (or q-tree)][qtree] data structure from [this][publication] publication.


> A quadtree is a tree data structure in which each internal node has exactly four children. Quadtrees are most often used to partition a two-dimensional space by recursively subdividing it into four quadrants or regions. The regions may be square or rectangular, or may have arbitrary shapes.

An example preview:

<p align="center">
  <img src="/quadoctree/static/pub1.png" height="50%" width="50%"/>
</p>


### Implementation

An interactive Python application where one can draw obstacles, place a red point. Then generate q-tree structure and using that structure effectively find if the placed red point is inside an obstacle or not.

* **Requirements**:
  + Python 3.4
  + Pygame
  + Fabric (for automatic setup/launching)


Also Pygame's dependencies for Ubuntu:
```
sudo apt-get build-dep python-pygame
sudo apt-get install python-dev
sudo apt-get install mercurial
```

* **Setup**:
 + Automatic setup (Ubuntu only): `fab setup`

* **Running** the app:
 + `fab go`

* **Sample** screens:

<p align="center">
  <img src="/quadoctree/static/1.png" height="80%" width="80%"/>
  <img src="/quadoctree/static/2.png" height="80%" width="80%"/>
</p>

----
[qtree]: http://en.wikipedia.org/wiki/Quadtree
[publication]: /quadoctree/static/quadoctree.pdf


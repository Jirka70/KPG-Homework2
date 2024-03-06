# MAZE GENERATOR - Jiri Tresohlavy
**Tasks**
-  Find some maze generator algorithm and implement them (2 pts.)
-  Introduce maze solving algorithm, which will find a route from start point to the end point (2 pts.)
-  Animate maze generating step by step (2 pts.)
-  Animate maze solving algorithm (2 pts.)


**GUI Framework**
- Whole project is made in `JavaFX` framework, because I have large experience with that

**Launching**

From IDE:
  - Pull this repository to your IDE with Java
  - Load maven dependencies by executing `pom.xml` (only if your IDE does not load `pom.xml` automatically)
  - Launch `org.kpg2.Main` class

Algorithm of maze generation was taken from here:

`https://www.itnetwork.cz/algoritmy/bludiste/algoritmus-tvorba-nahodneho-bludiste`



Final program is able to animate creation of maze, then also maze solving algorithm is animated step-by-step. 
For the maze solving, I have chosen BFS algorithm, because it is not tough to implement and in the result, 
the animation is much better then for instance animation of DFS. 

All the classes, which take care about animations are situated in the `animation` folder. On the beginning,
`MazeAnimation` class is called (see line 30 in `MazeApp`), which basically animates the basics of the maze.
After the `MazeAnimation` finishes, the `StretchingWallsAnimation` is played and after `StretchingWallsAnimation`
terminates, the last `MazeSolvingAnimation` is called.

Within the `MazeSolvingAnimation` is btw. implmented the BFS algorithm within the method `makeRoute()`. This mentioned
method basically creates route from the starting point `[1, 1]` to the ending point, which is on the other side of the maze.


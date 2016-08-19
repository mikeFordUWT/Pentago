Michael Ford
TCSS 435
Summer 2016

I wrote this program in Java 8 and built it in JetBrains IntelliJ IDEA.
This code is also on github at: https://github.com/mikeFordUWT/Pentago.git

Algorithm data and analysis.

The alpha-beta pruning optimization of the minimax algorithm is by far much faster.
At a look ahead of a 3 depth tree Alpha-Beta looked at a total of 21432 vs.
Minimax's 220803 for a difference of 199371 less nodes expanded over the course
of the game.

At a look ahead of a 4 depth tree Alpha-Beta looked at a total of 173619 vs.
Minimax's 5825666 for a difference of 5652047 less nodes expanded over the
course of the game.

With all this in mind the advantage of Alpha-Beta pruning is evident and based on the
difference in time complexities, could potentially double the depth of a look
ahead game tree in the same amount of time.

MINIMAX:
TIME COMPLEXITY: O(b^m)
SPACE COMPLEXITY: O(bm)
DEPTH 3:

[EMPTY BOARD]
TURN 1: 44722
TURN 2: 37646
TURN 3: 31370
TURN 4: 25346
TURN 5: 21026
TURN 6: 16862
TURN 7: 13306
TURN 8: 10310
TURN 9: 7826
TURN 10: 5806
TURN 11: 4202
TURN 12: 2381
--------------
TOTAL: 220803
GAME ENDED WITH AI WIN

DEPTH 4:
[EMPTY BOARD]
TURN 1: 1462538
TURN 2: 1154766
TURN 3: 898506
TURN 4: 687662
TURN 5: 516522
TURN 6: 379758
TURN 7: 272426
TURN 8: 189966
TURN 9: 128202
TURN 10: 83342
TURN 11: 51978
---------------
TOTAL: 5825666
GAME ENDED WITH USER WIN

ALPHA-BETA PRUNING:
TIME COMPLEXITY: O(b^(m/2))
SPACE COMPLEXITY: O(bm)
DEPTH 3:

[EMPTY BOARD]
TURN 1: 2388
TURN 2: 2857
TURN 3: 2438
TURN 4: 3959
TURN 5: 1514
TURN 6: 2499
TURN 7: 2405
TURN 8: 1041
TURN 9: 2331
---------------
TOTAL: 21432
GAME ENDED WUTH USER WIN

DEPTH 4:

[EMPTY BOARD]
TURN 1: 13129
TURN 2: 14943
TURN 3: 24240
TURN 4: 11439
TURN 5: 18844
TURN 6: 9910
TURN 7: 25942
TURN 8: 12681
TURN 9: 18129
TURN 10: 4992
TURN 11: 5494
TURN 12: 8185
TURN 13: 4801
TURN 14: 890
-----------------
TOTAL: 173619

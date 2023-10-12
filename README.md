# Battle-Salvo-Winner
We developed an innovative Java algorithm to master a unique version of the classic game, Battleship. 


The Game Rules are as follows: ### Board Size

BattleSalvo grids, instead of being 10x10, can have height and width dimensions of any value between 6 and 15 (inclusive)! Height and width dimensions do not need to match. For example, 6x10 and 9x6 would both be valid board dimensions for a game. The size of the board for each player, however, must be identical.

### Ship/Boat Sizes

In Battleship, each boat is positioned horizontally in one row or vertically in one column, and covers a specific number of cells on the board.

In traditional Battleship, the boat sizes are:

- Carrier: Size 5
- Battleship: Size 4
- Destroyer: Size 4
- Submarine: Size 3
- Patrol Boat: Size 2

**BattleSalvo is a bit different**:

- Carrier: Size 6
- Battleship: Size 5
- Destroyer: Size 4
- Submarine: Size 3

### Fleet Size

In Battleship, there is one of each boat type. But, in BattleSalvo, there may be several of each boat type.

The total fleet size may not exceed the smaller board dimension, but must have at least one of each boat type. Therefore, for a board of size 8x11, there should be no more than 8 total boats. Fleet size and boat types will be identical between players. 

### Number of Shots

In traditional Battleship, a player launches a missile once per turn. In BattleSalvo, for each turn, each player launches one missile per non-sunk boat remaining in their fleet.  For example, if you currently have 3 remaining ships in your fleet, you would launch 3 missiles. At the same point in time, if your opponent had 5 ships remaining, they would be able to launch 5 missiles.

### Shooting Order

In BattleSalvo, both players select their shots (target locations), exchanging them simultaneously. Information about hits is then exchanged, surviving ships are updated, and the process is repeated until one (or both) players have no more surviving ships. Importantly, this means some games will end in ties!

More specifically, the steps for the shooting stage of Salvo are laid out below:

1. Both Players shoot their “Salvo’s”
2. Both Players receive the “Incoming” salvo that their opponent fired
3. Both Players update their ships accordingly and communicate which of the incoming shots hit
4. Repeat





# Strategy
To tackle this project, we developed a strategy to handle each step of the game: Placing ships, firing shots, and processing shot results.

## Firing Shots
To fire shots, we developed a complex probability map. To fire a shot, our algorithm sorts the list of cells remaining by probability and fires shots at the highest probability cells

### Probabilities
Firstly, all cells are assigned an initial probability based on how likely the cell is to contain a ship. To do this economically, we did the distance formula for each cell from the center. This looked roughly similar to this map: ![image](https://github.com/siondw/Battle-Salvo-Winner/assets/124106512/be77e4c0-c75d-47f8-bab0-eca69224e333)

### Parity
Additionally, we introduced the idea of Parity. Succinctly, Parity is the idea of firing in a pattern that lets you eliminate cells that weren't fired at based on the shots you have fired. For example, if a cell is surrounded by misses, it is impossible for it to contain a ship. Based on our initial map, our highest probability calls were always in the middle of the board and touching each other. This was inefficient. So in order to increase parity, we boosted probabilities for every other cell so that we would fire in checkered pattern.

## Processing Shot Results
- Hits
  - When we receive feedback that our shot hit, we boost the probabilities of the immediately adjacent cells in order to fire at them next.
  - For cells two cells away, we adjust the probability as well but by significantly less.
  - By doing this, on our next round of shots, we shoot at all shots surrounding the hit in order to determine where the rest of the ship is.
  - When we have multiple shots in one direction, we determine the orientation of the ship and stop firing in a ring around a hit cell and only shoot in the ship's direction.
- Misses
  - When we receive feedback of a miss, we decrease the probability of all surrounding cells by 30%, because they are less likely to have a ship if an adjacent cell is a miss.
- Parity Check
  - At the end of each round, we also run a check throughout the whole board and eliminate cells that certainly contain no ships.


# Placing Ships
As directed in the rules, each team is responsible for creating valid ship placements on randomized board sizes and ship counts. To do this, we would randomly select an unoccupied cell, and then determine if the current ship we were placing would fit horizontally on it. If it couldn't, we rotate the ship 90 degrees and try and place it vertically. If the ship fit, it was placed. If not, a new cell was selected until the ship was fit.

## Meta-Strategy
To win this tournament, we understood that we would need some sort of meta-strategy to get ahead of our clever competition. By anticipating that other students may have a similar probability strategy, we made sure to counteract by making a clever tweak to our ship-placing algorithm. Instead of selecting ship placements randomly, we sorted our cells by *least* likely to contain a ship and placed our ships on those cells. This ultimately paid off in the final round when our final two ships were in the corners and we defeated our opponent.

 

# 3DTicTacToe
This was a project I did to familiarize myself with working with GUIs. Specifically, I used the Swing library in the Java library. Because I was learning, I locked it to 720p. The project is broken down into a few components. 

There is the Game class, which pretty much holds all the conceptual information to make the game possible. Every other class is just a custom jPanel used for displaying that specific part of the game. The TicTacToePanel displays the 3D stack of grids on the left side of the screen, the Grid panel displays the right side of the screen, which is just whatever 2D grid the player selects. The WinScreen is exactly what it sounds like. test is the main class, I just use it to make an instance of Game.

In addition to displaying the game, the panel classes also have mouse listeners attached to recognize the input of the player. I used a relatively naive approach, and would parse coordinates of the panel based on how I drew the graphics, so there are no buttons. This is glaringly obvious by the "Click to rotate 90 degrees" text, which definitely is not a button. I just portioned of the coordinates and would rotate if the mouse was clicked in that area.

As far as improvements go, I want to remove a lot of the magic numbers out of the graphics classes. I would do this by dynamically resizing the game according to the window's size. There is also a bug with resetting that I just never quit got around to fixing. Be careful when you "press anywhere to continue" from the win screen!

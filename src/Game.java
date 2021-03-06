import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;

/*
 * a singleton class
 * a 1 in the grid is an X
 * a -1 in the grid is an O
 * z,y,x
 */
public class Game {
	// game vars
	int[][][] masterGrid;
	int length;
	int player;

	// visual vars
	TicTacToePanel tttp;
	Grid grid;
	JFrame frame;
	WinScreen win;
	int selectedGrid;

	// constructor
	static Game g;

	private Game() {
		length = 4;
		player = 1; // X starts
		masterGrid = new int[length][length][length];

		for (int x = 0; x < length; x++) {
			for (int y = 0; x < length; x++) {
				for (int z = 0; x < length; x++) {
					masterGrid[x][y][z] = 0;
				}
			}
		}

		frame = new JFrame("3D Tic-Tac-Toe!"); // window name
		frame.setLayout(null);
		frame.setSize(1280, 720);
		frame.setMinimumSize(new Dimension(1280, 760));

		tttp = new TicTacToePanel();
		grid = new Grid();
		tttp.refresh(masterGrid);
		frame.add(tttp);
		frame.add(grid);
		frame.pack();

		frame.setVisible(true);
	}

	public static Game getInstance() {
		if (g == null) {
			g = new Game();
		}
		return g;
	}

	// sets the point in the element and sets the visual elements so they don't
	// conflict
	void setPoint(int x, int y, int z) {
		if (masterGrid[z][y][x] == 0) {
			masterGrid[z][y][x] = player;
			tttp.refresh(masterGrid);
			grid.setGrid(masterGrid[selectedGrid]);
			int c = checkForWin();
			if (c != 0) {
				if (c == -1) {
					c = 2;
				} // if player -1 = player 2
				setWinScreen(c);
			}
			setPlayer();
		}
	}

	// sets the winscreen
	private void setWinScreen(int winner) {
		frame.getContentPane().removeAll();
		win = new WinScreen(winner);
		frame.add(win);
		frame.repaint();
	}

	// updates the selected Grid that is displayed
	void setSelectedGrid(int s) {
		selectedGrid = s;
		grid.setGrid(masterGrid[s]);
	}

	// switches players
	public void setPlayer() {
		if (player == 1) {
			player = -1;
		} else if (player == -1) {
			player = 1;
		}
	}

	// ... checks to see if a player wins
	// there are 7 possible ways to win, and a loop for each way
	// empty() just returns an array of the same length with all value of zero
	// checkWinArr() returns an integer representing the player if the win arr has 4
	// in a row, otherwise it returns 0
	public int checkForWin() {
		int b = 0;
		int[] win = new int[length];

		// checking for wins along x
		for (int z = 0; z < length; z++) {
			System.out.println("stage 1");
			for (int y = 0; y < length; y++) {
				for (int x = 0; x < length; x++) {
					win[x] = masterGrid[z][y][x];
				}
				b = checkWinArr(win);
				if (b != 0)
					return b;
			}
		}

		win = empty(win);

		// checking for wins along y
		for (int z = 0; z < length; z++) {
			System.out.println("stage 2");
			for (int x = 0; x < length; x++) {
				for (int y = 0; y < length; y++) {
					win[y] = masterGrid[z][y][x];
				}
				b = checkWinArr(win);
				if (b != 0)
					return b;
			}
		}

		win = empty(win);

		// checking for wins along z
		for (int y = 0; y < length; y++) {
			System.out.println("stage 3");
			for (int x = 0; x < length; x++) {
				for (int z = 0; z < length; z++) {
					win[z] = masterGrid[z][y][x];
				}
				b = checkWinArr(win);
				if (b != 0)
					return b;
			}
		}

		win = empty(win);

		// checking for wins along x & y
		for (int z = 0; z < length; z++) {
			System.out.println("stage 4");

			// top right to bottom left
			for (int x = 0; x < length; x++) {
				if (masterGrid[z][x][x] != 0) {
					win[x] = masterGrid[z][x][x];
				}
			}
			b = checkWinArr(win);
			if (b != 0)
				return b;

			win = empty(win);

			// bottom left to top right
			for (int x = 0; x < length; x++) {
				if (masterGrid[z][(length - 1) - x][x] != 0) {
					win[x] = masterGrid[z][(length - 1) - x][x];
				}
			}
			b = checkWinArr(win);
			if (b != 0)
				return b;
		}

		win = empty(win);

		// checking for wins across z & x
		for (int y = 0; y < length; y++) {
			System.out.println("stage 5");
			// top left to bottom right
			for (int x = 0; x < length; x++) {
				if (masterGrid[x][y][x] != 0) {
					win[x] = masterGrid[x][y][x];
				}
			}
			b = checkWinArr(win);
			if (b != 0)
				return b;

			win = empty(win);

			// bottom left to top right
			for (int x = 0; x < length; x++) {
				if (masterGrid[x][y][(length - 1) - x] != 0) {
					win[x] = masterGrid[x][y][(length - 1) - x];
				}
			}
			b = checkWinArr(win);
			if (b != 0)
				return b;
		}

		win = empty(win);

		// checking for wins across z & y
		for (int x = 0; x < length; x++) {
			System.out.println("stage 6");

			// top right to bottom left
			for (int z = 0; z < length; z++) {
				if (masterGrid[z][z][x] != 0) {
					win[z] = masterGrid[z][z][x];
				}
			}
			b = checkWinArr(win);
			if (b != 0)
				return b;

			win = empty(win);

			// bottom left to top right
			for (int z = 0; z < length; z++) {
				if (masterGrid[z][(length - 1) - z][x] != 0) {
					win[z] = masterGrid[z][(length - 1) - z][x];
				}
			}
			b = checkWinArr(win);
			if (b != 0)
				return b;
		}

		win = empty(win);

		// checking for wins from opposite corner to opposite corner
		// aka across z,y,x
		int[][][] temp = masterGrid;
		for (int a = 0; a < length; a++) {
			System.out.println("stage 7");

			for (int z = 0; z < length; z++) {
				win[z] = temp[z][z][z];
			}
			b = checkWinArr(win);
			if (b != 0)
				return b;
			System.out.println("rotating...");
			temp = rotate3DArr(temp);
		}

		return b;
	}

	// will return either -1, 0, or 1
	// 0 is if the arr does not have 4 in a row or is 4 blank things in a row
	int checkWinArr(int[] arr) {
		if (arr[0] == 0) {
			return 0; // sho r t c u t
		}

		int start = 0;
		for (int a = 0; a < arr.length; a++) {
			if (arr[0] == arr[a]) {
				start++;
				System.out.println(start);
			}
		}
		if (start == length)
			return arr[0];
		else
			return 0;
	}

	int[] empty(int[] arr) {
		for (int a = 0; a < arr.length; a++)
			arr[a] = 0;
		return arr;
}

	/*
	 * precondition: arr is cubic
	 */
	public int[][][] rotate3DArr(int[][][] arr) {
		int[][][] newArr = new int[arr.length][arr[0].length][arr[0][0].length];
		for (int z = 0; z < arr.length; z++) {
			newArr[z] = rotateMatrixRight(arr[z]);
		}
		return newArr;
	}

	// credit for this algorithm goes to Martijn Courteaux on StackOverflow
	public int[][] rotateMatrixRight(int[][] matrix) {
		/* W and H are already swapped */
		int w = matrix.length;
		int h = matrix[0].length;
		int[][] ret = new int[h][w];
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				ret[i][j] = matrix[w - j - 1][i];
			}
		}
		return ret;
	}

	// resets the game
	public void reset() {
		frame.getContentPane().removeAll();
		for (int a = 0; a < length; a++) {
			for (int b = 0; b < length; b++) {
				masterGrid[a][b] = empty(masterGrid[a][b]);
			}
		}
		tttp = new TicTacToePanel();
		grid = new Grid();
		tttp.refresh(masterGrid);
		frame.add(tttp);
		frame.add(grid);
		frame.pack();
	}

	// turns a coordinate on the screen into something that can be put in the array
	public void parseGridCoord(int x, int y, int width, int height) {
		int parsedX = (int) (x - 75) / 125;
		int parsedY = (int) (y - 105) / 125;
		setPoint(parsedX, parsedY, selectedGrid);
	}

	// rotates the tttp
	public void rotateDisplay() {
		masterGrid = rotate3DArr(masterGrid);
		tttp.refresh(masterGrid);
		grid.setGrid(masterGrid[selectedGrid]);
	}
}
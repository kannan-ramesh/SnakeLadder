package com.kannanrameskrk;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class SnakeLadder {
	Scanner input = new Scanner(System.in);
	List<Integer> path = new ArrayList<>();

	public void start() {
		System.out.println("\t\tSnake Ladder");
		System.out.println("\t\t**************");
		System.out.println("Enter board details");
		System.out.println("*******************");
		System.out.println();
		System.out.println("Enter Row size:");
		int row = input.nextInt();
		System.out.println("Enter Col size:");
		int col = input.nextInt();
		System.out.println();

		int[][] arr = new int[row][col];

		fillNumber(arr);
		displayBoard(arr);

		System.out.println("Enter the number of Snakes: ");
		int snakeCount = input.nextInt();
		int[][] snake = new int[snakeCount][2];

		for (int i = 0; i < snakeCount; i++) {
			System.out.println("Snake head point:" + (i + 1) + ":");
			int head = input.nextInt();
			System.out.println("Snake Tail point:" + (i + 1) + ":");
			int tail = input.nextInt();
			snake[i][0] = head;
			snake[i][1] = tail;
			System.out.println();
		}
		System.out.println();
		System.out.println("Enter the number of Laddor: ");
		int laddorCount = input.nextInt();
		int[][] ladder = new int[laddorCount][2];

		for (int i = 0; i < laddorCount; i++) {
			System.out.println("Ladder Bottom point:" + (i + 1) + ":");
			int bottom = input.nextInt();
			System.out.println("Ladder up point:" + (i + 1) + ":");
			int up = input.nextInt();
			ladder[i][0] = bottom;
			ladder[i][1] = up;
			System.out.println();
		}

		System.out.println("Enter number of players:");
		int playerCount = input.nextInt();
		String[] players = new String[playerCount];
		int[] playerPositions = new int[playerCount];

		for (int i = 0; i < players.length; i++) {
			System.out.println("Enter player" + (i + 1) + " Name: ");
			players[i] = input.next();
		}

		System.out.println("1. Play Game");
		System.out.println("2. Find minimum path move:");
		System.out.println("3. If a player is in 95 what is the probality to win the game?:");
		System.out.println("4. exit..");
		System.out.println("ENter option:");
		int choice = input.nextInt();
		boolean loop = true;

		while (loop) {
			switch (choice) {
			case 1: {
				playGame(arr, snake, ladder, players, playerPositions);
				break;
			}
			case 2: {
				int square = arr.length * arr[0].length;
				System.out.println(square);
				int minMoves = findMinMoves(arr, snake, ladder, 100);

				System.out.println("The least number of moves required to reach square " + square + " is: " + minMoves);
				System.out.println(path);
				break;
			}
			case 3: {
				loop = false;
				System.exit(0);
				break;
			}
			default: {
				System.out.println("invalid choice:");
				break;
			}
			}
		}

	}

	private int findMinMoves(int[][] arr, int[][] snake, int[][] ladder, int destinationSquare) {
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[destinationSquare + 1];

		queue.add(1);
		visited[1] = true;

		int moves = 0;

		while (!queue.isEmpty()) {
			int size = queue.size();

			for (int i = 0; i < size; i++) {
				int currentVal = queue.poll();
				if (currentVal == destinationSquare) {
					return moves;
				}

				for (int j = 1; j <= 6; j++) {
					int nextSquare = currentVal + j;

					for (int k = 0; k < snake.length; k++) {
						if (snake[k][0] == nextSquare) {
							nextSquare = snake[k][1];
							break;
						}
					}
					for (int k = 0; k < ladder.length; k++) {
						if (ladder[k][0] == nextSquare) {
							nextSquare = ladder[k][1];
							break;
						}
					}

					if (nextSquare <= destinationSquare && !visited[nextSquare]) {
						queue.add(nextSquare);
						visited[nextSquare] = true;
					}
				}
			}
			moves++;
			path.add(queue.element());
		}
		System.out.println(path);
		return -1;
	}

	private void playGame(int[][] arr, int[][] snake, int[][] ladder, String[] players, int[] playerPositions) {
		while (true) {
			for (int i = 0; i < players.length; i++) {
				int consecutiveSixes = 0;
				int totalMoves = 0;

				while (true) {
					int rotate = (int) ((Math.random() * 6) + 1);

					if (rotate == 6) {
						consecutiveSixes++;

						if (consecutiveSixes == 3) {
							System.out.println("Three consecutive 6s! " + players[i] + " loses their turn.");
							break;
						}
					} else {
						consecutiveSixes = 0;
					}

					System.out.println("Player " + players[i] + " rolled: " + rotate);

					playerPositions[i] += rotate;

					if (playerPositions[i] >= arr.length * arr[0].length) {
						System.out.println(players[i] + " won the game!");
						return;
					}

					int newPos = checkSnakeLaddor(snake, ladder, playerPositions[i]);
					if (playerPositions[i] == newPos) {
						System.out.println(players[i] + " moved from " + (playerPositions[i] - rotate) + " to "
								+ playerPositions[i]);
					} else {
						playerPositions[i] = newPos;
					}

					totalMoves++;

					if (rotate != 6 || consecutiveSixes == 3) {
						break;
					}
				}
				System.out.println("Total moves for " + players[i] + ": " + totalMoves);
				System.out.println();
			}
		}
	}

	private int checkSnakeLaddor(int[][] snake, int[][] ladder, int position) {
		for (int i = 0; i < snake.length; i++) {
			int snakeHead = snake[i][0];
			int snakeTail = snake[i][1];

			if (snakeHead == position) {
				System.out.println("Snake is attack " + position + " to " + snakeTail);
				return snakeTail;
			}
		}
		for (int j = 0; j < ladder.length; j++) {
			int ladderBottom = ladder[j][0];
			int ladderTop = ladder[j][1];

			if (ladderBottom == position) {
				System.out.println("Ladder is used " + position + " to " + ladderTop);
				return ladderTop;
			}
		}
		return position;
	}

	private void displayBoard(int[][] arr) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (i % 2 == arr.length % 2) {
				for (int j = 0; j < arr[i].length; j++) {
					System.out.print(arr[i][j] + "\t");
				}
			} else {
				for (int j = arr[i].length - 1; j >= 0; j--) {
					System.out.print(arr[i][j] + "\t");
				}
			}
			System.out.println();
		}
	}

	private void fillNumber(int[][] arr) {
		int count = 1;

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = count++;
			}
		}
	}
}
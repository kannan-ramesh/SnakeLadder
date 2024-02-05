package com.kannanrameskrk;

import java.util.Scanner;

public class SnakeLadder {
	Scanner input = new Scanner(System.in);

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

		playGame(arr, snake, ladder, players, playerPositions);
		displayBoard(snake);
		System.out.println();
		displayBoard(ladder);
	}

	private void playGame(int[][] arr, int[][] snake, int[][] ladder, String[] players, int[] playerPositions) {
		while (true) {

			for (int i = 0; i < players.length; i++) {
				int rotate = (int) ((Math.random() * 6) + 1);
				System.out.println("players " + players[i] + " Rotate role:");
				playerPositions[i] += rotate;

				if (playerPositions[i] >= arr.length * arr[0].length) {
					System.out.println(players[i] + "  Won the game...");
					return;
				}

				int newPos = checkSnakeLaddor(snake, ladder, playerPositions[i]);
				if(playerPositions[i]==newPos) {
					System.out.println(
							players[i] + " moved from " + (playerPositions[i] - rotate) + " to " + playerPositions[i]);
					System.out.println();
				}else {
					playerPositions[i] = newPos;
					System.out.println(
							players[i] + " moved from " + newPos + " to " + playerPositions[i]);
					System.out.println();
				}
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
package com.vicoub.triovisiongame;

public class Player {
    int number;
    int points;
    Inventory inventory;

    public Player(int number, Inventory inventory) {
        this.number = number;
        this.points = 0;
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }
}

import java.util.Random;

import creatures.*;
import items.*;
import weight.*;

public class MainProgram {

	public static void main(String[] args) throws Exception {
		System.out.println("MAIN PROGRAM 1.");
		System.out.println("--------------------");
		Weapons Weapon1 = new Weapons (50, new Weight(WeightUnit.GRAM, 1));
		Weapons Weapon2 = new Weapons (50, new Weight(WeightUnit.GRAM, 1));
		Backpacks Backpack1 = new Backpacks(50, new Weight(WeightUnit.GRAM, 1), new Weight(WeightUnit.GRAM, 10000));
		Backpacks Backpack2 = new Backpacks(50, new Weight(WeightUnit.GRAM, 1), new Weight(WeightUnit.GRAM, 10000));
		Monsters Player1 = new Monsters("Ghoul", 100, 2, Weapon1, Backpack1);
		Monsters Player2 = new Monsters ("Goblin", 100, 2, Weapon2, Backpack2);
		try {
			Player1.displayProperties();
		} catch (Exception e){
			System.out.println(e);	
		}
		System.out.println("--------------------");
		try {
			Player2.displayProperties();
		} catch (Exception e){
			System.out.println(e);	
		}

		System.out.println("MAIN PROGRAM 2.");
		System.out.println("--------------------");
		Backpacks Backpack3 = new Backpacks(50, new Weight(WeightUnit.GRAM, 1), new Weight(WeightUnit.GRAM, 10000));
		Weapons Weapon3 = new Weapons (50, new Weight(WeightUnit.GRAM, 1));
		Backpack3.addContent(Weapon3);

		System.out.println("MAIN PROGRAM 3.");
		System.out.println("--------------------");
		Purses Purse1 = new Purses(50, new Weight(WeightUnit.GRAM, 20), 1);
		Purses Purse2 = new Purses(50, new Weight(WeightUnit.GRAM, 20), 1);
		Random rand1 = new Random();
		Purse1.changeDukats(rand1.nextInt(Purse1.getMaxCapacity()));
		Random rand2 = new Random();
		Purse2.changeDukats(rand2.nextInt(Purse2.getMaxCapacity()));
		Backpack3.addContent(Purse1, Purse2);
		
		System.out.println("MAIN PROGRAM 4.");
		System.out.println("--------------------");
		Player1.changeAnchors(2, Backpack3);

		System.out.println("MAIN PROGRAM 5. + 6. + 7.");
		System.out.println("--------------------");
		Random rand3 = new Random();
		int Starter = rand3.nextInt(2);
		while(Player1.getHitpoints() > 0 && Player2.getHitpoints() > 0) {
			if (Starter == 0) {
				try {
					Player1.Hitting(Player2);
				} catch (Exception e) {
					System.out.println(e);
				}
				try {
					Player2.Hitting(Player1);
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				try {
					Player2.Hitting(Player2);
				} catch (Exception e) {
					System.out.println(e);
				}
				try {
					Player1.Hitting(Player1);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		System.out.println("--------------------");
		if (Player1.getHitpoints() > 0) {
			System.out.println("The winner is " + Player1.getName() + "!");
			((Backpacks) Player1.getAnchor(2)).addContent(Player2.getAnchors());
			Player1.displayProperties();
		} else {
			System.out.println("The winner is " + Player2.getName() + "!");
			((Backpacks) Player2.getAnchor(2)).addContent(Player1.getAnchors());
			Player2.displayProperties();
		}
	}	

}

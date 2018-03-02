package items;

import creatures.*;
import weight.*;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class that defines purses for a Role Playing Game.
 * 
 * @Invar	At stable times a purse will always have an amount of dukats that is within its
 * 				carrying capacity if it is not thorn apart.
 * 			|if this.getIsFunctional() == true
 * 			|	then 0 <= this.getDukatAmount <= this.getCapacity()
 * 
 * @author Murat Cem Kose & Nuno Goncalo Pires Chicoria
 *
 */
public class Purses extends Items {

	private static ArrayList<Long>FibonacciArray = new ArrayList<Long>();
	private int dukatAmount;
	private int totalValue;
	private int MAX_CAPACITY;

	/**
	 * A constructor to create a purse that can later be in the possession of monsters or backpacks.
	 * 
	 * @param	value
	 * 			The inherent value of the purse.
	 * @param	itemWeight
	 * 			The inherent weight of the purse.
	 * @param 	capacity
	 * 			The amount of dukats the purse can hold at the same time.
	 * 
	 * @effect	The purse is initiated as a new item with a given value, weight and PurseID
	 * 			|super(value, itemWeight, Purses.generateID())
	 * 
	 * @post		Sets the maximum capacity of a purse.
	 * 			| setMaxCapacity(capacity)
	 * @post		Sets the totalValue.
	 * 			| new.totalValue = getPurseValue() + getDukatAmount()
	 * 
	 */
	public Purses(int value, Weight itemWeight, int capacity) throws Exception {
		super(value, itemWeight, Purses.generateID());
		this.setMaxCapacity(capacity);
		this.totalValue = this.getValue() + getDukatAmount();
	}


	/**
	 * Generates the next Fibonacci number taking into account the numbers that are already in the FibonacciArray.
	 * 
	 * @post		Each number that is generated if a Fibonacci number.
	 * 			|newFib == FibonacciArray.get(FibonacciArray.size() - 1) + FibonacciArray.get(FibonacciArray.size() - 2)
	 * 
	 * @returns	Returns the next Fibonacci number.
	 * 			|return == newFib
	 * 
	 */
	public static long generateID() {
		if (FibonacciArray.isEmpty()) {
			FibonacciArray.add(Long.valueOf("1"));
			FibonacciArray.add(Long.valueOf("1"));
			return Long.valueOf("1");
		}
		else {
			long newFib = FibonacciArray.get(FibonacciArray.size() - 1) + FibonacciArray.get(FibonacciArray.size() - 2);
			FibonacciArray.add(newFib);
			return newFib;
		}
	}

	/**
	 * Returns the amount of dukats the purse currently stores.
	 * 
	 */
	@Basic
	public int getDukatAmount() {
		return dukatAmount;
	}

	/**
	 * Sets the amount of dukats the purse currently holds.
	 * 
	 * @param 	dukatAmount
	 * 			Number that refers to the total amount of dukats the purse will carry.
	 * 
	 * @Pre		If the purse has a holder it must be possible to set this number of dukats taking into account that this will change the
	 * 				purse's weight and the holder may not be able to carry it.
	 * 			|if this.getHolder() != null
	 * 			| then this.checkWeightChange(dukatAmount)
	 * 
	 * @post		If we try to add more dukats than we can the purse will be thorn apart.
	 * 			|if dukatAmount > this.getMaxCapacity()
	 * 			|	then new.getDukatAmount() == 0
	 * 			|	then new.getValue() == 0
	 * 			|	then new.getIsFunctional() == false
	 * @post		Sets the number of dukats inside the purse to the given dukatAmount
	 * 			|new.getDukatAmount() == dukatAmount

	 * @throws 	Exception
	 * 			Throws exception if the amount of dukats is below zero.
	 * 			|dukatAmount < 0
	 * @throws	Exception
	 * 			Throws exception if the amount of dukats is bigger than the purse capacity.
	 * 			|dukatAmount > this.getMaxCapacity()
	 * @throws	Exception
	 * 			Throws an exception if the purse is not functional.
	 * 			|this.getIsFunctional() == false
	 * 
	 */
	private void setDukatAmount(int dukatAmount) throws Exception {
		if(this.getIsFunctional() == false)
			throw new Exception ("This purse is broken and cannot be used.");
		if (dukatAmount < 0)
			throw new Exception("Please stop trying to incur into debt.");
		if (this.getHolder() != null)
			this.checkWeightChange(dukatAmount);
		if (dukatAmount > this.getMaxCapacity()) {
			this.setDukatAmount(0);
			this.setValue(0);
			this.destroyItem();
			throw new Exception("You tried to add more dukats than you could and now the purse has teared.");
		}
		else {
			this.dukatAmount = dukatAmount;
		}
	}

	/**
	 * Changes the amount of dukats inside a purse.
	 * 
	 * @param 	change
	 * 			The amount of dukats we will add/subtract from the purse.
	 * 
	 * @post		The change will be added to the purse dukat amount.
	 * 			|new.getDukatAmount() == this.getDukatAmount() + change
	 * 
	 * @throws 	The purse is not functional and cannot be used.
	 * 			|this.getIsFunctional() == false
	 * 
	 */
	public void changeDukats(int change) throws Exception{
		if(this.getIsFunctional() == false)
			throw new Exception ("This purse is broken and cannot be used.");
		this.setDukatAmount(this.getDukatAmount() + change);
	}

	/**
	 * Transfers dukats from one purse to another.
	 * 
	 * @param 	otherPurse
	 * 			The purse that will receive the dukats.
	 * @param 	amount
	 * 			The amount of dukats that will be transfered to the otherPurse.
	 * 
	 * @post		Removes the dukats from the purse and adds that amount to the given purse.
	 * 			| this.setDukatAmount(this.getDukatAmount() - transferAmount)
				| otherPurse.setDukatAmount(otherPurse.getDukatAmount() + transferAmount)
	 * 
	 * @effect 	setDukatAmount()
	 * 
	 * @throws	Exception
	 * 			Both purses need to be functional for the transfer to take place.
	 * 			|this.getIsFunctional() == false
	 * 			|otherPurse.getIsFunctional == false
	 * 
	 */
	public void transferDukats(Purses otherPurse, int transferAmount) throws Exception {
		if(this.getIsFunctional() == false)
			throw new Exception ("This purse is broken and cannot be used.");
		if(otherPurse.getIsFunctional() == false)
			throw new Exception ("The other purse is broken and cannot be used.");
		this.setDukatAmount(this.getDukatAmount() - transferAmount);
		otherPurse.setDukatAmount(otherPurse.getDukatAmount() + transferAmount);
	}

	/**
	 * Returns the total value of the purse.
	 * 
	 */
	@Basic
	public int getTotalValue() {
		return totalValue;
	}

	/**
	 * Returns the maximum number of dukats a purse can carry.
	 * 
	 */
	@Basic @Immutable
	public int getMaxCapacity() {
		return MAX_CAPACITY;
	}

	/**
	 * Sets the maximum number of dukats a purse can carry.
	 * 
	 * @param 	maxCapacity
	 * 			Maximum number of dukats.
	 * 
	 * @post		Sets the new maximum capacity.
	 * 			|if maxCapacity > 0
	 * 			|	then new.getMaxCapacity() == maxCapacity
	 * 			|else new.getMaxCapacity() == 1
	 * 
	 * @throws	Exception
	 * 			The purse is not functional.
	 * 			|this.getIsFunctional() == false
	 * 
	 */
	public void setMaxCapacity(int maxCapacity) throws Exception {
		if(this.getIsFunctional() == false)
			throw new Exception ("This purse is broken and cannot be used.");
		if (maxCapacity < 1)
			this.MAX_CAPACITY = 1;
		else
			this.MAX_CAPACITY = maxCapacity;
	}

	/**
	 * Checks if the change in weight we are doing is valid for the totality of all holders and indirect holders of the purse.
	 * 
	 * @param 	dukatAmount
	 * 			The value of dukats the purse will carry after the change.
	 * 			
	 * @throws 	Gives exception if the monster or backpack cannot carry the purse with that weight.
	 * 			| ((Monsters) this.getHolder()).getCurrentWeight().getWeightAmount() + dukatAmount * 50 > ((Monsters)this.getHolder()).getMaxWeight().getWeightAmount() ||
	 * 			| 	((Backpacks) this.getHolder()).getTotalWeight().getWeightAmount() + dukatAmount * 50 > ((Backpacks)this.getHolder()).getMaxWeight().getWeightAmount()
	 * 
	 */
	public void checkWeightChange(int dukatAmount)throws Exception {
		if (this.getHolder() instanceof Monsters) {
			if (((Monsters) this.getHolder()).getCurrentWeight().getWeightAmount() + dukatAmount * 50 > ((Monsters)this.getHolder()).getMaxWeight().getWeightAmount())
				throw new Exception("The monster cannot hold that many dukats.");
		} else { 
			while(this.getHolder() instanceof Backpacks) {
				if (((Backpacks) this.getHolder()).getTotalWeight().getWeightAmount() + dukatAmount * 50 > ((Backpacks)this.getHolder()).getMaxWeight().getWeightAmount())
					throw new Exception("The backpack cannot hold that many dukats.");
				else this.checkWeightChange(dukatAmount);
			}
		}
	}

	/**
	 * Returns the total weight of a purse.
	 * 
	 * @Throws	The purse must be functional.
	 * 			|this.getIsFunctional() == false
	 * 
	 */
	@Override
	public Weight getTotalWeight() throws Exception {
		if(this.getIsFunctional() == false)
			throw new Exception ("This purse is broken and cannot be used.");
		Weight totalWeight = new Weight(this.getWeight().getWeightType(), (this.getDukatAmount() * 50) + this.getWeight().getWeightAmount());
		return totalWeight;
	}

	/**
	 * Displays the properties of the purse. 
	 * 
	 */
	public void displayProperties() {
		if(this.getIsFunctional() == false)
			System.out.println("This purse is torn appart.");
		else {
			System.out.println("Purse " + this.getItemID() + " properties.");
			System.out.println("Dukat amount: " + this.getDukatAmount());
			System.out.println("Value: " + this.getValue());
			if (this.getHolder() == null)
				System.out.println("Holder: null");
			else
				if (this.getHolder() instanceof Monsters)
					System.out.println("Holder: " + ((Monsters)this.getHolder()).getName());
				else
					System.out.println("Holder: " + ((Backpacks)this.getHolder()).getItemID());
		}
	}

}

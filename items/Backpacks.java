package items;

import creatures.Monsters;
import weight.*;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class that defines backpacks for a Role Playing Game.
 * 
 * @Invar	Each name needs to have at least two characters and should only include letters, digits, apostrophes and spaces.
 * 			| value > 0
 * 
 * @author Murat Cem Kose & Nuno Goncalo Pires Chicoria
 * 
 */
public class Backpacks extends Items{

	private static ArrayList<Long>backpackIDs = new ArrayList<Long>();
	private ArrayList<Items> contents = new ArrayList<Items>();
	private final Weight MAX_WEIGHT;

	/**
	 * A constructor to initialize backpacks.
	 * 
	 * 	@param 	value
	 * 			The inherent value of the backpack.
	 *	@param 	itemWeight
	 *			The weight of the backpack.
	 * 	@param 	MAX_WEIGHT
	 * 			The maximum carrying weight of the backpack.
	 * 
	 * 	@Post 	The maximum weight value can only have a positive value. 
	 * 			| if MAX_WEIGHT.getWeightAmount() < 0
	 * 			| 	new.getMaxWeight.getWeightAmount() == 1
	 * 	@post 	New maximum weight capacity is equal to given MAX_WEIGHT
	 * 			| new.getMaxWeight() == MAX_WEIGHT
	 * 
	 * 	@effect 	Gets the weight amount by using getWeightAmount method from Weight class.
	 * 			| Weight.getWeightAmount()
	 * 	@effect	The new backpack is initialized as a new item with given value, weight and the ID generated by generateID()
	 *      		| super(value, itemWeight , Backpacks.generateID())
	 * 
	 */
	public Backpacks(int value, Weight itemWeight, Weight MAX_WEIGHT) throws Exception {
		super(value, itemWeight , Backpacks.generateID());
		if (MAX_WEIGHT.getWeightAmount() < 1)
			this.MAX_WEIGHT= new Weight(WeightUnit.GRAM, 1);
		else
			this.MAX_WEIGHT = MAX_WEIGHT;
	}

	/**
	 * Returns the value of the item. 
	 * 
	 */
	@Basic @Immutable
	public Weight getMaxWeight() {
		return this.MAX_WEIGHT;
	}

	/**
	 * The method that generates item id for backpacks.
	 * 
	 * 	@Pre		If there are no backpacks created before, first backpack starts with id 1.
	 * 			| if (backpackIDs.isEmpty())
	 * 			| 	return 1.
	 * 
	 * 	@post	The id that is generated must be an odd number. 
	 * 			| new.getItemID() % 2 == 1
	 * 
	 */
	public static long generateID() {
		if (backpackIDs.isEmpty()) {
			backpackIDs.add(Long.valueOf("1"));
			return Long.valueOf("1");
		}
		else {
			backpackIDs.add(backpackIDs.get(backpackIDs.size() - 1) + 2);
			return backpackIDs.get(backpackIDs.size()-1);
		}
	}

	/**
	 * The method that adds content to the backpack.
	 * 
	 * @param	args
	 * 			The items to be added.
	 * 
	 * @post 	Each given item is added to the backpack.
	 * 			| for(Items item : args)
	 * 			| 	  this.contents.contains(temp) == true
	 * 
	 * @throws 	Exception
	 * 		 	Throws an exception if the addition of the item results with exceeding the weight capacity of the backpack.
	 * 			| this.getMaxWeight().getWeightAmount() < new.getTotalWeight().getWeightAmount()
	 * 
	 */
	public void addContent(Items... args) throws Exception {
		for(Items item : args) {
			if (item == null)
				continue;
			if (this.getMaxWeight().getWeightAmount() < this.getTotalWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount())
				throw new Exception("The backpack cannot hold that item due to weight constrictions.");
			checkWeightChange(item);
			this.contents.add(item);
			item.setHolder(this);
		}
	}

	/**
	 * Checks if the change in weight we are doing is valid for the totality of all holders and indirect holders of the backpack.
	 * 
	 * @param 	item
	 * 			The item that is going to be added to the backpack and makes the weight check necessary.
	 * 			
	 * @throws 	Gives exception if the monster or backpack cannot carry the backpack with that weight.
	 * 			| ((Monsters) this.getHolder()).getCurrentWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount() > ((Monsters)this.getHolder()).getMaxWeight().getWeightAmount() ||
	 * 			| 	((Backpacks) this.getHolder()).getTotalWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount() > ((Backpacks)this.getHolder()).getMaxWeight().getWeightAmount()
	 * 
	 */
	public void checkWeightChange(Items item)throws Exception {
		if (this.getHolder() instanceof Monsters) {
			if (((Monsters) this.getHolder()).getCurrentWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount() > ((Monsters)this.getHolder()).getMaxWeight().getWeightAmount())
				throw new Exception("The monster cannot carry that item.");
		} else { 
			while(this.getHolder() instanceof Backpacks) {
				if (((Backpacks) this.getHolder()).getTotalWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount() > ((Backpacks)this.getHolder()).getMaxWeight().getWeightAmount())
					throw new Exception("The backpack cannot hold that item.");
				else this.checkWeightChange(item);
			}
		}
	}

	/**
	 * The method to drop an item from the backpack.
	 * 
	 * @param	item
	 * 		 	The item to be dropped.
	 * 
	 * @post 	Removes the item from the backpack.
	 * 			| this.contents.contains(item) == false
	 * 
	 * @effect 	Sets the holder of the dropped item to null. 
	 * 			setHolder()
	 * 
	 */
	public void dropItem(Items... args) throws Exception {
		for(Items item : args) {
			if (item.getHolder() == null)
				this.contents.remove(this.contents.indexOf(item));
			else
				item.setHolder(null);
		}
	}
	
	/**
	 * Returns the contents of the backpack. 
	 * 
	 */
	@Basic
	public ArrayList<Items> getContents() {
		return contents;
	}

	/** 
	 * The method to transfer an item from the backpack to a new receiver.
	 * 
	 * @param 	item
	 * 		 	The item to be transfered.
	 * @param 	newHolder
	 * 		 	The new holder that will receive the item. 
	 * 
	 * @post 	The given item is transfered to the new receiver.
	 * 			| newHolder.getContents().contains(item) != true
	 * @post 	The given item is excluded from the backpack.
	 * 			| this.contents.contains(item) != true
	 * @post 	The holder of the item is changed to new holder.
	 * 			| item.getHolder() == newHolder
	 * 
	 * @effect 	If the new holder is a monster, transferItem method is used.
	 * 			| Monsters.transferItem(item)
	 * @effect 	If the new holder is a backpack, addContent method is used.
	 * 			| Backpacks.addContent(item)
	 * 
	 * @throws 	Exception
	 * 		 	Throws an exception if the backpack does not contain the given item.
	 * 			| this.contents.contains(item) !=true
	 * 
	 */
	public void transferContent(Items item, Object newHolder) throws Exception {
		if (this.contents.contains(item)) {
			if (Monsters.class.isInstance(newHolder)) 
				((Monsters)newHolder).transferItem(item);
			else
				((Backpacks)newHolder).addContent(item);
		}
		else
			throw new Exception("This item is not present.");
	}

	/**
	 * Returns the total value of the backpack. 
	 * 
	 */
	@Basic
	public int getTotalValue() {
		int totalValue = 0;
		for(Items temp : this.contents) {
			totalValue = totalValue + temp.getTotalValue();
		}
		return totalValue;
	}

	/**
	 * Returns the total weight of the backpack. 
	 * 
	 */
	@Basic
	public Weight getTotalWeight() throws Exception {
		double totalAmount = 0;
		for(Items temp : this.contents) {
			totalAmount = totalAmount + temp.getTotalWeight().getWeightAmount();}
		return new Weight(WeightUnit.GRAM, totalAmount);

	}

	/**
	 * Returns the heaviest item in the backpack.
	 * 
	 * @post 	Finds the lightest item.
	 * 			| for(Items temp : this.contents)
	 * 			| 		temp.getWeight().getWeightAmount >= heaviestItem.getWeight().getWeightAmount
	 * 
	 * @return 	returns the lightest item in the backpack.
	 * 			| return == heaviestItem
	 * 
	 * @throws 	Exception
	 * 			| Throws exception if the backpack is empty.
	 * 			| this.contents.isEmpty()
	 */
	public Items heaviestItem() throws Exception {
		if (this.contents.isEmpty())
			throw new Exception("The backpack is empty.");
		Items heaviestItem = this.contents.get(0);
		for(Items temp : this.contents) {
			if (temp.getTotalWeight().getWeightAmount() > heaviestItem.getTotalWeight().getWeightAmount())
				heaviestItem = temp;
		}
		return heaviestItem;
	}

	/**
	 * Returns the lightest item in the backpack.
	 * 
	 * 	@post 	Finds the lightest item.
	 * 			| for(Items temp : this.contents)
	 * 			| 	temp.getWeight().getWeightAmount<=lightestItem.getWeight().getWeightAmount
	 * 
	 * 	@return 	Returns the lightest item in the backpack.
	 * 			| return == lightestItem
	 * 
	 * 	@throws 	Exception
	 * 			Throws exception if the backpack is empty.
	 * 			| this.contents.isEmpty()
	 */
	public Items lightestItem() throws Exception {
		if (this.contents.isEmpty())
			throw new Exception("The backpack is empty.");
		Items lightestItem = this.contents.get(0);
		for(Items temp : this.contents) {
			if (temp.getTotalWeight().getWeightAmount() < lightestItem.getTotalWeight().getWeightAmount())
				lightestItem = temp;
		}
		return lightestItem;
	}

	/**
	 * The function that displays properties of the backpack.
	 * 
	 * 
	 */
	public void displayProperties() throws Exception  {
		System.out.println("Backpack " + this.getItemID() + " contents and properties.");
		System.out.println("Full list of contents:");
		for(Items item : this.contents) {
			System.out.println(String.valueOf(item.getClass()) + " " + String.valueOf(item.getItemID()));
		}
		System.out.println("Heaviest item: " + this.heaviestItem().getClass() + " " + this.heaviestItem().getItemID());
		System.out.println("Lightest item: " + this.lightestItem().getClass() + " " + this.lightestItem().getItemID());
		System.out.println("Current total weight: " + this.getTotalWeight().getWeightAmount());
		System.out.println("Maximum weight: " + this.getMaxWeight().getWeightAmount());
	}

}

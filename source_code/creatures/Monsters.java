package creatures;

import items.*;
import weight.*;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;


/**
 * A class that defines monsters for a Role Playing Game.
 * 
 * @Invar	Each name needs to have at least two characters and should only include letters, digits, apostrophes and spaces.
 * 			|isValidName(getName())
 * @Invar	Damage is always a value between 1 and MAX_DAMAGE
 * 			|0 < this.getDamage() < getMaxDamage()
 * @Invar	Strength is always a value between -10 and 30.
 * 			|-11 < this.getStrength() < 31
 * @Invar	Protection is always a value between 1 and MAX_PROT.
 * 			|0 < this.getHitpoins() <= getMaxProtection()
 * @Invar	Hitpoints is always a value between the hitpoints the monster is created with and zero
 * 			| 0 <= hitpoints <= this.getMaxHitpoints
 * @Invar	The current weight of a monster is always between zero and the maximum carrying weight.
 * 			| Weight(WeightUnit.KG, 0) <= this.getCurrentWeight < Weight(WeightUnit.KG, 12.00 * Math.abs(this.getStrength))
 * 
 * @author	Murat Cem Kose & Nuno Goncalo Pires Chicoria
 *
 */
public class Monsters{

	private final String name;
	private int damage;
	private static int MAX_DAMAGE = 20;
	private final int protection;
	private static int MAX_PROTECTION = 40;
	private int hitpoints;
	private final int MAX_HITPOINTS;
	private final int strength;
	private Items[] anchors;
	private Weight MAX_WEIGHT;
	private Weight currentWeight = new Weight(WeightUnit.GRAM,0.00);

	/**
	 * Constructor for the initiation of a Monster.
	 * 
	 *	@param	name
	 * 			The name of the monster.
	 *	@param	hitpoints
	 * 			The hitpoints of the monster.
	 * 	@param	nbOfAnchors
	 * 			The number of anchors the monster has.
	 * 	@param	weapon
	 * 			The weapon that the monster has when it borns. This weapon is assigned to his left hand.
	 * 	@param	backpack	
	 * 			The backpack that the monster has when it borns. This backpack is assigned to his back.
	 * 
	 *	@post	Sets the name of the monster.
	 * 			| new.getName() == name
	 *	@post	Sets the maximum hitpoints of the monster.
	 * 			| new.getMaxHitpoints() == hitpoints
	 *	@post	Sets the hitpoints of the monster.
	 * 			| new.getHitpoints() == hitpoints 
	 *	@post	Sets the protection of the monster to a randomly generated value.
	 * 			| new.getProtection() == generateProtection()
	 *	@post	Sets the strength of the monster to a randomly generated value.
	 * 			| new.getStrength() == generateStrength()
	 * 	@post	Sets the maximum carrying capacity of a monster.
	 * 			|new.getMaxWeight() = Weight(WeightUnit.KG, 12.00 * Math.abs(this.strength))
	 * 	@post	Sets the number of anchors the monster will have to store items.
	 * 			| new.anchors = new Items[nbOfAnchors]
	 * 	@post	Adds the weapon to the left hand.
	 * 			|new.getAnchor(0) = weapon
	 * 	@post	Adds the backpack to the back of the monster.
	 * 			|new.getAnchor(2) = backpack	
	 * 
	 *	@throws	Throws an exception if the given name is not valid for a monster.
	 * 			| ! isValidName(name)
	 * 
	 * 	@effect	setDamage(), setHitPoints(), Weight(), toNewWeightUnit(), addAnchor()
	 * 
	 */
	public Monsters(String name, int hitpoints, int nbOfAnchors, Weapons Weapon, Backpacks Backpack) throws Exception{
		if (isValidName(name))
			this.name = name;
		else
			throw new Exception("The name is not valid.");
		this.MAX_HITPOINTS = hitpoints;
		setHitpoints(hitpoints);
		this.protection = generateProtection();
		this.strength = generateStrength();
		Weight temp;
		if (this.getStrength() == 0)
			temp = new Weight(WeightUnit.KG, 12.00);
		else
			temp = new Weight(WeightUnit.KG, 12.00 * Math.abs(this.strength));
		this.MAX_WEIGHT = temp.toNewWeightUnit(WeightUnit.GRAM);
		setDamage();
		if (nbOfAnchors < 3)
			anchors = new Items[3];
		else
			anchors = new Items[nbOfAnchors];
		addAnchor(Weapon);
		addAnchor(Backpack);
	}

	/**
	 * Returns the name of the monster. 
	 * 
	 */
	@Basic @Immutable
	public String getName() {
		return name;
	}

	/**
	 * Checks if the name that will be set is valid.
	 * 
	 * 	@param  name
	 *         	The name to check.
	 * 
	 *  @return	True if and only if the name consists of at least two characters, only contains letters, digits, apostrophes and spaces
	 *  				and starts with a capital letter.
	 *  			| return == name.matches(".*[A-Z].*[a-zA-Z0-9]")
	 * 
	 */
	public boolean isValidName(String name) {
		if (name == null)
			return false;
		if (name.matches(".*[A-Z].*[a-zA-Z0-9]"))
			return true;
		else
			return false;
	}

	/**
	 * Returns the damage of the monster. 
	 * 
	 */
	@Basic @Immutable
	public int getDamage() {
		return this.damage;
	}

	/**
	 * 	Generate a random damage within 1 and MAX_DAMAGE and sets it to the monster's damage variable.
	 * 
	 *  @post	The generated value is assigned to the monster's damage variable. 
	 *  			|0 < new.getDamage() < getMaxDamage()
	 *  
	 */
	public void setDamage() {
		Random rand = new Random();
		int  damage = rand.nextInt(getMaxDamage()) + 1;
		this.damage = damage;
	}

	/**
	 * Returns the maximum value allowed when generating the damage for a monster.
	 * 
	 */
	public static int getMaxDamage() {
		return MAX_DAMAGE;
	}

	/**
	 * Returns the protection of the monster. 
	 * 
	 */
	@Basic @Immutable
	public int getProtection() {
		return protection;
	}

	/**
	 * 	Generates a random value within 1 and MAX_PROTECTION. Checks if it is a prime number. If it is, returns the value.
	 * 		Otherwise, generates a new value until we obtain a prime number and then return it.	
	 * 
	 * 	@Pre		The protection value must be a prime number.
	 * 			| isPrime(protection)
	 * 
	 *  @return	Returns a valid value that can be set to the monster's protection.
	 *  			| new.getProtection() == protection
	 *  
	 */
	public int generateProtection() {
		Random rand = new Random();
		int  protection = 4;
		while (!isPrime(protection))
			protection = rand.nextInt(MAX_PROTECTION) + 1;
		return protection;
	}

	/**
	 * Checks whether the given number is a prime or not.
	 *  
	 * 	@param  	number
	 * 		   	The number to check.
	 * 
	 * 	@return	True if and only if the given number is prime.
	 * 			| for (i = 2, i < n,  i++)
	 * 			|	n % i != 0
	 *       
	 */	
	public boolean isPrime(int n) {
		if (n < 2);
		for(int i = 2; i < n; i++) {
			if(n % i == 0)
				return false;
		}
		return true;
	}

	/**
	 * Returns the maximum value that can be set for the monster's protection. 
	 * 
	 */
	@Basic @Immutable
	public static int getMaxProtection() {
		return MAX_PROTECTION;
	}

	/**
	 * Set the maximum protection to the given maxProt.
	 * 
	 * 	@param  	maxProt
	 * 		   	The value to set for the MAX_PROTECTION variable.
	 * 
	 * 	@Pre   	The given value must be a valid value for maximum protection.
	 *       	| maxProt > 1
	 *       
	 * 	@post	The maximum value of protection for monsters is equal to the given value.
	 *       	| getMaxProtection() == maxProt
	 *       
	 */
	public static void setMaxProtection(int maxProt) {
		assert (maxProt > 1);
		MAX_PROTECTION = maxProt;
	}

	/**
	 * Returns the hitpoints of the monster. 
	 * 
	 */
	@Basic
	public int getHitpoints() {
		return hitpoints;
	}

	/**
	 * Set a new value for the monster's hitpoints.
	 * 
	 *  @param	hitpoints
	 *         	The value to set for the monster's current hitpoints.
	 * 
	 *	@post 	Sets the value to the monsters hitpoints.
	 * 			| new.getHitpoints() == hitpoints.
	 * 
	 *	@throws	Exception
	 * 			Throws exception when the value is greater than maximum hitpoints allowed for the monster. 
	 * 			| hitpoints > MAX_HITPOINTS
	 * 
	 */
	public void setHitpoints(int hitpoints) throws Exception{
		if(hitpoints > MAX_HITPOINTS)
			throw new Exception("The value for hitpoints is greater than the value for maximum hitpoints.");
		this.hitpoints = hitpoints;
	}

	/**
	 * Returns the maximum value that can be set for the monster's hitpoints. 
	 * 
	 */
	@Basic @Immutable
	public int getMaxHitpoints() {
		return MAX_HITPOINTS;
	}

	/**
	 * Returns the strength of the monster. 
	 * 
	 */
	@Basic @Immutable
	public int getStrength() {
		return this.strength;
	}

	/**
	 * 	Generates a random value for the monster's strength. 
	 * 
	 *  @return 	Returns a random value that can be set to the monster's strength.
	 *  			| new.getStrength == strength;
	 *  
	 *  @notes 	We defined the range to be between -10 and 30. However, it can be 
	 *  				changed by modifying the parameters (41 and -10) in the function for the later versions.
	 *  
	 */
	public int generateStrength() {
		Random rand = new Random();
		int  strength = rand.nextInt(41) - 10;
		return strength;
	}

	/**
	 * Returns the item attached to the anchor in the given point.
	 * 
	 * @param	point
	 * 			The number of the position we want to check.
	 * 
	 * @Throws 	Exception 
	 * 			Throws an exception if given point is out of bounds.
	 * 			|!(0 < point ||point < this.anchors.length)
	 * 
	 * @return	Returns the item in the given position.
	 * 			| return == this.anchors[point]
	 * 
	 */
	@Basic
	public Items getAnchor(int point) throws Exception {
		if (!(0 < point || point < this.anchors.length) ) {
			throw new Exception("That's not a valid point.");
		}
		return this.anchors[point];
	}

	/**
	 * Returns all the items in the monster's possession.
	 * 
	 */
	public Items[] getAnchors() {
		return this.anchors;
	}

	/**
	 * Adds the given item to the anchor list of the monster.
	 * 
	 * @param 	item
	 * 			The item to be added to the monster's anchor.
	 * 
	 * @Pre		The item cannot have a holder.
	 * 			| item.getHolder() == null
	 * 
	 *  @Post	If the item is a backpack it will be added to the anchor position 2 or greater
	 *  			| if item instanceof Backpacks
	 *  			| 	then this.anchors[i] == item for 2 < i < this.anchors.length
	 *  			| 	then item.setHolder(this)
	 *  @Post	If the item is a weapon it will be added to the anchor position 0, 1 or greater than 2.
	 *  			| if item instanceof Weapons
	 *  			| 	then this.anchors[i] == item for i = [0; this.anchors.length] - {2}
	 *  			| 	then item.setHolder(this)
	 *  @Post	If the item is a purse it will be added to the anchor position greater than 2.
	 *  			| if item instanceof Purses
	 *  			| 	then this.anchors[i] == item for i = [0; this.anchors.length] - {2}
	 *  			| 	then item.setHolder(this)
	 * 
	 * @throws 	Exception
	 * 			Throws an exception if the item is to heavy for the monster to carry.
	 * 			| this.MAX_WEIGHT.getWeightAmount() < this.getCurrentWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount()
	 * @throws	Exception
	 * 			Throws an exception if there is no place for the item we are trying to add
	 * 			| added == false
	 * @throws	Exception
	 * 			Throws an exception if the item we are trying to add already has an owner.
	 * 			| item.getHolder() != null
	 * 	
	 */
	public void addAnchor(Items item) throws Exception {
		if (item.getHolder() == null) {
			boolean added = false;
			if(Backpacks.class.isInstance(item)) {
				for (int i = 2; i < this.anchors.length; i++) {
					if (this.anchors[i] == null) {
						if(this.MAX_WEIGHT.getWeightAmount() < this.getCurrentWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount())
							throw new Exception("The backpack is too heavy for the monster to carry.");
						this.anchors[i] = item;
						added = true;
						item.setHolder(this);
						break;
					}
				}
			}
			if(Weapons.class.isInstance(item)) {
				for (int i = 0; i < this.anchors.length; i++) {
					if (i == 2)
						continue;
					else if (this.anchors[i] == null) {
						if(this.MAX_WEIGHT.getWeightAmount() < this.getCurrentWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount())
							throw new Exception("The weapon is too heavy for the monster to carry.");
						this.anchors[i] = item;
						added = true;
						item.setHolder(this);
						break;
					}
				}
			}
			if(Purses.class.isInstance(item))
				if (this.anchors.length > 3) {
					for (int i = 3; i < this.anchors.length; i++) {
						if (this.anchors[i] == null) {
							if(this.MAX_WEIGHT.getWeightAmount() < this.getCurrentWeight().getWeightAmount() + item.getTotalWeight().getWeightAmount())
								throw new Exception("The item is too heavy for the monster to carry.");
							this.anchors[i] = item;
							added = true;
							item.setHolder(this);
							break;
						}
					}
				}
			if (added == false)
				throw new Exception("There is no place for the item you're trying to add.");
		}
		else
			throw new Exception("You can only add items that don't have a holder.");
	}

	/**
	 * Removes the item in the given position of the anchors of the monster.
	 * 
	 * @param 	point
	 * 			The position of the anchor in the monster from where we want to remove the item.
	 * 
	 * @Pre		The item's holder has to be set to null before removing the item.
	 * 			|item.getHolder() == null
	 * 
	 * @post		Removes the item from the given position in the anchors.
	 * 			| this.anchors[point] == null
	 * 
	 * @effect 	getAnchor(), setHolder()
	 * 			
	 */
	public void removeAnchor(int point) throws Exception {
		if (this.getAnchor(point).getHolder() == null) {
			this.anchors[point] = null;
		}
		else
			this.getAnchor(point).setHolder(null);
	}

	/**
	 * Changes the given item from one anchor position to the given new one.
	 * 
	 * @param 	point
	 * 			The new position in the anchors for the item.
	 * @param 	item
	 * 			The item that will change positions.
	 * 
	 * 	@Pre		The given point needs to exist in the monster anchors.
	 * 			|0 <= point < this.getAnchors().length
	 * 	@Pre		The given point needs to be empty.
	 * 			|this.getAnchor(point) == null
	 * 	@Pre		If the point is smaller than 2 the item needs to be a weapon.
	 * 			| item instanceof Weapons == true
	 * 	@Pre		If the point is equal to 2 the item needs to be a backpack.
	 * 			| item instaceof Backpacks == true
	 * 
	 * 	@post	The item will be added to the given position.
	 * 			| this.anchors[point] == item	
	 * 
	 * 	@throws Exception
	 * 			If the item we try to add cannot go to that position and exception will be thrown.
	 * 			| (if point < 2 && item instanceof Weapons == false) || (if point = 2 && item instanceof Backpacks)
	 * 
	 */
	public void changeAnchors(int point, Items item) throws Exception {
		if (point < 0 || point > this.getAnchors().length)
			throw new Exception("That's not a valid point.");
		if (point < 2)
			if (item instanceof Weapons || item == null) {
				this.anchors[point] = item;
				if (item != null)
					item.setHolder(this);
			}
			else
				throw new Exception ("That item cannot go into that position.");
		else if (point == 2)
			if (item instanceof Backpacks || item == null) {
				this.anchors[point] = item;
				if (item != null)
					item.setHolder(this);
			}
			else
				throw new Exception("That item cannot go into that position.");
		else {
			this.anchors[point] = item;
			if (item != null)
				item.setHolder(this);
		}
	}

	/**
	 * Returns the position of a item in the monster anchors. If the item is not in the
	 * 			anchors it returns -1.
	 * 
	 * @param 	item
	 * 			The item that we want to look for the position.
	 * 
	 * @return	position
	 * 			|this.anchors[point] == item
	 * 
	 */
	public int getAnchorPosition(Items item) {
		int count = -1;
		int position = -1;
		for(Items args: this.getAnchors()) {
			count = count + 1;
			if (args == item)
				position = count;
		}
		return position;
	}

	/**
	 * A method to transfer an item from one monster to another.
	 * 
	 * @param 	item
	 * 			The item that will be transfered for the other monster.
	 * 
	 * @Post		The given monster will be the holder of the item.
	 * 			|item.getHolder == this
	 * 
	 * @throws 	Exception
	 * 			This transaction can only occur between monsters/backpacks.
	 * 			|(item.getHolder() instanceof Monsters) || (item.getHolder() instanceof Backpacks)
	 * 
	 */	
	public void transferItem(Items item) throws Exception {
		if (item.getHolder() instanceof Monsters) {
			item.setHolder(null);
			this.addAnchor(item);
		}
		else if (item.getHolder() instanceof Backpacks) {
			item.setHolder(null);
			this.addAnchor(item);
		}
		else
			throw new Exception("The transaction is only possible between monsters and/or backpacks.");
	}

	/**
	 * Returns the current weight of the monster.
	 * 
	 */
	@Basic
	public Weight getCurrentWeight() {
		return this.currentWeight;
	}

	/**
	 * Sets the current weight of a monster.
	 * 
	 * @param 	newWeight
	 * 			The weight value that will be set for the monster.
	 * 
	 * @post 	Sets a new weight to monster.
	 * 			| new.getCurrentWeight = new Weight(currentWeight.getWeightType(), newWeight)
	 * 
	 * @effect 	Weight()
	 * 
	 */
	public void setCurrentWeight(double newWeight) throws Exception {
		this.currentWeight = new Weight(currentWeight.getWeightType(), newWeight);
	}

	/**
	 * Returns the maximum weight a monster can carry.
	 * 
	 */
	@Basic @Immutable
	public Weight getMaxWeight() {
		return this.MAX_WEIGHT;
	}

	/**
	 * Returns the total value of all the items a monster is carrying.
	 * 
	 */
	public int getTotalValue() {
		int totalValue = 0;
		for(Items temp : this.getAnchors()) {
			if (temp == null)
				continue;
			else
				totalValue = totalValue + temp.getTotalValue();
		}
		return totalValue;
	}

	/**
	 * Simulates an attack of a monster to another.
	 * 
	 *  @param 	opponent
	 *  			The monsters that is hit. 
	 * 
	 * 	@Pre 	The monster can not fight itself.
	 * 			|  this != opponent
	 * 
	 * 	@post  	If and only if the random generated hit value is greater than attacking monster's 
	 * 			hitpoints set it to the attacking monster's hitpoints 
	 * 			| if hit >= this.getHitpoints()
	 * 			| 	then hit = this.getHitpoints()
	 * 
	 * 	@post 	If and only if the random generated hit value is greater than the protection 
	 * 				of the opponent, attack takes place. Otherwise, no damage can be dealt to the opponent.
	 * 			| if hit >= opponent.getProtection()
	 * 			| 	then new.opponent.getHitpoints()  == opponent.getHitpoints() - power
	 * 
	 * @effect	setHitpoints()
	 * 
	 */
	public void Hitting(Monsters opponent) throws Exception {
		assert (this != opponent);
		Random rand = new Random();
		int  hit = rand.nextInt(30);
		if(hit >= this.getHitpoints()) 
			hit = this.getHitpoints();
		if (hit >= opponent.getProtection()) {
			int power;
			if (this.getAnchor(1) == null)
				power = this.getDamage() + (this.getStrength() - 5) / 3;
			else
				power = this.getDamage() + (this.getStrength() - 5) / 3 + ((Weapons)this.getAnchor(1)).getDamage();
			opponent.setHitpoints(opponent.getHitpoints() - power);
			System.out.println(this.getName() + " Deals " + power + " damage over " + opponent.getName());
		}
		else
			System.out.println(this.getName() + " attack failed!");
	}

	/**
	 * Displays the properties of the monster.
	 *  
	 * @effect	item.displayProperties()
	 * 
	 */
	public void displayProperties() throws Exception {
		System.out.println("Monster " + this.getName() + " properties:");
		System.out.println("Protection: " + this.getProtection());
		System.out.println("Remaining Hitpoints: " + this.getHitpoints());
		System.out.println("Strength: " + this.getStrength());
		System.out.println("Total value of possessions: " + this.getTotalValue() + " dukats");
		for(Items temp : this.anchors) {
			if (temp == null)
				continue;
			temp.displayProperties();
		}
	}

}

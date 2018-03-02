package items;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import creatures.Monsters;
import weight.*;

/**
 * An abstract class that represents items and its methods to a Role Playing Game.
 * 
 * @Invar	The value of the item is always positive.
 * 			| value > 0
 * 
 * @author Murat Cem Kose & Nuno Goncalo Pires Chicoria
 * 
 */
public abstract class Items implements Cloneable {
	private int value;
	private final Weight itemWeight;
	private final long itemID;
	private Object holder;
	private boolean isFunctional = true;

	/**
	 * 
	 *	@param	value
	 * 			The value of the item.
	 *	@param	itemWeight
	 * 			The weight of the item.
	 * 	@param	itemID
	 * 			The identification number of the item.
	 * 
	 *	@post 	Sets the weight of the item.
	 * 			| new.getWeight() == itemWeight
	 *	@post 	Sets the item id of the item.
	 * 			| new.getItemID() == itemID 
	 * 
	 *	@effect	Sets the value of the item.
	 * 			| setValue(value)
	 * 
	 */
	@Model
	protected Items(int value, Weight itemWeight, long itemID) throws Exception {
		setValue(value);
		this.itemWeight = itemWeight;
		this.itemID = itemID;
	}

	/**
	 * 
	 * @param 	value
	 * 			The value of the item.
	 * 
	 * @throws 	Exception
	 * 			Throws exception when the value is less than 0.
	 * 		  	| value < 0
	 * 
	 */
	public void setValue(int value)throws Exception {
		if(value < 0)
			throw new Exception();
		this.value = value;
	}

	/**
	 * Returns the value of the item. 
	 * 
	 */
	@Basic @Immutable
	public int getValue() {
		return this.value;
	}

	/**
	 * 
	 * @param 	holder
	 * 			The new holder of the item.
	 * 
	 * 	@Pre 	To set a new holder to an item, it should not have a current holder
	 * 			| this.getHolder() == null
	 * 
	 * 	@post 	The new holder of the item is set to given holder.
	 * 			| new.getHolder() == holder
	 * 	@post 	The given holder has the item.
	 * 			| if(Monsters.class.isInstance(holder))
	 * 			|	 holder.getAnchorPosition(this) > -1
	 * 			| if(Backpacks.class.isInstance(holder))
	 * 			|	 holder.getContents.contains(this) == true
	 * 	@post 	If there is a current holder that the item is set to, the item should be removed and called again.
	 * 			| new.getHolder() == null
	 * 			| new.setHolder(holder)
	 * 
	 * 	@effect 	addAnchor method is used to add the item to the monsters anchor.
	 * 			| Monsters.addAnchor(this)
	 * 	@effect 	addContent method is used to add the item to the backpack.
	 * 			| Backpacks.addContent(this)
	 * 	@effect 	removeAnchor method is used to remove the item from the monsters anchor.
	 * 			| Monsters.removeAnchor(this.getHolder().getAnchorPosition(this))
	 * 	@effect 	dropItem method is used to remove the item from the backpack.
	 * 			| Backpacks.dropItem(this)
	 * 
	 * 	@throws Exception
	 * 			Throws the exception if the given holder is not a monsters, backpack or null.
	 * 			| Monsters.class.isInstance(holder) == false  && Backpacks.class.isInstance(holder) == false && holder != null
	 * 
	 */
	public void setHolder(Object holder) throws Exception {
		if (this.getHolder() == null) {
			if (Monsters.class.isInstance(holder)) {
				boolean added=false;
				if(((Monsters)holder).getAnchorPosition(this) > -1) {
					added = true;
				}
				if (added == true)
					this.holder=holder;
				else {
					((Monsters)holder).addAnchor(this);
					//this.holder=holder;
				}
			}
			else if (Backpacks.class.isInstance(holder)) {
				boolean isAdded=((Backpacks)holder).getContents().contains(this);
				if (isAdded==true)
					this.holder=holder;
				else {
					((Backpacks)holder).addContent(this);
					//this.holder=holder;
				}
			}
			else if (holder == null) {
				if(Weapons.class.isInstance(this))
					this.destroyItem();
			}
			else
				throw new Exception("The given holder does not have the hability to hold items");
		}
		else if (this.getHolder() != null) {
			if (Monsters.class.isInstance(this.getHolder())) {
				Items tempItem = (Items) this.clone();
				this.holder = null;
				((Monsters)tempItem.getHolder()).removeAnchor(((Monsters)tempItem.getHolder()).getAnchorPosition(this));
				this.setHolder(holder);
			}
			if (Backpacks.class.isInstance(this.getHolder())) {
				Items tempItem = (Items) this.clone();
				this.holder = null;
				((Backpacks)tempItem.getHolder()).dropItem(this);
				this.setHolder(holder);
			}
		}
	}

	/**
	 * Returns the weight of the item. 
	 * 
	 */
	@Basic @Immutable
	public Weight getWeight() {
		return this.itemWeight;
	}

	/**
	 * Returns the item id. 
	 * 
	 */
	@Basic @Immutable
	public long getItemID() {
		return this.itemID;
	}

	/**
	 * Returns the holder of the item. 
	 * 
	 */
	@Basic
	public Object getHolder() {
		return this.holder;
	}

	/**
	 * Returns the indirect holder of the item. 
	 *
	 */
	@Basic
	public Object getIndirectHolder() {
		if(this.holder instanceof Monsters)
			return this.holder;
		else 
			if (((Backpacks)this.holder).getHolder() == null)
				return this.holder;
			else
				return ((Backpacks)this.holder).getIndirectHolder();
	}

	/**
	 * Changes the value of the weapon with the given amount.
	 * 
	 * 	@param	change
	 * 		  	The change that will be applied to the current value of the weapon.
	 * 
	 * 	@post  	Add the change to the weapon value.
	 * 		  	| new.getValue() == this.getValue + change
	 */
	public void changeValue(int change)throws Exception {
		setValue(change + getValue());
	}

	/**
	 * Destroys the item.
	 * 
	 * 	@post  	Makes the item non functional.
	 * 			| new.isFunctional == false
	 * 
	 */
	public void destroyItem() {
		this.setIsFunctional(false);
	}

	/**
	 * Returns the functionality of the item. 
	 * 
	 */
	@Basic
	public boolean getIsFunctional() {
		return this.isFunctional;
	}

	/**
	 * Sets the item functionality to isFunctional.
	 * 
	 * 	@post  	Sets the new functionality condition.
	 * 			| new.isFunctional == isFunctional
	 * 
	 */
	protected void setIsFunctional(boolean isFunctional) {
		this.isFunctional = isFunctional;
	}
	
	public abstract Weight getTotalWeight() throws Exception;

	public abstract int getTotalValue();

	public abstract void displayProperties() throws Exception;


}

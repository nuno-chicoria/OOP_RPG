package weight;

import java.text.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class that defines weights for items in a Role Playing Game.
 * 
 * @author Murat Cem Kose & Nuno Goncalo Pires Chicoria
 *
 */
public class Weight {
	private WeightUnit weightType;
	private double weightAmount;

	/**
	 * A constructor to create a weight that can later be assigned to items and monsters.
	 * 
	 *	@param 	wu
	 * 		  	The unit type of the weight.
	 * 	@param 	wa
	 * 		 	The amount of the weight.
	 * 
	 * 	@post 	Sets given weight unit to weight unit.
	 * 		  	| setWeightType(weightUnit)
	 * 	@post 	Sets given weight amount to weight amount.
	 * 		  	| setWeightAmount(weightAmount)
	 * 
	 * 	@throws 	Exception
	 * 			Throws exception if the given unit type is null or the given amount is negative.
	 * 		  	| weightUnit == null || weightAmount < 0.00
	 * 	
	 */
	public Weight(WeightUnit weightUnit, double weightAmount)throws Exception {
		if (weightUnit == null)
			throw new Exception("The given unit is invalid.");
		setWeightType(weightUnit);
		DecimalFormat df = new DecimalFormat("#.##");
		weightAmount = Double.valueOf(df.format(weightAmount));
		if(weightAmount < 0.00)
			throw new Exception("The amount introduced cannot be negative");
		setWeightAmount(weightAmount);	
	}

	/**
	 * Returns the unit type of the weapon. 
	 * 
	 */
	@Basic 
	public WeightUnit getWeightType() {
		return weightType;
	}

	/**
	 * Sets the weightType to the given weightType.
	 * 
	 *	@param 	weightType
	 * 		  	The weight unit type to assign.
	 * 
	 * 	@post  	Sets the weight type to the given weight type.
	 * 		  	| new.getWeightType() == weightType
	 * 
	 */
	public void setWeightType(WeightUnit weightType) {
		this.weightType = weightType;
	}

	/**
	 * Returns the amount of the weight. 
	 * 
	 */
	@Basic
	public double getWeightAmount() {
		return weightAmount;
	}

	/**
	 * Sets the weightAmount to the given weightAmount.
	 * 
	 * @param weightAmount
	 * 		  The weightAmount to assign.
	 * 
	 * @post  Sets the weight amount to the given value.
	 * 		  | new.getWeightAmount() == weightAmount
	 */
	public void setWeightAmount(double weightAmount) {
		this.weightAmount = weightAmount;
	}

	/**
	 * Changes the weight unit of the weight for the given new weight unit.
	 * 
	 * @param 	weightUnit
	 * 		  	The new weight unit to assign.
	 * 
	 * @return 	Returns a new weight in the given weightUnit.
	 * 			|new Weight.getWeigthType() == weightUnit
	 * 
	 * @throws 	Exception
	 * 		  	Throws exception if given weightUnit is null.
	 * 		  	| weightUnit == null
	 */
	public Weight toNewWeightUnit(WeightUnit weightUnit) throws Exception {
		if (weightUnit == null)
			throw new Exception("The given weight unit is invalid.");
		double conversion = getWeightType().toWeightUnit(weightUnit);
		double amount = getWeightAmount();
		amount = amount * conversion;
		return new Weight(weightUnit, amount);
	}

}

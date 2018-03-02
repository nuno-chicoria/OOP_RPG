package weight;

/**
 * An enum class representing the different weight units a weight can have and the exchange rates between them.
 * 
 * @author Murat Cem Kose & Nuno Goncalo Pires Chicoria
 *
 */
public enum WeightUnit {

	GRAM('g'), KG('k'), POUND('p');

	private final char symbol;
	private static Double[][] exchangeRates = new Double[3][3];

	/**
	 *
	 * 	@param 	symbol
	 * 		  	The symbol to assign.
	 * 
	 * 	@post 	Assigns the given symbol to symbol.
	 * 		 	| new.symbol = symbol
	 */
	private WeightUnit(char symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gives the exchange rate from one unit type to another. 
	 * 
	 * 	@param 	other
	 * 		  	New unit type for which we want the exchange rate.
	 * 
	 * 	@return 	Returns the exchange rate between current unit type to new unit type.
	 * 		  	|return == exchangeRates[this.ordinal()][other.ordinal()]
	 * 
	 * 	@throws 	Exception
	 * 			Throws an exception if the given unit type is null.
	 * 		  	| other == null
	 */
	public double toWeightUnit(WeightUnit other) throws Exception {
		if (other == null)
			throw new Exception("Non effective weight unit!");
		if (exchangeRates[this.ordinal()][other.ordinal()] == null)
			exchangeRates[this.ordinal()][other.ordinal()] = 1.00 / (exchangeRates[other.ordinal()][this.ordinal()]);
		return exchangeRates[this.ordinal()][other.ordinal()];
	} static {
		exchangeRates[GRAM.ordinal()][GRAM.ordinal()] = 1.00;
		exchangeRates[GRAM.ordinal()][KG.ordinal()] = 0.001;
		exchangeRates[GRAM.ordinal()][POUND.ordinal()] = 0.0022;
		exchangeRates[KG.ordinal()][KG.ordinal()] = 1.00;
		exchangeRates[KG.ordinal()][POUND.ordinal()] = 2.205;
		exchangeRates[POUND.ordinal()][POUND.ordinal()] = 1.00;
	}

}

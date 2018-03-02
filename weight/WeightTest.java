package weight;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeightTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test(expected = Exception.class)
	//Set an invalid Weight Type.
	public final void toNewWeightType_Illegal() throws Exception {
		Weight testWeight = new Weight(WeightUnit.GRAM, 2000);
		testWeight = testWeight.toNewWeightUnit(null);
		assertTrue(WeightUnit.GRAM == testWeight.getWeightType());
		}
	
	@Test
	//Set a new Weight Type.
	public final void toNewWeightType_Legal() throws Exception {
		Weight testWeight = new Weight(WeightUnit.GRAM, 2000);
		testWeight = testWeight.toNewWeightUnit(WeightUnit.KG);
		assertTrue(2.0 == testWeight.getWeightAmount());
		}
	
}

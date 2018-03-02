package items;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import weight.*;

public class PursesTest {

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
	//Break the purse trying to add more dukats than it can hold.
	public final void changeDukats_Illegal1() throws Exception {
		Purses testPurse = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse.changeDukats(1001);
		assertEquals(0, testPurse.getDukatAmount());
		assertFalse(testPurse.getIsFunctional());
		}
	
	@Test(expected = Exception.class)
	//Try to change the amount of dukats to a negative value.
	public final void changeDukats_Illegal2() throws Exception {
		Purses testPurse = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse.changeDukats(900);
		testPurse.changeDukats(-910);
		assertEquals(900, testPurse.getDukatAmount());
		assertTrue(testPurse.getIsFunctional());
		}
	
	@Test
	//Add 900 dukats to the purse.
	public final void changeDukats_Legal1() throws Exception {
		Purses testPurse = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse.changeDukats(900);
		assertEquals(900, testPurse.getDukatAmount());
		assertTrue(testPurse.getIsFunctional());
		}
	
	@Test
	//Add 10 dukats to a purse.
	public final void changeDukats_Legal2() throws Exception {
		Purses testPurse = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse.changeDukats(900);
		testPurse.changeDukats(10);
		assertEquals(910, testPurse.getDukatAmount());
		assertTrue(testPurse.getIsFunctional());
		}
	
	@Test
	//Subtract 10 dukats from a purse.
	public final void changeDukats_Legal3() throws Exception {
		Purses testPurse = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse.changeDukats(900);
		testPurse.changeDukats(-10);
		assertEquals(890, testPurse.getDukatAmount());
		assertTrue(testPurse.getIsFunctional());
		}
	
	@Test(expected = Exception.class)
	//Transfer more dukats than the ones the purse has.
	public final void transferDukats_Illegal1() throws Exception {
		Purses testPurse1 = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		Purses testPurse2 = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse1.changeDukats(100);
		testPurse1.transferDukats(testPurse2, 200);
		assertEquals(100, testPurse1.getDukatAmount());
		assertEquals(0, testPurse2.getDukatAmount());
		}
	
	@Test(expected = Exception.class)
	//Transfer dukats to a broken purse.
	public final void transferDukats_Illegal2() throws Exception {
		Purses testPurse1 = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		Purses testPurse2 = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse1.changeDukats(100);
		testPurse2.changeDukats(1001);
		testPurse1.transferDukats(testPurse2, 90);
		assertEquals(100, testPurse1.getDukatAmount());
		assertEquals(0, testPurse2.getDukatAmount());
		}
	
	@Test
	//Transfer dukats to another purse.
	public final void transferDukats_Legal() throws Exception {
		Purses testPurse1 = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		Purses testPurse2 = new Purses(10, new Weight (WeightUnit.GRAM, 100), 1000);
		testPurse1.changeDukats(100);
		testPurse2.changeDukats(100);
		testPurse1.transferDukats(testPurse2, 90);
		assertEquals(10, testPurse1.getDukatAmount());
		assertEquals(190, testPurse2.getDukatAmount());
		}
	
}

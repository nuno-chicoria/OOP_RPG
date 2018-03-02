package items;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import creatures.*;
import weight.*;

public class BackpacksTest {

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
	//Add a weapon that goes over the weight limit.
	public final void addContent_Illegal() throws Exception {
		Weapons weapon1 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon2 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon3 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Backpacks backpack = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 500));
		backpack.addContent(weapon1, weapon2, weapon3);
		}
	
	@Test
	//Add contents to backpacks.
	public final void addContent_Legal() throws Exception {
		Weapons weapon1 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon2 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon3 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Backpacks backpack = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		backpack.addContent(weapon1);
		backpack.addContent(weapon2, weapon3, null);
		assertTrue(backpack.getContents().contains(weapon1));
		assertTrue(backpack.getContents().contains(weapon2));
		assertTrue(backpack.getContents().contains(weapon3));
		}
	
	@Test(expected = Exception.class)
	//Transfer an item that is not in the backpack.
	public final void transferContent_Illegal1() throws Exception {
		Weapons weapon1 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon2 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon3 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Backpacks backpack2 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		Backpacks backpack1 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		Monsters monster1 = new Monsters("Abc", 100, 2, weapon2, backpack2);
		backpack1.addContent(weapon1);
		backpack1.transferContent(weapon3, monster1);
	}
	
	@Test(expected = Exception.class)
	//Transfer null.
	public final void transferContent_Illegal2() throws Exception {
		Weapons weapon1 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon2 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Backpacks backpack2 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		Backpacks backpack1 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		Monsters monster1 = new Monsters("Abc", 100, 2, weapon2, backpack2);
		backpack1.addContent(weapon1);
		backpack1.transferContent(null, monster1);
	}
	
	@Test
	//Transfer contents from one backpack to a monster.
	public final void transferContent_Legal1() throws Exception {
		Weapons weapon1 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon2 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon3 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Backpacks backpack2 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		Backpacks backpack1 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		Monsters monster1 = new Monsters("Abc", 100, 2, weapon2, backpack2);
		backpack1.addContent(weapon1, weapon3);
		backpack1.transferContent(weapon1, monster1);
		assertFalse(backpack1.getContents().contains(weapon1));
		assertEquals(monster1.getAnchor(1), weapon1);
	}
	
	@Test
	//Transfer an item from one backpack to another backpack.
	public final void transferContent_Legal2() throws Exception {
		Weapons weapon1 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon2 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Weapons weapon3 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Backpacks backpack1 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		Backpacks backpack2 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		backpack1.addContent(weapon1, weapon2, weapon3);
		backpack1.transferContent(weapon1, backpack2);
		assertFalse(backpack1.getContents().contains(weapon1));
		assertTrue(backpack2.getContents().contains(weapon1));
		}
	
	@Test(expected = Exception.class)
	//Find the heaviest item in a backpack where there is zero items.
	public final void heaviest_Illegal() throws Exception {
		Backpacks backpack1 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		backpack1.heaviestItem();
	}
	
	@Test
	//Find the heaviest item in a backpack.
	public final void heaviest_Legal() throws Exception {
		Weapons weapon1 = new Weapons (100, new Weight (WeightUnit.GRAM, 200));
		Backpacks backpack1 = new Backpacks(100, new Weight (WeightUnit.GRAM, 10), new Weight (WeightUnit.GRAM, 700));
		backpack1.addContent(weapon1);
		backpack1.heaviestItem();
	}
	
}

package items;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import creatures.*;
import weight.*;

public class WeaponsTest {

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
	//Trying to change the damage of a destroyed weapon.
	public final void setDamage_Illegal() throws Exception {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("VFGHJK", 100, 2, testWeapon, testBackpack);
		Test.removeAnchor(0);
		testWeapon.setDamage();
		assertFalse(testWeapon.getIsFunctional());
	}
	
	@Test
	//Check if the damage of a weapon is between the accepted values.
	public final void setDamage_Legal() throws Exception {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		assertTrue(testWeapon.getDamage() < 21);
		assertTrue(0 < testWeapon.getDamage());
	}
	
	@Test(expected = Exception.class)
	//Transfer a weapon from a monster to a backpack.
	public final void transferWeapon_Illegal() throws Exception {
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon2 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack1 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test1 = new Monsters("Test1", 100, 2, testWeapon2, testBackpack1);
		testWeapon1.transferWeapon(Test1);
	}
	
	@Test
	//Transfer a weapon from a monster to another.
	public final void transferWeapon_Legal() throws Exception {
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon2 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack1 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Backpacks testBackpack2 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test1 = new Monsters("Test1", 100, 2, testWeapon1, testBackpack1);
		Monsters Test2 = new Monsters("Test2", 100, 2, testWeapon2, testBackpack2);
		testWeapon1.transferWeapon(Test2);
		assertEquals(Test1.getAnchor(0), null);
		assertEquals(Test2.getAnchor(1), testWeapon1);
		assertTrue(testWeapon1.getIsFunctional());
	}
	
}

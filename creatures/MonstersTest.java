package creatures;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import items.*;
import weight.*;

public class MonstersTest {

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
	//Check a name that doesn't have the minimum number of characters.
	public final void checkName_Illegal1() throws Exception {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		new Monsters("A'", 100, 2, testWeapon, testBackpack);
		}
	
	@Test(expected = Exception.class)
	//Check a name with the minimum number of characters but illegal characters.
	public final void checkName_Illegal2() throws Exception {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		new Monsters("œ ∑æ®™¥†ı øπåß∂ƒ˙ˇ¯„‘¸Ω«©√∫¬¬µ“", 100, 2, testWeapon, testBackpack);
		}
	
	@Test(expected = Exception.class)
	//Check a name that does not start with a capital letter.
	public final void checkName_Illegal3() throws Exception {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		new Monsters("abc", 100, 2, testWeapon, testBackpack);
		}
	
	@Test(expected = Exception.class)
	//Check a null name.
	public final void checkName_Illegal4() throws Exception {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		new Monsters(null, 100, 2, testWeapon, testBackpack);
		}
	
	@Test
	//Check a valid name.
	public final void checkName_Legal() throws Exception {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		new Monsters("Test", 100, 2, testWeapon, testBackpack);
	}
	
	@Test(expected = Exception.class)
	//Set the hitpoints above the maximum hitpoints for a monster.
	public final void checkMaxHitponts_Illegal() throws Exception{
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("Test", 100, 2, testWeapon, testBackpack);
		Test.setHitpoints(110);
	}
	
	@Test
	//Set the hitpoints for a value bellow the maximum hitpoints.
	public final void checkMaxHitponts_Legal() throws Exception{
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("Test", 100, 2, testWeapon, testBackpack);
		Test.setHitpoints(90);
	}
	
	@Test
	//Check the isPrime method with a number that is not prime.
	public final void check_isPrime_Illegal() throws Exception  {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("Test", 100, 6, testWeapon, testBackpack);
		assertFalse(Test.isPrime(4));
	}

	@Test
	//Check the isPrime method with a prime number.
	public final void check_isPrime_Legal() throws Exception  {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("Test", 100, 6, testWeapon, testBackpack);
		assertTrue(Test.isPrime(2));
	}
	
	@Test(expected = Exception.class)
	//Trying to add a weapon to an anchor position that does not exist.
	public final void check_changeAnchors_Illegal1() throws Exception  {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("Test", 100, 2, testWeapon, testBackpack);
		Test.changeAnchors(3, testWeapon1);
	}
	
	@Test(expected = Exception.class)
	//Trying to add a purse to a anchor position that is for weapons.
	public final void check_changeAnchors_Illegal2() throws Exception  {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("Test", 100, 2, testWeapon, testBackpack);
		Purses testPurse = new Purses (10, new Weight(WeightUnit.GRAM, 10), 100);
		Test.changeAnchors(0, testPurse);
	}
	
	@Test(expected = Exception.class)
	//Trying to add a weapon to an anchor position that does not exist.
	public final void check_changeAnchors_Illegal3() throws Exception  {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("Test", 100, 2, testWeapon, testBackpack);
		Test.changeAnchors(-50, testWeapon1);
		}
	
	@Test
	//Trying to remove a backpack from its anchor position in a monster.
	public final void check_removeAnchor_Legal1() throws Exception  {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("VFGHJK", 100, 2, testWeapon, testBackpack);
		Test.removeAnchor(2);
		assertEquals(Test.getAnchor(2), null);
	}
	
	@Test
	//Trying to remove a weapon from its anchor position in a monster and check if its destroyed.
	public final void check_removeAnchor_Legal2() throws Exception  {
		Weapons testWeapon = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test = new Monsters("VFGHJK", 100, 2, testWeapon, testBackpack);
		Test.removeAnchor(0);
		assertFalse(testWeapon.getIsFunctional());
	}
	
	@Test(expected = Exception.class)
	//Transfering an item that the monster does not have.
	public final void check_transferItem_Illegal1() throws Exception  {
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Purses testPurse = new Purses (10, new Weight(WeightUnit.GRAM, 10), 100);
		Backpacks testBackpack1 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test1 = new Monsters("Test1", 100, 4, testWeapon1, testBackpack1);
		Test1.transferItem(testPurse);
	}
	
	@Test(expected = Exception.class)
	//Trying to transfer a weapon that already has an holder.
	public final void check_transferItem_Illegal2() throws Exception  {
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon2 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon3 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack1 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Backpacks testBackpack2 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test1 = new Monsters("Test1", 100, 3, testWeapon1, testBackpack1);
		Monsters Test2 = new Monsters("Test1", 100, 3, testWeapon3, testBackpack2);
		Test1.addAnchor(testWeapon2);
		Test1.transferItem(Test2.getAnchor(0));
	}

	@Test
	//Transfer a purse from one monster to another.
	public final void check_transferItem_Legal1() throws Exception  {
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon2 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Purses testPurse = new Purses (10, new Weight(WeightUnit.GRAM, 10), 100);
		Backpacks testBackpack1 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Backpacks testBackpack2 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test1 = new Monsters("Test1", 100, 4, testWeapon1, testBackpack1);
		Monsters Test2 = new Monsters("Test2", 100, 4, testWeapon2, testBackpack2);
		Test2.addAnchor(testPurse);
		Test1.transferItem(Test2.getAnchor(3));
		assertEquals(testPurse, Test1.getAnchor(3));
		assertEquals(testPurse.getHolder(), Test1);
	}
	
	@Test
	//Check that if we add a weapon to a monster that already has two weapons the weapon will go to the 4th anchor.
	public final void check_transferItem_Legal2() throws Exception  {
		Weapons testWeapon1 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon2 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Weapons testWeapon3 = new Weapons (10, new Weight(WeightUnit.GRAM, 10));
		Backpacks testBackpack1 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Backpacks testBackpack2 = new Backpacks (10, new Weight(WeightUnit.GRAM, 10), new Weight(WeightUnit.GRAM, 1000));
		Monsters Test1 = new Monsters("Test1", 100, 4, testWeapon1, testBackpack1);
		Monsters Test2 = new Monsters("Test2", 100, 4, testWeapon2, testBackpack2);
		Test2.addAnchor(testWeapon3);
		Test2.transferItem(Test1.getAnchor(0));
		assertEquals(testWeapon1, Test2.getAnchor(3));
	}


}

package v0id.api.exp.item.food;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import v0id.api.exp.player.Nutrient;

public class FoodEntry
{
	// Essentially metadata for the item. Use in your model registering.
	private final int id;
	
	// Per 100 grams
	private Map<Nutrient, Float> nutrientData = Maps.newHashMap();
	private float caloriesRestored;
	
	// Volume in inventory
	private Pair<Byte, Byte> volume = Pair.of((byte)1, (byte)1);
	
	// In grams
	private float maxWeight = 10000;
	
	// With a multiplier of 1.0 and health of 100 this food will rot in 1 month
	private float rotMultiplier = 1.0F;
	private float baseHealth = 100;
	
	// Display name
	private String unlocalizedName;
	
	public static final List<FoodEntry> allEntries = Lists.newArrayList();
	
	public FoodEntry()
	{
		this.id = index++;
		allEntries.add(this);
	}
	
	public FoodEntry withName(String s)
	{
		this.setUnlocalizedName(s);
		return this;
	}
	
	public FoodEntry withHealth(float f)
	{
		this.setBaseHealth(f);
		return this;
	}
	
	public FoodEntry withRotMultiplier(float f)
	{
		this.setMaxWeight(f);
		return this;
	}
	
	public FoodEntry withMaxWeight(float f)
	{
		this.setMaxWeight(f);
		return this;
	}
	
	public FoodEntry withVolume(byte vx, byte vy)
	{
		this.setVolume(Pair.of(vx, vy));
		return this;
	}
	
	public FoodEntry withVolume(Pair<Byte, Byte> volume)
	{
		this.setVolume(volume);
		return this;
	}
	
	public FoodEntry withCalories(float f)
	{
		this.setCaloriesRestored(f);
		return this;
	}
	
	public FoodEntry withNutrient(Nutrient n, Float f)
	{
		this.getNutrientData().put(n, f);
		return this;
	}
	
	public FoodEntry withNutrients(Map<Nutrient, Float> map)
	{
		this.setNutrientData(map);
		return this;
	}
	
	public float getCaloriesRestored()
	{
		return this.caloriesRestored;
	}

	public void setCaloriesRestored(float caloriesRestored)
	{
		this.caloriesRestored = caloriesRestored;
	}

	public int getId()
	{
		return this.id;
	}

	public Map<Nutrient, Float> getNutrientData()
	{
		return this.nutrientData;
	}

	public void setNutrientData(Map<Nutrient, Float> nutrientData)
	{
		this.nutrientData = nutrientData;
	}

	public Pair<Byte, Byte> getVolume()
	{
		return this.volume;
	}

	public void setVolume(Pair<Byte, Byte> volume)
	{
		this.volume = volume;
	}

	public float getMaxWeight()
	{
		return this.maxWeight;
	}

	public void setMaxWeight(float maxWeight)
	{
		this.maxWeight = maxWeight;
	}

	public float getRotMultiplier()
	{
		return this.rotMultiplier;
	}

	public void setRotMultiplier(float rotMultiplier)
	{
		this.rotMultiplier = rotMultiplier;
	}

	public float getBaseHealth()
	{
		return this.baseHealth;
	}

	public void setBaseHealth(float baseHealth)
	{
		this.baseHealth = baseHealth;
	}

	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}

	public void setUnlocalizedName(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	private static int index;
	
	// Mind the BALANCE_MULTIPLIER. Very unrealistic but needed so it is not frustrating to gather food.
	// Without it you would need 212 crops of barley growing CONSTANTLY to even allow bare SURVIVAL.
	// Yeah. Really puts those huge crop fields in real life into perspective as I took all numbers from wiki.
	public static final float BALANCE_MULTIPLIER = 8;
	public static final FoodEntry BARLEY = new FoodEntry().withCalories(354 * BALANCE_MULTIPLIER).withName("barley");
	public static final FoodEntry BEANS = new FoodEntry().withCalories(80 * BALANCE_MULTIPLIER).withName("beans");
	public static final FoodEntry BEETROOT = new FoodEntry().withCalories(43 * BALANCE_MULTIPLIER).withName("beetroot");
	public static final FoodEntry CABBAGE = new FoodEntry().withCalories(25 * BALANCE_MULTIPLIER).withName("cabbage");
	public static final FoodEntry CARROT = new FoodEntry().withCalories(41 * BALANCE_MULTIPLIER).withName("carrot");
	public static final FoodEntry CASSAVA = new FoodEntry().withCalories(160 * BALANCE_MULTIPLIER).withName("cassava");
	public static final FoodEntry CORN = new FoodEntry().withCalories(86 * BALANCE_MULTIPLIER).withName("corn");
	public static final FoodEntry CUCUMBER = new FoodEntry().withCalories(65 * BALANCE_MULTIPLIER).withName("cucumber");
	public static final FoodEntry EGGPLANT = new FoodEntry().withCalories(25 * BALANCE_MULTIPLIER).withName("eggplant");
	public static final FoodEntry GARLIC = new FoodEntry().withCalories(149 * BALANCE_MULTIPLIER).withName("garlic");
	public static final FoodEntry LEEK = new FoodEntry().withCalories(255 * BALANCE_MULTIPLIER).withName("leek");
	public static final FoodEntry LETTUCE = new FoodEntry().withCalories(13 * BALANCE_MULTIPLIER).withName("lettuce");
	public static final FoodEntry MILLET = new FoodEntry().withCalories(378 * BALANCE_MULTIPLIER).withName("millet");
	public static final FoodEntry OAT = new FoodEntry().withCalories(389 * BALANCE_MULTIPLIER).withName("oat");
	public static final FoodEntry ONION = new FoodEntry().withCalories(40 * BALANCE_MULTIPLIER).withName("onion");
	public static final FoodEntry PARSNIP = new FoodEntry().withCalories(75 * BALANCE_MULTIPLIER).withName("parsnip");
	public static final FoodEntry PEAS = new FoodEntry().withCalories(81 * BALANCE_MULTIPLIER).withName("peas");
	public static final FoodEntry PEPPER_GREEN = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("pepper_green");
	public static final FoodEntry PEPPER_YELLOW = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("pepper_yellow");
	public static final FoodEntry PEPPER_RED = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("pepper_red");
	public static final FoodEntry POTATO = new FoodEntry().withCalories(77 * BALANCE_MULTIPLIER).withName("potato");
	public static final FoodEntry PUMPKIN = new FoodEntry().withCalories(26 * BALANCE_MULTIPLIER).withName("pumpkin");
	public static final FoodEntry RADISH = new FoodEntry().withCalories(16 * BALANCE_MULTIPLIER).withName("radish");
	public static final FoodEntry RICE = new FoodEntry().withCalories(130 * BALANCE_MULTIPLIER).withName("rice");
	public static final FoodEntry RYE = new FoodEntry().withCalories(324 * BALANCE_MULTIPLIER).withName("rye");
	public static final FoodEntry SORGHUM = new FoodEntry().withCalories(329 * BALANCE_MULTIPLIER).withName("sorghum");
	public static final FoodEntry SPINACH = new FoodEntry().withCalories(23 * BALANCE_MULTIPLIER).withName("spinach");
	public static final FoodEntry SWEET_POTATO = new FoodEntry().withCalories(86 * BALANCE_MULTIPLIER).withName("sweet_potato");
	public static final FoodEntry TOMATO = new FoodEntry().withCalories(18 * BALANCE_MULTIPLIER).withName("tomato");
	public static final FoodEntry TURNIP = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("turnip");
	public static final FoodEntry WHEAT = new FoodEntry().withCalories(327 * BALANCE_MULTIPLIER).withName("wheat");
	
}

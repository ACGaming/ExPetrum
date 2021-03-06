package v0id.api.exp.item.food;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import v0id.api.exp.player.EnumFoodGroup;

import java.util.List;
import java.util.Map;

public class FoodEntry
{
	// Essentially metadata for the item. Use in your model registering.
	private final int id;
	
	// Per 100 grams
	private Map<EnumFoodGroup, Float> nutrientData = Maps.newHashMap();
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
	
	// Is the food poisonous?
	private boolean isPoisonous;
	
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
	
	public FoodEntry withFoodGroupData(EnumFoodGroup n, Float f)
	{
		this.getNutrientData().put(n, f);
		return this;
	}
	
	public FoodEntry withFoodGroupData(Map<EnumFoodGroup, Float> map)
	{
		this.setNutrientData(map);
		return this;
	}
	
	public FoodEntry setPoisonous()
	{
		this.setPoisonous(true);
		return this;
	}

	public FoodEntry withNutrition(EnumFoodGroup category, float value)
    {
        this.nutrientData.put(category, value);
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

	public Map<EnumFoodGroup, Float> getNutrientData()
	{
		return this.nutrientData;
	}

	public void setNutrientData(Map<EnumFoodGroup, Float> nutrientData)
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

	public boolean isPoisonous()
	{
		return this.isPoisonous;
	}

	public void setPoisonous(boolean isPoisonous)
	{
		this.isPoisonous = isPoisonous;
	}

	private static int index;
	
	// Mind the BALANCE_MULTIPLIER. Very unrealistic but needed so it is not frustrating to gather food.
	// Without it you would need 212 crops of barley growing CONSTANTLY to even allow bare SURVIVAL.
	// Yeah. Really puts those huge crop fields in real life into perspective as I took all numbers from wiki.
	public static final float BALANCE_MULTIPLIER = 4;
	public static final FoodEntry BARLEY = new FoodEntry().withCalories(354 * BALANCE_MULTIPLIER).withName("barley").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry BEANS = new FoodEntry().withCalories(80 * BALANCE_MULTIPLIER).withName("beans").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry BEETROOT = new FoodEntry().withCalories(43 * BALANCE_MULTIPLIER).withName("beetroot").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry CABBAGE = new FoodEntry().withCalories(25 * BALANCE_MULTIPLIER).withName("cabbage").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry CARROT = new FoodEntry().withCalories(41 * BALANCE_MULTIPLIER).withName("carrot").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry CASSAVA = new FoodEntry().withCalories(160 * BALANCE_MULTIPLIER).withName("cassava").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry CORN = new FoodEntry().withCalories(86 * BALANCE_MULTIPLIER).withName("corn").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry CUCUMBER = new FoodEntry().withCalories(65 * BALANCE_MULTIPLIER).withName("cucumber").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry EGGPLANT = new FoodEntry().withCalories(25 * BALANCE_MULTIPLIER).withName("eggplant").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry GARLIC = new FoodEntry().withCalories(149 * BALANCE_MULTIPLIER).withName("garlic").withNutrition(EnumFoodGroup.VEGETABLE, 2);
	public static final FoodEntry LEEK = new FoodEntry().withCalories(255 * BALANCE_MULTIPLIER).withName("leek").withNutrition(EnumFoodGroup.VEGETABLE, 2);
	public static final FoodEntry LETTUCE = new FoodEntry().withCalories(13 * BALANCE_MULTIPLIER).withName("lettuce").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry MILLET = new FoodEntry().withCalories(378 * BALANCE_MULTIPLIER).withName("millet").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry OAT = new FoodEntry().withCalories(389 * BALANCE_MULTIPLIER).withName("oat").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry ONION = new FoodEntry().withCalories(40 * BALANCE_MULTIPLIER).withName("onion").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry PARSNIP = new FoodEntry().withCalories(75 * BALANCE_MULTIPLIER).withName("parsnip").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry PEAS = new FoodEntry().withCalories(81 * BALANCE_MULTIPLIER).withName("peas").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry PEPPER_GREEN = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("pepper_green").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry PEPPER_YELLOW = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("pepper_yellow").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry PEPPER_RED = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("pepper_red").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry POTATO = new FoodEntry().withCalories(77 * BALANCE_MULTIPLIER).withName("potato").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry PUMPKIN = new FoodEntry().withCalories(26 * BALANCE_MULTIPLIER).withName("pumpkin").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry RADISH = new FoodEntry().withCalories(16 * BALANCE_MULTIPLIER).withName("radish").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry RICE = new FoodEntry().withCalories(130 * BALANCE_MULTIPLIER).withName("rice").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry RYE = new FoodEntry().withCalories(324 * BALANCE_MULTIPLIER).withName("rye").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry SORGHUM = new FoodEntry().withCalories(329 * BALANCE_MULTIPLIER).withName("sorghum").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry SPINACH = new FoodEntry().withCalories(23 * BALANCE_MULTIPLIER).withName("spinach").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry SWEET_POTATO = new FoodEntry().withCalories(86 * BALANCE_MULTIPLIER).withName("sweet_potato").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry TOMATO = new FoodEntry().withCalories(18 * BALANCE_MULTIPLIER).withName("tomato").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry TURNIP = new FoodEntry().withCalories(20 * BALANCE_MULTIPLIER).withName("turnip").withNutrition(EnumFoodGroup.VEGETABLE, 4);
	public static final FoodEntry WHEAT = new FoodEntry().withCalories(327 * BALANCE_MULTIPLIER).withName("wheat").withNutrition(EnumFoodGroup.GRAIN, 4);
	public static final FoodEntry BLACKBERRY = new FoodEntry().withCalories(43 * BALANCE_MULTIPLIER).withName("blackberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry CLOUDBERRY = new FoodEntry().withCalories(51 * BALANCE_MULTIPLIER).withName("cloudberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry RASPBERRY = new FoodEntry().withCalories(51 * BALANCE_MULTIPLIER).withName("raspberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry BLUEBERRY = new FoodEntry().withCalories(57 * BALANCE_MULTIPLIER).withName("blueberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry CRANBERRY = new FoodEntry().withCalories(46 * BALANCE_MULTIPLIER).withName("cranberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry ELDERBERRY = new FoodEntry().withCalories(73 * BALANCE_MULTIPLIER).withName("elderberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry GOOSEBERRY = new FoodEntry().withCalories(44 * BALANCE_MULTIPLIER).withName("gooseberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry HACKBERRY = new FoodEntry().withCalories(54 * BALANCE_MULTIPLIER).withName("hackberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry STRAWBERRY = new FoodEntry().withCalories(33 * BALANCE_MULTIPLIER).withName("strawberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry WOLFBERRY = new FoodEntry().withCalories(37 * BALANCE_MULTIPLIER).withName("wolfberry").setPoisonous().withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry SALMONBERRY = new FoodEntry().withCalories(47 * BALANCE_MULTIPLIER).withName("salmonberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
	public static final FoodEntry CROWBERRY = new FoodEntry().withCalories(43 * BALANCE_MULTIPLIER).withName("crowberry").withNutrition(EnumFoodGroup.VEGETABLE, 1);
    public static final FoodEntry APPLE = new FoodEntry().withCalories(52 * BALANCE_MULTIPLIER).withName("apple").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry OLIVE = new FoodEntry().withCalories(148 * BALANCE_MULTIPLIER).withName("olive").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry PEACH = new FoodEntry().withCalories(39 * BALANCE_MULTIPLIER).withName("peach").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry ORANGE = new FoodEntry().withCalories(47 * BALANCE_MULTIPLIER).withName("orange").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry PEAR = new FoodEntry().withCalories(57 * BALANCE_MULTIPLIER).withName("pear").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry PLUM = new FoodEntry().withCalories(46 * BALANCE_MULTIPLIER).withName("plum").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry BANANA = new FoodEntry().withCalories(89 * BALANCE_MULTIPLIER).withName("banana").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry LEMON = new FoodEntry().withCalories(29 * BALANCE_MULTIPLIER).withName("lemon").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry APRICOT = new FoodEntry().withCalories(48 * BALANCE_MULTIPLIER).withName("apricot").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry WALNUT = new FoodEntry().withCalories(654 * BALANCE_MULTIPLIER).withName("walnut").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry CHERRY = new FoodEntry().withCalories(63 * BALANCE_MULTIPLIER).withName("cherry").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry POMEGRANATE = new FoodEntry().withCalories(83 * BALANCE_MULTIPLIER).withName("pomegranate").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry GRAPEFRUIT = new FoodEntry().withCalories(33 * BALANCE_MULTIPLIER).withName("grapefruit").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry AVOCADO = new FoodEntry().withCalories(160 * BALANCE_MULTIPLIER).withName("avocado").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry CARAMBOLA = new FoodEntry().withCalories(31 * BALANCE_MULTIPLIER).withName("carambola").withNutrition(EnumFoodGroup.FRUIT, 4);
    public static final FoodEntry EGG = new FoodEntry().withCalories(155 * BALANCE_MULTIPLIER).withName("egg").withNutrition(EnumFoodGroup.PROTEIN, 4);
    public static final FoodEntry CHICKEN_RAW = new FoodEntry().withCalories(100 * BALANCE_MULTIPLIER).withName("chicken_raw").withNutrition(EnumFoodGroup.PROTEIN, 4);
    public static final FoodEntry CHICKEN_COOKED = new FoodEntry().withCalories(239 * BALANCE_MULTIPLIER).withName("chicken_cooked").withNutrition(EnumFoodGroup.PROTEIN, 4);
    public static final FoodEntry BEEF_RAW = new FoodEntry().withCalories(110 * BALANCE_MULTIPLIER).withName("beef_raw").withNutrition(EnumFoodGroup.PROTEIN, 4);
    public static final FoodEntry BEEF_COOKED = new FoodEntry().withCalories(250 * BALANCE_MULTIPLIER).withName("beef_cooked").withNutrition(EnumFoodGroup.PROTEIN, 4);
	public static final FoodEntry LAMB_RAW = new FoodEntry().withCalories(160 * BALANCE_MULTIPLIER).withName("lamb_raw").withNutrition(EnumFoodGroup.PROTEIN, 4);
	public static final FoodEntry LAMB_COOKED = new FoodEntry().withCalories(294 * BALANCE_MULTIPLIER).withName("lamb_cooked").withNutrition(EnumFoodGroup.PROTEIN, 4);
	public static final FoodEntry PORK_RAW = new FoodEntry().withCalories(100 * BALANCE_MULTIPLIER).withName("pork_raw").withNutrition(EnumFoodGroup.PROTEIN, 4);
	public static final FoodEntry PORK_COOKED = new FoodEntry().withCalories(231 * BALANCE_MULTIPLIER).withName("pork_cooked").withNutrition(EnumFoodGroup.PROTEIN, 4);
	public static final FoodEntry CHEESE = new FoodEntry().withCalories(402 * BALANCE_MULTIPLIER).withName("cheese").withNutrition(EnumFoodGroup.DAIRY, 4);
	public static final FoodEntry GRAIN_BARLEY = new FoodEntry().withCalories(354 * BALANCE_MULTIPLIER).withName("grain_barley").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry GRAIN_MILLET = new FoodEntry().withCalories(378 * BALANCE_MULTIPLIER).withName("grain_millet").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry GRAIN_OAT = new FoodEntry().withCalories(389 * BALANCE_MULTIPLIER).withName("grain_oat").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry GRAIN_RICE = new FoodEntry().withCalories(130 * BALANCE_MULTIPLIER).withName("grain_rice").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry GRAIN_RYE = new FoodEntry().withCalories(324 * BALANCE_MULTIPLIER).withName("grain_rye").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry GRAIN_SORGHUM = new FoodEntry().withCalories(329 * BALANCE_MULTIPLIER).withName("grain_sorghum").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry GRAIN_WHEAT = new FoodEntry().withCalories(327 * BALANCE_MULTIPLIER).withName("grain_wheat").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry FLOUR_BARLEY = new FoodEntry().withCalories(354 * BALANCE_MULTIPLIER).withName("flour_barley").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry FLOUR_MILLET = new FoodEntry().withCalories(378 * BALANCE_MULTIPLIER).withName("flour_millet").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry FLOUR_OAT = new FoodEntry().withCalories(389 * BALANCE_MULTIPLIER).withName("flour_oat").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry FLOUR_RICE = new FoodEntry().withCalories(130 * BALANCE_MULTIPLIER).withName("flour_rice").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry FLOUR_RYE = new FoodEntry().withCalories(324 * BALANCE_MULTIPLIER).withName("flour_rye").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry FLOUR_SORGHUM = new FoodEntry().withCalories(329 * BALANCE_MULTIPLIER).withName("flour_sorghum").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry FLOUR_WHEAT = new FoodEntry().withCalories(327 * BALANCE_MULTIPLIER).withName("flour_wheat").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry DOUGH_BARLEY = new FoodEntry().withCalories(442 * BALANCE_MULTIPLIER).withName("dough_barley").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry DOUGH_MILLET = new FoodEntry().withCalories(472 * BALANCE_MULTIPLIER).withName("dough_millet").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry DOUGH_OAT = new FoodEntry().withCalories(486 * BALANCE_MULTIPLIER).withName("dough_oat").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry DOUGH_RICE = new FoodEntry().withCalories(260 * BALANCE_MULTIPLIER).withName("dough_rice").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry DOUGH_RYE = new FoodEntry().withCalories(405 * BALANCE_MULTIPLIER).withName("dough_rye").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry DOUGH_SORGHUM = new FoodEntry().withCalories(411 * BALANCE_MULTIPLIER).withName("dough_sorghum").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry DOUGH_WHEAT = new FoodEntry().withCalories(408 * BALANCE_MULTIPLIER).withName("dough_wheat").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry BREAD_BARLEY = new FoodEntry().withCalories(442 * BALANCE_MULTIPLIER).withName("bread_barley").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry BREAD_MILLET = new FoodEntry().withCalories(472 * BALANCE_MULTIPLIER).withName("bread_millet").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry BREAD_OAT = new FoodEntry().withCalories(486 * BALANCE_MULTIPLIER).withName("bread_oat").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry BREAD_RICE = new FoodEntry().withCalories(420 * BALANCE_MULTIPLIER).withName("bread_rice").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry BREAD_RYE = new FoodEntry().withCalories(405 * BALANCE_MULTIPLIER).withName("bread_rye").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry BREAD_SORGHUM = new FoodEntry().withCalories(411 * BALANCE_MULTIPLIER).withName("bread_sorghum").withNutrition(EnumFoodGroup.GRAIN, 4);
    public static final FoodEntry BREAD_WHEAT = new FoodEntry().withCalories(408 * BALANCE_MULTIPLIER).withName("bread_wheat").withNutrition(EnumFoodGroup.GRAIN, 4);
}

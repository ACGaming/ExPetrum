package v0id.exp.item.tool;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;
import v0id.api.exp.combat.EnumWeaponWeight;
import v0id.api.exp.combat.IWeapon;
import v0id.api.exp.combat.WeaponType;
import v0id.api.exp.data.ExPCreativeTabs;
import v0id.api.exp.data.ExPOreDict;
import v0id.api.exp.data.ExPRegistryNames;
import v0id.api.exp.data.IOreDictEntry;
import v0id.api.exp.inventory.IWeightProvider;
import v0id.api.exp.item.IKnife;
import v0id.api.exp.item.IShears;
import v0id.api.exp.metal.EnumToolClass;
import v0id.api.exp.metal.EnumToolStats;

import java.util.Arrays;

public class ItemKnife extends ItemExPWeapon implements IWeapon, IWeightProvider, IOreDictEntry, IShears, IKnife
{
	public ItemKnife(EnumToolStats stats)
	{
		super(stats, EnumToolClass.KNIFE);
		this.setSelfRegistryName(ExPRegistryNames.asLocation(ExPRegistryNames.itemKnife));
		this.setUnlocalizedName(this.getRegistryName().toString().replace(':', '.'));
		this.setCreativeTab(ExPCreativeTabs.tabTools);
		this.setHasSubtypes(true);
	}

	@Override
	public void registerOreDictNames()
	{
		Arrays.stream(EnumToolStats.values()).forEach(mat -> Arrays.stream(ExPOreDict.itemKnife).forEach(name ->
		{
			OreDictionary.registerOre(name, new ItemStack(this, 1, mat.ordinal()));
			OreDictionary.registerOre(name + Character.toUpperCase(mat.name().charAt(0)) + mat.name().toLowerCase().substring(1), new ItemStack(this, 1, mat.ordinal()));
		}));
	}

	@Override
	public float provideWeight(ItemStack item)
	{
		return this.getStats(item).getWeight() * this.getToolClass().getWeight();
	}

	@Override
	public Pair<Byte, Byte> provideVolume(ItemStack item)
	{
		return IWeightProvider.DEFAULT_VOLUME;
	}

	@Override
	public WeaponType getType(ItemStack is)
	{
		return WeaponType.DAGGER;
	}

	@Override
	public EnumWeaponWeight getWeaponWeight(ItemStack is)
	{
		return EnumWeaponWeight.LIGHT;
	}

	@Override
	public float getAttackDamage(ItemStack is)
	{
		return this.getStats(is).getDamage() * this.getStats(is).getWeaponDamageMultiplier() * 0.8F;
	}

	@Override
	public float getAttackSpeed(ItemStack is)
	{
		return -1.5F;
	}

	@Override
	public int getWoolAmount(EntityLivingBase sheep, ItemStack shears)
	{
		return 1;
	}

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		ItemStack ret = itemStack.copy();
		ret.setItemDamage(ret.getItemDamage() + 1);
		if (ret.getItemDamage() >= ret.getMaxDamage())
		{
			return ItemStack.EMPTY;
		}

		return ret;
	}
}

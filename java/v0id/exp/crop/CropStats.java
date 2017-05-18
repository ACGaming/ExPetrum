package v0id.exp.crop;

import java.util.EnumMap;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.Constants.NBT;
import v0id.api.core.util.nbt.NBTList;
import v0id.api.exp.tile.crop.EnumCrop;
import v0id.api.exp.tile.crop.EnumPlantNutrient;
import v0id.api.exp.world.Calendar;
import v0id.api.exp.world.IExPWorld;
import v0id.api.exp.world.TemperatureRange;

/**
 * Stats that are bound to the crop tile entity and seeds
 * @author V0idWa1k3r
 *
 */
public class CropStats implements INBTSerializable<NBTTagCompound>
{
	public TemperatureRange[] growthRanges = new TemperatureRange[3];
	public Pair<Float, Float> humidityGrowthRange;
	public int generation;
	public boolean wild;
	public EnumCrop type = EnumCrop.DEAD;
	public Calendar plantedAt = new Calendar();
	public Optional<Calendar> uprootedAt = Optional.empty();
	public float health;
	public float growthRate;
	public float waterConsumption;
	public EnumMap<EnumPlantNutrient, Float> nutrientConsumption = Maps.newEnumMap(EnumPlantNutrient.class);
	
	// Ensures that nothing is null
	public CropStats()
	{
		for (int i = 0; i < 3; ++i)
		{
			this.growthRanges[i] = new TemperatureRange();
		}
	}
	
	public NBTTagCompound createItemNBT(World w)
	{
		NBTTagCompound ret = this.serializeNBT();
		this.uprootedAt = Optional.of(IExPWorld.of(w).today());
		ret.setTag("uprootedAt", this.uprootedAt.get().serializeNBT());
		return ret;
	}
	
	public void createFromItemNBT(NBTTagCompound tag)
	{
		this.uprootedAt = Optional.of(new Calendar());
		this.uprootedAt.get().deserializeNBT((NBTTagLong) tag.getTag("uprootedAt"));
		this.deserializeNBT(tag);
	}
	
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound ret = new NBTTagCompound();
		ret.setTag("tGrowthRangeMinimal", this.growthRanges[0].serializeNBT());
		ret.setTag("tGrowthRangeOptimal", this.growthRanges[1].serializeNBT());
		ret.setTag("tGrowthRangePerfect", this.growthRanges[2].serializeNBT());
		NBTTagCompound humidityTag = new NBTTagCompound();
		humidityTag.setFloat("min", this.humidityGrowthRange.getLeft());
		humidityTag.setFloat("max", this.humidityGrowthRange.getRight());
		ret.setTag("hGrowthRange", humidityTag);
		ret.setInteger("generation", this.generation);
		ret.setBoolean("wild", this.wild);
		ret.setByte("type", (byte) this.type.ordinal());
		ret.setTag("plantedAt", this.plantedAt.serializeNBT());
		ret.setFloat("health", this.health);
		ret.setFloat("growthRate", this.growthRate);
		ret.setFloat("waterConsumption", this.waterConsumption);
		NBTTagList lst = new NBTTagList();
		this.nutrientConsumption.forEach((EnumPlantNutrient nutrient, Float f) -> {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setByte("nutrient", (byte) nutrient.ordinal());
			tag.setFloat("amount", f);
			lst.appendTag(tag);
		});
		
		ret.setTag("nutrientConsumption", lst);
		return ret;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.growthRanges[0].deserializeNBT(nbt.getCompoundTag("tGrowthRangeMinimal"));
		this.growthRanges[1].deserializeNBT(nbt.getCompoundTag("tGrowthRangeOptimal"));
		this.growthRanges[2].deserializeNBT(nbt.getCompoundTag("tGrowthRangePerfect"));
		this.humidityGrowthRange = Pair.of(nbt.getCompoundTag("hGrowthRange").getFloat("min"), nbt.getCompoundTag("hGrowthRange").getFloat("max"));
		this.generation = nbt.getInteger("generation");
		this.wild = nbt.getBoolean("wild");
		this.type = EnumCrop.values()[nbt.getByte("type")];
		this.plantedAt = new Calendar();
		this.plantedAt.deserializeNBT((NBTTagLong) nbt.getTag("plantedAt"));
		this.health = nbt.getFloat("health");
		this.growthRate = nbt.getFloat("growthRate");
		this.waterConsumption = nbt.getFloat("waterConsumption");
		this.nutrientConsumption.clear();
		NBTList.<NBTTagCompound>of(nbt.getTagList("nutrientConsumption", NBT.TAG_COMPOUND)).forEach(tag -> this.nutrientConsumption.put(EnumPlantNutrient.values()[tag.getByte("nutrient")], tag.getFloat("anount")));
	}

}
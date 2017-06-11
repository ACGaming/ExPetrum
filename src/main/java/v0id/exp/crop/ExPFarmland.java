package v0id.exp.crop;

import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.stream.Stream;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import v0id.api.core.network.PacketType;
import v0id.api.core.network.VoidNetwork;
import v0id.api.core.util.DimBlockPos;
import v0id.api.exp.block.property.ExPBlockProperties;
import v0id.api.exp.tile.crop.EnumPlantNutrient;
import v0id.api.exp.tile.crop.IFarmland;
import v0id.api.exp.world.Calendar;

public class ExPFarmland implements IFarmland
{
	public TileEntity holder;
	public EnumMap<EnumPlantNutrient, Float> nutrientData = new EnumMap(EnumPlantNutrient.class);
	public float moistureLevel;
	public Calendar timeKeeper = new Calendar();
	
	public ExPFarmland()
	{
		Stream.of(EnumPlantNutrient.values()).forEach(n -> this.nutrientData.put(n, 0F));
	}
	
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound ret = new NBTTagCompound();
		NBTTagList lst = new NBTTagList();
		for (Entry<EnumPlantNutrient, Float> e : this.nutrientData.entrySet())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByte("key", (byte) e.getKey().ordinal());
			nbt.setFloat("value", e.getValue());
			lst.appendTag(nbt);
		}
		
		ret.setTag("nutrientData", lst);
		ret.setFloat("moisture", this.moistureLevel);
		ret.setTag("calendar", this.timeKeeper.serializeNBT());
		return ret;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.moistureLevel = nbt.getFloat("moisture");
		for (NBTBase tag : nbt.getTagList("nutrientData", NBT.TAG_COMPOUND))
		{
			NBTTagCompound tagCompound = (NBTTagCompound) tag;
			this.nutrientData.put(EnumPlantNutrient.values()[tagCompound.getByte("key")], tagCompound.getFloat("value"));
		}
		
		if (nbt.hasKey("calendar"))
		{
			this.timeKeeper.deserializeNBT((NBTTagLong) nbt.getTag("calendar"));
		}
	}

	@Override
	public float getNutrient(EnumPlantNutrient nut)
	{
		return this.nutrientData.get(nut);
	}

	@Override
	public void setNutrient(EnumPlantNutrient nut, float newVal)
	{
		this.nutrientData.put(nut, newVal);
	}

	@Override
	public float getMoisture()
	{
		return this.moistureLevel;
	}

	@Override
	public void setMoisture(float newVal)
	{
		this.moistureLevel = newVal;
	}

	@Override
	public float getGrowthMultiplier()
	{
		return this.holder.getWorld().getBlockState(this.holder.getPos()).getValue(ExPBlockProperties.DIRT_CLASS).getGrowthMultiplier();
	}

	@Override
	public void onWorldTick()
	{
		CropLogic.onFarmlandUpdate(this);
		this.setDirty();
	}

	@Override
	public TileEntity getContainer()
	{
		return this.holder;
	}

	@Override
	public void setDirty()
	{
		NBTTagCompound sent = new NBTTagCompound();
		sent.setTag("tileData", this.getContainer().serializeNBT());
		sent.setTag("blockPosData", new DimBlockPos(this.getContainer().getPos(), this.getContainer().getWorld().provider.getDimension()).serializeNBT());
		VoidNetwork.sendDataToAllAround(PacketType.TileData, sent, new TargetPoint(this.getContainer().getWorld().provider.getDimension(), this.getContainer().getPos().getX(), this.getContainer().getPos().getY(), this.getContainer().getPos().getZ(), 96));
	}

	public static ExPFarmland createDefault()
	{
		return new ExPFarmland();
	}
	
	public static ExPFarmland createWithTile(TileEntity tile)
	{
		ExPFarmland ret = createDefault();
		ret.holder = tile;
		return ret;
	}
	
	public static int getColor(IFarmland of)
	{
		if (of == null)
		{
			return -1;
		}
		
		float colorMultiplier = 0.25F + (1 - of.getMoisture()) / 1.5F;
		return ((int)(colorMultiplier * 255F) << 16) + ((int)(colorMultiplier * 255F) << 8) + (int)(colorMultiplier * 255F);
	}
}

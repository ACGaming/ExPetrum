package v0id.exp.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import v0id.api.exp.block.EnumTreeType;
import v0id.api.exp.item.IContainerTickable;
import v0id.api.exp.recipe.RecipesBarrel;
import v0id.api.exp.tile.ISyncableTile;
import v0id.exp.item.ItemWoodenBucket;
import v0id.exp.net.ExPNetwork;
import v0id.exp.util.Helpers;
import v0id.exp.util.temperature.TemperatureUtils;

import javax.annotation.Nullable;
import java.util.Stack;

public class TileBarrel extends TileEntity implements ITickable, ISyncableTile
{
    public ItemStackHandler inventory = new ItemStackHandler(2);
    public FluidTank fluidInventory = new FluidTank(10000);
    public int recipeProgress;
    public RecipesBarrel.IRecipeBarrel currentRecipe;
    public Stack<ItemStack> results = new Stack<>();
    public EnumTreeType treeType = EnumTreeType.KALOPANAX;

    public void sendUpdatePacket()
    {
        if (this.world != null && !this.world.isRemote)
        {
            ExPNetwork.sendTileData(this, false);
        }
    }

    @Override
    public void update()
    {
        for (int i = 0; i < inventory.getSlots(); ++i)
        {
            ItemStack is = inventory.getStackInSlot(i);
            if (!is.isEmpty())
            {
                if (is.getItem() instanceof IContainerTickable)
                {
                    ((IContainerTickable) is.getItem()).onContainerTick(is, this.getWorld(), this.getPos(), this);
                }

                TemperatureUtils.tickItem(is, true);
            }

            if (i == 0 && is == this.inventory.getStackInSlot(i))
            {
                FluidStack fs = this.fluidInventory.getFluid();
                int missing = this.fluidInventory.getCapacity() - this.fluidInventory.getFluidAmount();
                if (Helpers.tryConsumeFluidItem(is, this.fluidInventory, stack ->
                {
                    if (this.inventory.getStackInSlot(1).isEmpty())
                    {
                        this.inventory.setStackInSlot(1, stack);
                        return true;
                    }
                    else
                    {
                        if (ItemHandlerHelper.canItemStacksStack(stack, this.inventory.getStackInSlot(1)) && this.inventory.getStackInSlot(1).getCount() + stack.getCount() <= this.inventory.getStackInSlot(1).getMaxStackSize())
                        {
                            this.inventory.getStackInSlot(1).grow(stack.getCount());
                            return true;
                        }
                    }

                    return false;
                }, stack ->
                {
                    this.inventory.getStackInSlot(0).shrink(1);
                    return true;
                }))
                {
                    this.sendUpdatePacket();
                }
                else
                {
                    if (is.getItem() instanceof ItemWoodenBucket)
                    {
                        ItemWoodenBucket woodenBucket = (ItemWoodenBucket) is.getItem();
                        Fluid f = woodenBucket.getStoredFluid(is);
                        if (fs == null || fs.getFluid() == f)
                        {
                            int amount = woodenBucket.getWater(is) * 100;
                            int added = amount > missing ? missing : amount;
                            woodenBucket.setWater(is, woodenBucket.getWater(is) - added / 100);
                            if (woodenBucket.getWater(is) == 0)
                            {
                                woodenBucket.setWaterType(is, null);
                            }

                            this.fluidInventory.fill(new FluidStack(f, added), true);
                            this.sendUpdatePacket();
                            continue;
                        }
                    }
                }

                if (this.currentRecipe == null)
                {
                    this.currentRecipe = RecipesBarrel.findRecipe(is, this.fluidInventory.getFluid());
                }

                if (this.currentRecipe != null)
                {
                    if (!this.currentRecipe.matches(this.fluidInventory.getFluid(), is))
                    {
                        this.currentRecipe = null;
                    }
                    else
                    {
                        if (++this.recipeProgress >= this.currentRecipe.getProgressRequired(is))
                        {
                            ItemStack stack = this.currentRecipe.getResult(is, this.fluidInventory.getFluid());
                            if (!this.results.isEmpty() && this.results.peek().isItemEqual(stack) && this.results.peek().getCount() + stack.getCount() <= stack.getMaxStackSize())
                            {
                                this.results.peek().grow(stack.getCount());
                            }
                            else
                            {
                                this.results.push(stack);
                            }

                            this.currentRecipe.consumeFluid(this.fluidInventory, is);
                            this.currentRecipe.consumeItem(is);
                            this.sendUpdatePacket();
                            if (!this.currentRecipe.matches(this.fluidInventory.getFluid(), is))
                            {
                                this.currentRecipe = null;
                                this.recipeProgress = 0;
                            }
                        }
                    }
                }
            }
            else
            {
                if (i == 1)
                {
                    if (is.isEmpty() && !this.results.isEmpty())
                    {
                        ItemStack stack = this.results.peek();
                        this.inventory.setStackInSlot(i, this.results.pop());
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.fluidInventory.readFromNBT(compound);
        this.recipeProgress = compound.getInteger("recipeProgress");
        this.treeType = EnumTreeType.values()[compound.getByte("type")];
        this.results.clear();
        for (NBTBase tagCompound : compound.getTagList("results", Constants.NBT.TAG_COMPOUND))
        {
            ItemStack is = new ItemStack((NBTTagCompound) tagCompound);
            this.results.push(is);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound ret = super.writeToNBT(compound);
        ret.setTag("inventory", this.inventory.serializeNBT());
        ret.setInteger("recipeProgress", this.recipeProgress);
        ret.setByte("type", (byte) this.treeType.ordinal());
        this.fluidInventory.writeToNBT(compound);
        NBTTagList tagList = new NBTTagList();
        for (ItemStack is : this.results)
        {
            NBTTagCompound tag = new NBTTagCompound();
            is.writeToNBT(tag);
            tagList.appendTag(tag);
        }

        ret.setTag("results", tagList);
        return ret;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.getPos(), 0, this.serializeNBT());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.serializeNBT();
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.deserializeNBT(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory) : capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidInventory) : super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound serializeData()
    {
        NBTTagCompound ret = new NBTTagCompound();
        this.fluidInventory.writeToNBT(ret);
        return ret;
    }

    @Override
    public void readData(NBTTagCompound tag)
    {
        this.fluidInventory.readFromNBT(tag);
    }
}

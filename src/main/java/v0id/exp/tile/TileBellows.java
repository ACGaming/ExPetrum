package v0id.exp.tile;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import v0id.api.exp.tile.ISyncableTile;
import v0id.api.exp.tile.ITemperatureHolder;
import v0id.api.exp.world.IExPWorld;
import v0id.exp.net.ExPNetwork;

public class TileBellows extends TileEntity implements ISyncableTile
{
    public long bellowsLast;

    public void sendUpdatePacket()
    {
        if (this.world != null && !this.world.isRemote)
        {
            ExPNetwork.sendTileData(this, false);
        }
    }

    public void click()
    {
        long bellowsTimer = IExPWorld.of(this.world).today().getTime() - this.bellowsLast;
        if (bellowsTimer >= 100)
        {
            this.bellowsLast = IExPWorld.of(this.world).today().getTime();
            EnumFacing rotation = this.world.getBlockState(this.pos).getValue(BlockHorizontal.FACING);
            BlockPos pos = this.pos.offset(rotation);
            this.world.playSound(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.BLOCKS, 1.0F, 0.1F);
            if (this.world.getTileEntity(pos) instanceof ITemperatureHolder)
            {
                ((ITemperatureHolder)this.world.getTileEntity(pos)).acceptBellows(rotation.getOpposite(), false);
            }

            this.sendUpdatePacket();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.bellowsLast = compound.getLong("bellowsLast");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound ret = super.writeToNBT(compound);
        ret.setLong("bellowsLast", this.bellowsLast);
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
    public boolean hasFastRenderer()
    {
        return true;
    }

    @Override
    public NBTTagCompound serializeData()
    {
        NBTTagCompound ret = new NBTTagCompound();
        ret.setLong("bellowsLast", this.bellowsLast);
        return ret;
    }

    @Override
    public void readData(NBTTagCompound tag)
    {
        this.bellowsLast = tag.getLong("bellowsLast");
    }
}

package v0id.api.exp.gravity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import v0id.api.exp.ExPApi;
import v0id.api.exp.block.ISupportBeam;

import java.lang.reflect.Constructor;

public class GravityHelper
{
	public static Constructor<?> newGravFallingBlock;
	
	public static boolean isSupported(World world, BlockPos pos)
    {
        for (int x = -4; x <= 4; ++x)
        {
            for (int z = -4; z <= 4; ++z)
            {
                for (int y = -2; y <= 0; ++y)
                {
                    BlockPos at = pos.add(x, y, z);
                    if (world.getBlockState(at).getBlock() instanceof ISupportBeam)
                    {
                        return true;
                    }
                }
            }
        }
		// If there is a full block under our block consider the block supported. 
		return world.getBlockState(pos.down()).getBlock() instanceof ISupport || world.getBlockState(pos.down()).isOpaqueCube() || world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);

	}

	public static void doFall(IBlockState fallingBlock, World w, BlockPos pos, BlockPos trigger)
	{
		Block block = fallingBlock.getBlock();
		if (block instanceof IGravitySusceptible)
		{
			IGravitySusceptible gravityManager = (IGravitySusceptible) block;
			if (!gravityManager.fall(w, fallingBlock, pos, trigger))
			{
				fall(fallingBlock, w, pos);
			}

			tryFallRecursive(w, pos);
		}
	}

	private static void tryFallRecursive(World w, BlockPos pos)
	{
		if (w.rand.nextBoolean())
		{
			BlockPos newFallPos = pos.add((w.rand.nextDouble() - w.rand.nextDouble()) * 6, (w.rand.nextDouble() - w.rand.nextDouble()) * 2, (w.rand.nextDouble() - w.rand.nextDouble()) * 6);
			IBlockState state = w.getBlockState(newFallPos);
			if (state.getBlock() instanceof IGravitySusceptible && ((IGravitySusceptible)state.getBlock()).canFall(w, state, newFallPos, pos))
			{
				doFall(state, w, newFallPos, pos);
			}
		}
	}

	private static void fall(IBlockState fallingBlock, World w, BlockPos pos)
	{
		try
		{
			if (newGravFallingBlock == null)
			{
				Class<?> clazz = Class.forName("v0id.exp.entity.EntityGravFallingBlock");
				newGravFallingBlock = clazz.getDeclaredConstructor(World.class, double.class, double.class, double.class, IBlockState.class);
			}
		
			EntityFallingBlock efb = (EntityFallingBlock) newGravFallingBlock.newInstance(w, (double)pos.getX() + 0.5d, (double)pos.getY(), (double)pos.getZ() + 0.5d, fallingBlock);
			if (!w.isRemote)
			{
				w.setBlockToAir(pos);
				w.spawnEntity(efb);
			}
		}
		catch (Exception ex)
		{
			ExPApi.apiLogger.fatal("Api could not reflect v0id.exp.entity.EntityGravFallingBlock.<init>! Is the core mod ExPetrum installed?", ex);
		}
	}
}

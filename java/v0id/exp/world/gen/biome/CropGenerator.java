package v0id.exp.world.gen.biome;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import v0id.api.exp.block.IOreHintReplaceable;
import v0id.api.exp.data.ExPBlocks;
import v0id.api.exp.tile.crop.EnumCrop;
import v0id.api.exp.tile.crop.IExPCrop;
import v0id.exp.crop.ExPCrop;
import v0id.exp.tile.TileCrop;
import v0id.exp.world.biome.ExPBiome;

public class CropGenerator extends WorldGenerator
{
	public ExPBiome at;
	
	public CropGenerator(ExPBiome b)
	{
		super();
		this.at = b;
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		IBlockState toSet = ExPBlocks.crop.getDefaultState();
		if (this.at.cropsToGenerate.isEmpty())
		{
			return false;
		}
		
		EnumCrop crop = this.at.cropsToGenerate.get(rand.nextInt(this.at.cropsToGenerate.size()));
		for (int i = 0; i < 128; ++i)
		{
			BlockPos offset = position.add(rand.nextInt(8) - rand.nextInt(8), 8, rand.nextInt(8) - rand.nextInt(8));
			while (!worldIn.isSideSolid(offset.down(), EnumFacing.UP, false) && !worldIn.isBlockNormalCube(offset, false) && offset.getY() > 0)
			{
				offset = offset.down();
			}
			
			if (!(worldIn.getBlockState(offset).getBlock().isAssociatedBlock(Blocks.GRASS)))
			{
				continue;
			}
			
			if (!(worldIn.isAirBlock(offset.up()) || worldIn.getBlockState(offset.up()).getBlock().isAssociatedBlock(Blocks.TALLGRASS) || worldIn.getBlockState(offset.up()).getBlock() instanceof IOreHintReplaceable || worldIn.getBlockState(offset.up()).getBlock().isReplaceable(worldIn, offset.up())))
			{
				continue;
			}
			
			worldIn.setBlockState(offset.up(), toSet, 2);
			
			// Something went wrong
			if (!(worldIn.getTileEntity(offset.up()) instanceof TileCrop))
			{
				worldIn.setBlockToAir(offset.up());
				continue;
			}
			
			TileCrop cropTile = (TileCrop) worldIn.getTileEntity(offset.up());
			ExPCrop cropData = (ExPCrop) IExPCrop.of(cropTile);
			cropData.stats.createDefaults(crop);
			cropData.stats.growth = worldIn.rand.nextDouble() < 0.75F ? 1 : 0.9F;
		}
		
		return true;
	}

}

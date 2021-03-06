package v0id.exp.world.biome.impl;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import v0id.api.exp.block.EnumTreeType;
import v0id.api.exp.data.ExPRegistryNames;
import v0id.exp.world.biome.ExPBiome;
import v0id.exp.world.gen.tree.TreeEntry;

public class ExPDesert extends ExPBiome
{
	public ExPDesert(BiomeProperties properties, float... biomedata)
	{
		super(properties, ExPRegistryNames.biomeDesert, biomedata);
		this.decorator.treesPerChunk = 1;
		this.decorator.deadBushPerChunk = 0;
		this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SAND.getDefaultState();
        this.treesToGenerate.add(new TreeEntry(10, EnumTreeType.ACACIA));
        this.treesToGenerate.add(new TreeEntry(10, EnumTreeType.BAOBAB));
        this.treesToGenerate.add(new TreeEntry(10, EnumTreeType.TEAK));
		this.treesToGenerate.add(new TreeEntry(1, EnumTreeType.WALNUT));
        this.treesToGenerate.add(new TreeEntry(1, EnumTreeType.AVOCADO));
	}
	
	@Override
	public void registerTypes()
	{
		BiomeDictionary.addTypes(this, Type.PLAINS);
	}
	
	public static ExPDesert create()
	{
		return new ExPDesert(new Biome.BiomeProperties("desert").setBaseHeight(0.125F).setHeightVariation(0.05F), 1.1F, 0.2F, 12F, 0F);
	}
}

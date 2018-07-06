package v0id.exp.registry;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import v0id.api.exp.data.ExPFluids;
import v0id.api.exp.data.ExPRegistryNames;

public class ExPFluidRegistry extends AbstractRegistry
{
	static
	{
		ExPFluids.saltWater = new Fluid(ExPRegistryNames.fluidSaltWater, new ResourceLocation("exp", "blocks/fluid/water_still"), new ResourceLocation("exp", "blocks/fluid/water_flow")){
			
			@Override
			public int getColor()
		    {
		        return 0x9900acff;
		    }
		};
		
		ExPFluids.freshWater = new Fluid(ExPRegistryNames.fluidFreshWater, new ResourceLocation("exp", "blocks/fluid/water_still"), new ResourceLocation("exp", "blocks/fluid/water_flow")){
			
			@Override
			public int getColor()
		    {
		        return 0x99005aff;
		    }
		};
		
		ExPFluids.lava = new Fluid(ExPRegistryNames.fluidLava, new ResourceLocation("exp", "blocks/fluid/lava_still"), new ResourceLocation("exp", "blocks/fluid/lava_flow"))
				.setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300).setUnlocalizedName(Blocks.LAVA.getUnlocalizedName());
		
		ExPFluids.oil = new Fluid(ExPRegistryNames.fluidOil, new ResourceLocation("exp", "blocks/fluid/oil_still"), new ResourceLocation("exp", "blocks/fluid/oil_flow"))
				.setDensity(900).setViscosity(8000);

		ExPFluids.clay = new Fluid(ExPRegistryNames.fluidClay, new ResourceLocation("exp", "blocks/fluid/clay"), new ResourceLocation("exp", "blocks/fluid/clay"))
            .setDensity(1746).setViscosity(20000);

		ExPFluids.milk = new Fluid(ExPRegistryNames.fluidMilk, new ResourceLocation("exp", "blocks/fluid/milk"), new ResourceLocation("exp", "blocks/fluid/milk"))
				.setDensity(1000).setViscosity(3000);

		ExPFluids.tannin = new Fluid(ExPRegistryNames.fluidTannin, new ResourceLocation("exp", "blocks/fluid/tannin"), new ResourceLocation("exp", "blocks/fluid/tannin"))
				.setDensity(1000).setViscosity(1000);

		ExPFluids.oliveOil = new Fluid(ExPRegistryNames.fluidOliveOil, new ResourceLocation("exp", "blocks/fluid/nut_oil"), new ResourceLocation("exp", "blocks/fluid/nut_oil"))
				.setDensity(1000).setViscosity(1000);

		ExPFluids.walnutOil = new Fluid(ExPRegistryNames.fluidWalnutOil, new ResourceLocation("exp", "blocks/fluid/nut_oil"), new ResourceLocation("exp", "blocks/fluid/nut_oil"))
				.setDensity(1000).setViscosity(1000);

        ExPFluids.juice = new Fluid(ExPRegistryNames.fluidJuice, new ResourceLocation("exp", "blocks/fluid/juice"), new ResourceLocation("exp", "blocks/fluid/juice"))
                .setDensity(1000).setViscosity(1000);
		
	}
	
	public ExPFluidRegistry()
	{
		super();
	}

	@Override
	public void preInit(FMLPreInitializationEvent evt)
	{
		super.preInit(evt);
		FluidRegistry.registerFluid(ExPFluids.saltWater);
		FluidRegistry.registerFluid(ExPFluids.freshWater);
		FluidRegistry.registerFluid(ExPFluids.lava);
		FluidRegistry.registerFluid(ExPFluids.oil);
		FluidRegistry.registerFluid(ExPFluids.clay);
		FluidRegistry.registerFluid(ExPFluids.milk);
		FluidRegistry.registerFluid(ExPFluids.tannin);
		FluidRegistry.registerFluid(ExPFluids.oliveOil);
		FluidRegistry.registerFluid(ExPFluids.walnutOil);
		FluidRegistry.registerFluid(ExPFluids.juice);
	}
}

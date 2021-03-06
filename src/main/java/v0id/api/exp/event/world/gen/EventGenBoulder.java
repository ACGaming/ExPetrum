package v0id.api.exp.event.world.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Fired when ExPetrum tries to generate boulders in the world. <br>
 * This event is fired at MinecraftForge.TERRAIN_GEN_BUS. <br>
 * This event is cancelable.
 * @author V0idWa1k3r
 *
 */
@SuppressWarnings("CanBeFinal")
@Cancelable
public class EventGenBoulder extends Event
{
	/**
	 * World the boulders are generated in
	 */
	public final World world;
	
	/**
	 * Position in the world the boulders are generating at
	 */
	public final BlockPos pos;
	
	/**
	 * Generic random
	 */
	public final Random rand;
	
	/**
	 * Boulder generator. Please do not set to null. 
	 * Can be changed to another WorldGenerator implementation.
	 * If changed the provided implementation will be used when generating boulders.
	 */
	@Nonnull
	public WorldGenerator generator;
	
	public EventGenBoulder(World w, BlockPos pos, Random rand, @Nonnull WorldGenerator generator)
	{
		this.world = w;
		this.pos = pos;
		this.rand = rand;
		this.generator = generator;
	}
}

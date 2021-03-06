package v0id.api.exp.block;

import net.minecraft.util.IStringSerializable;

public enum EnumTreeType implements IStringSerializable
{
	KALOPANAX,
	BIRCH,
	ACACIA,
	CHESTNUT,
	OAK,
	HICKORY,
	BAOBAB,
	KAPOK,
	EUCALYPTUS,
	ASH,
	WILLOW,
	MAPLE,
	REDWOOD,
	FIR(true),
	CEDAR(true),
	SPRUCE(true),
	PINE(true),
	ELM,
	PALM,
	TEAK,
	ASPEN,
	ROWAN,
	MANGROVE,
	TUPELO,
	PARROTIA,
	SWEETGUM,
	JACKWOOD,
	TSUGA(true),
	VIBURNUM,
	KOELREUTERIA,
    APPLE,
    OLIVE,
    PEACH,
    ORANGE,
    PEAR,
    PLUM,
    BANANA,
    LEMON,
    APRICOT,
    WALNUT,
    CHERRY,
    POMEGRANATE,
    GRAPEFRUIT,
    AVOCADO,
    CARAMBOLA;

    public static final EnumTreeType[][] typesForIndex = new EnumTreeType[9][5];

	static
	{
        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                EnumTreeType.typesForIndex[i][j] = EnumTreeType.values()[i * 5 + j];
            }
        }
	}
	
	EnumTreeType(boolean b)
	{
		this.evergreen = b;
	}
	
	EnumTreeType()
	{
		this(false);
	}

	private final boolean evergreen;

	public boolean bearsFruit()
    {
        return this.associatedFruit != null;
    }

    public EnumFruit getAssociatedFruit()
    {
        return associatedFruit;
    }

    public void setAssociatedFruit(EnumFruit associatedFruit)
    {
        this.associatedFruit = associatedFruit;
    }

    private EnumFruit associatedFruit = null;
	
	@Override
	public String getName()
	{
		return this.name().toLowerCase();
	}

	public boolean isEvergreen()
	{
		return this.evergreen;
	}
	
}

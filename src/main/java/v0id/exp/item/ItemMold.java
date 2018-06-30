package v0id.exp.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import v0id.api.exp.data.ExPCreativeTabs;
import v0id.api.exp.data.ExPItems;
import v0id.api.exp.data.ExPRegistryNames;
import v0id.api.exp.inventory.IWeightProvider;
import v0id.api.exp.item.IMold;
import v0id.api.exp.metal.EnumMetal;
import v0id.api.exp.metal.EnumToolClass;
import v0id.api.exp.metal.EnumToolStats;
import v0id.exp.util.temperature.TemperatureUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ItemMold extends Item implements IInitializableItem, IWeightProvider, IMold
{
    public enum EnumMoldType
    {
        CLAY,
        CERAMIC,
        FULL
    }

    private boolean isIngot;

    public ItemMold(boolean isIngot)
    {
        super();
        this.isIngot = isIngot;
        this.initItem();
    }

    @Override
    public float provideWeight(ItemStack item)
    {
        return 0.05F;
    }

    @Override
    public Pair<Byte, Byte> provideVolume(ItemStack item)
    {
        return Pair.of((byte)2, (byte)1);
    }

    @Override
    public void initItem()
    {
        this.setHasSubtypes(true);
        this.setCreativeTab(ExPCreativeTabs.tabMiscItems);
        this.setRegistryName(this.isIngot ? ExPRegistryNames.itemMoldIngot : ExPRegistryNames.itemMoldTool);
        this.setUnlocalizedName(this.getRegistryName().toString().replace(':', '.'));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (tab != this.getCreativeTab())
        {
            return;
        }

        if (this.isIngot)
        {
            for (int i = 0; i < 2 + EnumMetal.values().length; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
        else
        {
            for (int i = 0; i < EnumToolClass.values().length * EnumMoldType.values().length; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (this.isIngot)
        {
            return super.getUnlocalizedName(stack) + "." + (stack.getMetadata() == 0 ? "clay" : stack.getMetadata() == 1 ? "ceramic" : "full");
        }

        return super.getUnlocalizedName(stack) + "." + EnumMoldType.values()[stack.getMetadata() / EnumToolClass.values().length].name().toLowerCase() + "." + EnumToolClass.values()[stack.getMetadata() % EnumToolClass.values().length].name().toLowerCase();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (this.isIngot && stack.getMetadata() > 1)
        {
            tooltip.add((TemperatureUtils.getTemperature(stack) >= EnumMetal.values()[stack.getMetadata() - 2].getMeltingTemperature() * 0.75F ? I18n.format("exp.txt.liquid") + " " : "") + I18n.format("exp.item.ingot.desc.type." + EnumMetal.values()[stack.getMetadata() - 2].name().toLowerCase()));
        }

        if (!this.isIngot && stack.getMetadata() >= EnumToolClass.values().length * 2)
        {
            if (TemperatureUtils.getTemperature(stack) >= EnumMetal.COPPER.getMeltingTemperature() * 0.75F)
            {
                tooltip.add(I18n.format("exp.txt.liquid"));
            }
        }
    }

    @Override
    public void tryFill(ItemStack self, BiConsumer<EnumMetal, Float> reductor, Consumer<ItemStack> resultSetter, EnumMetal metal, float value)
    {
        if (value >= 100)
        {
            if (this.isIngot)
            {
                if (self.getMetadata() == 1)
                {
                    reductor.accept(metal, 100F);
                    resultSetter.accept(new ItemStack(this, 1, 2 + metal.ordinal()));
                }
            }
            else
            {
                if (metal == EnumMetal.COPPER && self.getMetadata() >= EnumToolClass.values().length && self.getMetadata() < EnumToolClass.values().length * 2)
                {
                    reductor.accept(metal, 100F);
                    resultSetter.accept(new ItemStack(this, 1, self.getMetadata() + EnumToolClass.values().length));
                }
            }
        }
    }

    @Override
    public boolean isMold(ItemStack self)
    {
        return this.isIngot ? self.getMetadata() == 1 : self.getMetadata() >= EnumToolClass.values().length && self.getMetadata() < EnumToolClass.values().length * 2;
    }

    @Override
    public boolean hasMetal(ItemStack self)
    {
        if (this.isIngot)
        {
            return self.getMetadata() > 1;
        }
        else
        {
            return self.getMetadata() >= EnumToolClass.values().length * 2;
        }
    }

    @Override
    public boolean isLiquid(ItemStack self)
    {
        if (this.isIngot)
        {
            return TemperatureUtils.getTemperature(self) >= EnumMetal.values()[self.getMetadata() - 2].getMeltingTemperature() * 0.75F;
        }
        else
        {
            return TemperatureUtils.getTemperature(self) >= EnumMetal.COPPER.getMeltingTemperature() * 0.75F;
        }
    }

    @Override
    public ItemStack getResult(ItemStack self)
    {
        if (this.isIngot)
        {
            ItemStack ret = new ItemStack(ExPItems.ingot, 1, self.getMetadata() - 2);
            TemperatureUtils.setTemperature(ret, TemperatureUtils.getTemperature(self));
            return ret;
        }
        else
        {
            ItemStack ret = ItemToolHead.createToolHead(EnumToolClass.values()[self.getMetadata() % EnumToolClass.values().length], EnumToolStats.COPPER);
            TemperatureUtils.setTemperature(ret, TemperatureUtils.getTemperature(self));
            return ret;
        }
    }
}

package de.achtii.wandering_illager.item;

import de.achtii.wandering_illager.wandering_illager;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            // Add items to list when forge loads them
            DeferredRegister.create(ForgeRegistries.ITEMS, wandering_illager.MODID);

    public static final RegistryObject<Item> FADED_GEM = ITEMS.register("faded_gem",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WANDERING_GEM = ITEMS.register("wandering_gem",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}

package de.achtii.wandering_illager.entity;

import de.achtii.wandering_illager.entity.custom.WanderingIllagerEntity;
import de.achtii.wandering_illager.wandering_illager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, wandering_illager.MODID);

    //Registers new Entity
    public static final RegistryObject<EntityType<WanderingIllagerEntity>> WANDERINGILLAGER =
            ENTITY_TYPES.register("wandering_illager", () -> EntityType.Builder.of(WanderingIllagerEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 1.5f).build("wandering_illager"));

    //Register Method
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

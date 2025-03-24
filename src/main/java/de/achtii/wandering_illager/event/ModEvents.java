package de.achtii.wandering_illager.event;

import de.achtii.wandering_illager.item.ModItems;
import de.achtii.wandering_illager.wandering_illager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;


@Mod.EventBusSubscriber(modid = wandering_illager.MODID)
public class ModEvents {

    //Adds new Trades to the Wandering Trader
    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

            rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 64),
                    new ItemStack(ModItems.WANDERING_GEM.get(), 1),
                    2, 12, 0.15f));
    }
}

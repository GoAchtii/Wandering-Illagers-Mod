package de.achtii.wandering_illager.event;

import com.mojang.datafixers.optics.Wander;
import de.achtii.wandering_illager.entity.ModEntities;
import de.achtii.wandering_illager.entity.custom.WanderingIllagerEntity;
import de.achtii.wandering_illager.item.ModItems;
import de.achtii.wandering_illager.wandering_illager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

        VillagerTrades.ItemListing customTrade = (pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 64),
                new ItemStack(ModItems.WANDERING_GEM.get(), 1),
                2, 12, 0.15f);

        rareTrades.add(customTrade);


    }

    @SubscribeEvent
    public static void onTraderHit(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof WanderingTrader trader) {

            boolean hasGemTrade = trader.getOffers().stream()
                    .anyMatch(offer -> offer.getResult().is(ModItems.WANDERING_GEM.get()));

            if (hasGemTrade) {

                double traderX = trader.getX();
                double traderY = trader.getY();
                double traderZ = trader.getZ();
                trader.discard();
                spawn(trader.level(), traderX, traderY, traderZ);
            }
        }
    }

    public static void spawn(Level level, double x, double y, double z) {
        WanderingIllagerEntity WanderingIllager = new WanderingIllagerEntity(ModEntities.WANDERINGILLAGER.get(), (ServerLevel) level);
        WanderingIllager.setPos(x,y,z);
        level.addFreshEntity(WanderingIllager);

    }
}

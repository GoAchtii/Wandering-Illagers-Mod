package de.achtii.wandering_illager.event;

import de.achtii.wandering_illager.entity.ModEntities;
import de.achtii.wandering_illager.entity.custom.WanderingIllagerEntity;
import de.achtii.wandering_illager.item.ModItems;
import de.achtii.wandering_illager.wandering_illager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;


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

    @SubscribeEvent
    public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        Entity target = event.getTarget();
        RandomSource random = level.random;

        if (!(target instanceof WanderingIllagerEntity illager)) return;

        if (!itemStack.is(ModItems.WANDERING_GEM.get())) return;

        if (!level.isClientSide) {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            ItemStack dummyItem;

            int randomLevel = Mth.nextInt(random, 1, 5);

            if (randomLevel == 1) {
                dummyItem = new ItemStack(Items.IRON_SWORD);
            } else if (randomLevel == 2) {
                dummyItem = new ItemStack(Items.IRON_PICKAXE);
            } else if (randomLevel == 3) {
                dummyItem = new ItemStack(Items.IRON_CHESTPLATE);
            } else if (randomLevel == 4) {
                dummyItem = new ItemStack(Items.IRON_HELMET);
            } else if (randomLevel == 5){
                dummyItem = new ItemStack(Items.IRON_BOOTS);
            } else {
                dummyItem = new ItemStack(Items.IRON_SHOVEL);
            }

            List<EnchantmentInstance> enchantments = EnchantmentHelper.selectEnchantment(random, dummyItem, 16, true);

            for (EnchantmentInstance enchantment : enchantments) {
                EnchantedBookItem.addEnchantment(enchantedBook, enchantment);
            }

            if (enchantments.isEmpty()) {
                EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(Enchantments.SHARPNESS, 2));
            }

            ItemEntity bookDrop = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), enchantedBook);
            level.addFreshEntity(bookDrop);

            level.addFreshEntity(new ExperienceOrb(level, illager.getX(), illager.getY(), illager.getZ(), 200));

            illager.discard();
            spawnVillager(level, illager.getX(), illager.getY(), illager.getZ());

            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        }

        event.setCanceled(true);
    }

    public static void spawn(Level level, double x, double y, double z) {
        WanderingIllagerEntity WanderingIllager = new WanderingIllagerEntity(ModEntities.WANDERINGILLAGER.get(), (ServerLevel) level);
        WanderingIllager.setPos(x,y,z);
        WanderingIllager.populateDefaultEquipmentSlots();
        level.addFreshEntity(WanderingIllager);

        ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, x, y + 1, z, 50, 0.3, 0.5, 0.3, 0.02);
        ((ServerLevel) level).sendParticles(ParticleTypes.ENCHANT, x, y + 1, z, 40, 0.3, 0.6, 0.3, 0.1);

        level.playSound(
                null,
                x, y, z,
                SoundEvents.FIRE_EXTINGUISH,
                SoundSource.NEUTRAL,
                1.0f,
                1.2f
        );
    }

    private static void addCustomTrade(WanderingTrader trader) {
        MerchantOffers offers = trader.getOffers();

        ItemStack cost = new ItemStack(Items.EMERALD, 64);
        ItemStack result = new ItemStack(ModItems.WANDERING_GEM.get());

        MerchantOffer offer = new MerchantOffer(cost, result, 5, 10, 0.05F);
        offers.add(offer);
    }

    public static void spawnVillager(Level level, double x, double y, double z) {
        ServerLevel serverLevel = (ServerLevel) level;
        WanderingTrader trader = EntityType.WANDERING_TRADER.create(serverLevel);
        if (trader == null) return;
        trader.setPos(x,y,z);
        trader.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(trader.blockPosition()), null, null, null);
        addCustomTrade(trader);
        level.addFreshEntity(trader);

        ((ServerLevel) level).sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y + 1, z, 50, 0.3, 0.5, 0.3, 0.02);
        ((ServerLevel) level).sendParticles(ParticleTypes.CRIMSON_SPORE, x, y + 1, z, 40, 0.3, 0.6, 0.3, 0.1);

        level.playSound(
                null,
                x, y, z,
                SoundEvents.ENDER_CHEST_OPEN,
                SoundSource.NEUTRAL,
                1.0f,
                1.2f
        );
    }

    @SubscribeEvent
    public static void DistanceCheck(MobSpawnEvent event) {
        Level level = event.getLevel().getLevel();
        LivingEntity entity = event.getEntity();

        if (entity instanceof WanderingTrader trader) {
            boolean hasGemTrade = trader.getOffers().stream()
                    .anyMatch(offer -> offer.getResult().is(ModItems.WANDERING_GEM.get()));

            List<Player> players = level.getEntitiesOfClass(Player.class, trader.getBoundingBox().inflate(2));
            for (Player player : players) {
                if (hasGemTrade) {
                    spawn(level, trader.getX(), trader.getY(), trader.getZ());
                    trader.discard();
                    level.playSound(
                            null,
                            trader.getX(), trader.getY(), trader.getZ(),
                            SoundEvents.VINDICATOR_CELEBRATE,
                            SoundSource.NEUTRAL,
                            1.0f,
                            1.2f);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void giveItem(MobSpawnEvent event){
        Mob entity = event.getEntity();
        if (entity instanceof WanderingIllagerEntity wanderingIllager) {
            if (!wanderingIllager.getPersistentData().getBoolean("HasWeapon")) {
                wanderingIllager.populateDefaultEquipmentSlots();
                if (wanderingIllager.getMainHandItem().equals(new ItemStack(Items.IRON_AXE))) {
                    Objects.requireNonNull(wanderingIllager.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(6.0);
                } else if (wanderingIllager.getMainHandItem().equals(new ItemStack(Items.DIAMOND_AXE))){
                    Objects.requireNonNull(wanderingIllager.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(12.0);
                }
                wanderingIllager.getPersistentData().putBoolean("HasWeapon", true);
            }
        }

    }
}




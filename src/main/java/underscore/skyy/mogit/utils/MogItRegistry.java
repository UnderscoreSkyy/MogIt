package underscore.skyy.mogit.utils;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import underscore.skyy.mogit.common.MogItContent;

import static underscore.skyy.mogit.MogIt.MOD_ID;

public final class MogItRegistry {

    /**
     * Setup function to register all necessary object for the mod
     */
    public static void setup() {
        registerBlocks();
        registerBlocksEntities();
        registerItems();
    }

    /**
     * Register all blocks of the mod
     */
    private static void registerBlocks() {
        // no-op
    }

    /**
     * Register all blockentities of the mod
     */
    private static void registerBlocksEntities() {
        register(MogItContent.BlockEntities.TRANSMOGRIFICATION_TABLE, MogItContent.Blocks.TRANSMOGRIFICATION_TABLE, "transmogrification_table", MogItContent.Items.TRANSMOGRIFICATION_TABLE);
    }

    /**
     * Register all items of the mod
     */
    private static void registerItems() {
        register(MogItContent.Items.LIVING_MATTER, "living_matter");
    }

    /**
     * Register an Item
     * @param item The item
     * @param name The name of the item
     */
    private static void register(Item item, String name) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
    }

    /**
     * Register a Block and optionnaly the item that is linked with it
     * @param block The block
     * @param name The name of the block
     * @param blockItem The item that is linked to the block
     */
    private static void register(Block block, String name, @Nullable BlockItem blockItem) {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
        if(blockItem != null) { register(blockItem, name); }
    }

    private static void register(BlockEntityType blockEntity, Block block, String name, @Nullable BlockItem blockItem) {
        register(block, name, blockItem);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), blockEntity);
    }

}

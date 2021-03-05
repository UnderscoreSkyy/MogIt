package underscore.skyy.mogit.utils;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import underscore.skyy.mogit.client.gui.TransmogrificationTableScreen;
import underscore.skyy.mogit.common.MogItContent;
import underscore.skyy.mogit.common.screens.TransmogrificationTableScreenHandler;

import static underscore.skyy.mogit.MogIt.MOD_ID;

public final class MogItRegistry {

    private MogItRegistry() {
        //no-op
    }

    /**
     * Setup function to register all necessary object for the mod
     */
    public static void setupServer() {
        Server.registerBlocks();
        Server.registerBlocksEntities();
        Server.registerItems();
        Server.registerScreenHandlers();
    }

    /**
     * Setup function for client part of the mod
     */
    public static void setupClient() {
        Client.registerScreens();
    }

    private static class Server {
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
            registerBlockEntity(MogItContent.BlockEntitiesTypes.TRANSMOGRIFICATION_TABLE, MogItContent.Blocks.TRANSMOGRIFICATION_TABLE, "transmogrification_table", MogItContent.Items.TRANSMOGRIFICATION_TABLE);
        }

        /**
         * Register all items of the mod
         */
        private static void registerItems() {
            registerItem(MogItContent.Items.LIVING_MATTER, "living_matter");
        }

        private static void registerScreenHandlers() {
            MogItContent.ScreenHandlerTypes.TRANSMOGRIFICATION_TABLE = registerScreenHandler(TransmogrificationTableScreenHandler::new, "transmogrification_table");
        }
    }

    private static class Client {

        private static void registerScreens() {
            registerScreen(MogItContent.ScreenHandlerTypes.TRANSMOGRIFICATION_TABLE, TransmogrificationTableScreen::new);
        }

    }

    /**
     * Register an Item
     * @param item The item
     * @param name The name of the item
     */
    private static void registerItem(Item item, String name) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
    }

    /**
     * Register a Block and optionnaly the item that is linked with it
     * @param block The block
     * @param name The name of the block
     * @param blockItem The item that is linked to the block
     */
    private static void registerBlock(Block block, String name, @Nullable BlockItem blockItem) {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
        if(blockItem != null) { registerItem(blockItem, name); }
    }

    /**
     * Register a block entity, the linked block and optionnaly the item liked with it
     * @param blockEntity The block entity
     * @param block the block
     * @param name the name of the block
     * @param blockItem the item to access the block
     */
    private static void registerBlockEntity(BlockEntityType<?> blockEntity, Block block, String name, @Nullable BlockItem blockItem) {
        registerBlock(block, name, blockItem);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), blockEntity);
    }

    /**
     * Register screenHandler
     * @param screen
     * @param name
     * @return
     */
    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(ScreenHandlerRegistry.SimpleClientHandlerFactory<T> screen, String name){
        return ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, name), screen);
    }

    /**
     * Register a screen and linking it to it's screen handler only use by client side
     * @param screenHandlerType The type of screen
     * @param screen the screen
     */
    private static <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> void registerScreen(ScreenHandlerType<? extends H> screenHandlerType, ScreenRegistry.Factory<H, S> screen) {
        ScreenRegistry.register(screenHandlerType, screen);
    }

}

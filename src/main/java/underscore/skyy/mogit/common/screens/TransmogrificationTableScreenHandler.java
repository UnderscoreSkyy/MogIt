package underscore.skyy.mogit.common.screens;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import underscore.skyy.mogit.common.MogItContent;

import static net.minecraft.screen.PlayerScreenHandler.*;

public class TransmogrificationTableScreenHandler extends ScreenHandler {

    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER;
    private static final Identifier[] EMPTY_ARMOR_SLOT_TEXTURES;
    private final Inventory inventory;
    private final PlayerInventory playerInventory;

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public TransmogrificationTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public TransmogrificationTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(MogItContent.ScreenHandlerTypes.TRANSMOGRIFICATION_TABLE, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        this.playerInventory = playerInventory;

        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        addGearSlots();
        addPlayerInventory();
        addPlayerHotbar();

        setupTransmogrificationTableSlot();

    }

    private void addGearSlots() {
        // Head, chest, legs, feet
        for(int n = 0; n < 4; ++n) {
            final EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[n];

            this.addSlot(new Slot(playerInventory, 39 - n, 5, 37 + n * 22) {
                @Override
                public int getMaxItemCount() {
                    return 1;
                }

                @Override
                public boolean canInsert(ItemStack stack) {
                    return equipmentSlot == MobEntity.getPreferredEquipmentSlot(stack);
                }

                @Override
                public boolean canTakeItems(PlayerEntity playerEntity) {
                    ItemStack itemStack = this.getStack();
                    return (itemStack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.canTakeItems(playerEntity);
                }

                @Override
                @Environment(EnvType.CLIENT)
                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
                }
            });
        }

        // Main Hand
        this.addSlot(new Slot(playerInventory, playerInventory.selectedSlot, 30, 142));

        // Off Hand
        this.addSlot(new Slot(playerInventory, 40, 58, 142) {
            @Environment(EnvType.CLIENT)
            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        });
    }

    private void addPlayerInventory() {
        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 108 + j * 18, 84 + k * 18));
            }
        }
    }

    private void addPlayerHotbar() {
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 108 + k * 18, 142));
        }
    }

    private void setupTransmogrificationTableSlot() {
        this.addSlot(new Slot(this.inventory, 0, 117, 24) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == MogItContent.Items.LIVING_MATTER;
            }
        });

        this.addSlot(new Slot(this.inventory, 1, 117, 49) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });

        this.addSlot(new Slot(this.inventory, 2, 150, 51) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem()instanceof DyeItem;
            }
        });

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    static {
        EMPTY_ARMOR_SLOT_TEXTURES = new Identifier[]{EMPTY_BOOTS_SLOT_TEXTURE, EMPTY_LEGGINGS_SLOT_TEXTURE, EMPTY_CHESTPLATE_SLOT_TEXTURE, EMPTY_HELMET_SLOT_TEXTURE};
        EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }
}

package underscore.skyy.mogit.common.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import underscore.skyy.mogit.common.MogItContent;

public class TransmogrificationTableBlockEntity extends BlockEntity {

    private int number = 7;

    public TransmogrificationTableBlockEntity() {
        super(MogItContent.BlockEntities.TRANSMOGRIFICATION_TABLE);
    }

    // Serialize the BlockEntity
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        tag.putInt("number", number);
        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        number = tag.getInt("number");
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

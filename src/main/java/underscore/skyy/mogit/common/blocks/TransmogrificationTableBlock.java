package underscore.skyy.mogit.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import underscore.skyy.mogit.common.blockentities.TransmogrificationTableBlockEntity;

public class TransmogrificationTableBlock extends Block implements BlockEntityProvider {

    public TransmogrificationTableBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TransmogrificationTableBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            TransmogrificationTableBlockEntity entity = (TransmogrificationTableBlockEntity) world.getBlockEntity(pos);
            if(entity != null) {
                player.sendMessage(new LiteralText(String.valueOf(entity.getNumber())), false);
            }
        }

        return ActionResult.SUCCESS;
    }
}

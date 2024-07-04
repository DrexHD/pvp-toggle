package pvptoggle.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pvptoggle.PvpWhitelist;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @WrapOperation(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;isTeammate(Lnet/minecraft/entity/Entity;)Z"
        )
    )
    public boolean checkwhitelist(PlayerEntity instance, Entity entity, Operation<Boolean> original) {
        if (entity instanceof PlayerEntity player) {
            // if either player is not on pvp whitelist, return true
            if(!PvpWhitelist.contains(instance.getGameProfile()) || !PvpWhitelist.contains(player.getGameProfile())) {
                return true;
            }
        }
        return original.call(instance, entity);
    }

}

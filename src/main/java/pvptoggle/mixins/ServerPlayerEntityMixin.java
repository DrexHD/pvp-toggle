package pvptoggle.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pvptoggle.PvpWhitelist;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "shouldDamagePlayer", at = @At("HEAD"), cancellable = true)
    public void checkwhitelist(PlayerEntity targetPlayer, CallbackInfoReturnable<Boolean> cir) {
        // if either player is not on pvp whitelist, return false
        if(!PvpWhitelist.contains(this.getGameProfile()) || !PvpWhitelist.contains(targetPlayer.getGameProfile())) {
            cir.setReturnValue(false);
        }
    }
}

package tfar.explodingmobs.mixin;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ModelLayers.class)
public interface ModelLayersAccess {

    @Accessor
    static Set<ModelLayerLocation> getALL_MODELS() {
        throw new RuntimeException("oops");
    }

}

package tfar.explodingmobs;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;

public class CowPowerLayer extends UniversalSwirlLayer<Cow, CowModel<Cow>> {

    private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CowModel<Cow> model;

    public CowPowerLayer(RenderLayerParent<Cow, CowModel<Cow>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new CowModel<>(entityModelSet.bakeLayer(ModelLayers2.COW_POWER));
    }

    @Override
    protected float xOffset(float f) {
        return 0;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    @Override
    protected EntityModel<Cow> model() {
        return model;
    }
}

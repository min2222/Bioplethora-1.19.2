package io.github.bioplethora.client.armor.render;

import io.github.bioplethora.client.armor.model.AquChestplateModel;
import io.github.bioplethora.item.armor.AquChestplateItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AquChestplateRender extends GeoArmorRenderer<AquChestplateItem> {

    public AquChestplateRender() {
        super(new AquChestplateModel());
    }
    
    @Override
    public GeoBone getHeadBone() {
    	return this.model.getBone("dummybone").orElse(null);
    }
    
    @Override
    public GeoBone getBodyBone() {
    	return this.model.getBone("body").orElse(null);
    }
    
    @Override
    public GeoBone getRightArmBone() {
    	return this.model.getBone("right_arm").orElse(null);
    }
    
    @Override
    public GeoBone getLeftArmBone() {
    	return this.model.getBone("left_arm").orElse(null);
    }
    
    @Override
    public GeoBone getRightLegBone() {
    	return this.model.getBone("dummybone").orElse(null);
    }
    
    @Override
    public GeoBone getLeftLegBone() {
    	return this.model.getBone("dummybone").orElse(null);
    }
    
    @Override
    public GeoBone getRightBootBone() {
    	return this.model.getBone("dummybone").orElse(null);
    }
    
    @Override
    public GeoBone getLeftBootBone() {
    	return this.model.getBone("dummybone").orElse(null);
    }
}

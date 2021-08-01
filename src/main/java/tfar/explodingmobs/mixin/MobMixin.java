package tfar.explodingmobs.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.explodingmobs.MobDuck;
import tfar.explodingmobs.UniversalSwellGoal;

@Mixin(Mob.class)
abstract class MobMixin extends LivingEntity implements MobDuck{

	private static final EntityDataAccessor<Integer> $DATA_SWELL_DIR = SynchedEntityData.defineId(Mob.class, EntityDataSerializers.INT);

	private int $swell;
	private final int $maxSwell = 30;
	private int $oldSwell;
	private final int $explosionRadius = 3;

	@Shadow @Final protected GoalSelector goalSelector;

	@Shadow public abstract InteractionResult interact(Player player, InteractionHand interactionHand);

	@Shadow @Final protected GoalSelector targetSelector;

	protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	private void init(EntityType<? extends Mob> entityType, Level level, CallbackInfo ci) {
		if (level != null && !level.isClientSide && notCreeper()) {
			this.goalSelector.addGoal(0, new UniversalSwellGoal((Mob)(Object)this));
			if ((Object)this instanceof PathfinderMob) {
				this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>((PathfinderMob)(Object)this, Player.class, true));
				this.targetSelector.addGoal(2, new HurtByTargetGoal((PathfinderMob)(Object)this));
				this.goalSelector.addGoal(4, new MeleeAttackGoal((PathfinderMob)(Object)this, 1.0D, false));
			}

		}
	}

	@Inject(method = "defineSynchedData",at = @At("RETURN"))
	private void extraData(CallbackInfo ci) {
		if (notCreeper())
		this.entityData.define($DATA_SWELL_DIR,-1);
	}

	@Inject(method = "tick",at = @At("HEAD"))
	private void extraTick(CallbackInfo ci) {
		if (notCreeper() && isAlive()) {
			$oldSwell = $swell;
			int i = this.$getSwellDir();
			if (i > 0 && this.$swell == 0) {
				this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
				this.gameEvent(GameEvent.PRIME_FUSE);
			}

			this.$swell += i;
			if (this.$swell < 0) {
				this.$swell = 0;
			}

			if (this.$swell >= this.$maxSwell) {
				this.$swell = this.$maxSwell;
				this.explode();
			}
		}
	}

	private void explode() {
		if (!this.level.isClientSide) {
			Explosion.BlockInteraction blockInteraction = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
			float f = 2;//this.isPowered() ? 2.0F : 1.0F;
			this.dead = true;
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), this.$explosionRadius * f, blockInteraction);
			this.discard();
			//this.spawnLingeringCloud();
		}

	}

	public int $getSwellDir() {
		return this.entityData.get($DATA_SWELL_DIR);
	}

	public void $setSwellDir(int i) {
		this.entityData.set($DATA_SWELL_DIR, i);
	}

	public float $getSwelling(float f) {
		return Mth.lerp(f, (float)this.$oldSwell, (float)this.$swell) / (float)(this.$maxSwell - 2);
	}

	public boolean notCreeper() {
		return !((Object)this instanceof Creeper);
	}

}

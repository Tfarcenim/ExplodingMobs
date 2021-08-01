package tfar.explodingmobs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class UniversalSwellGoal extends Goal {
    private final Mob creeper;
    private LivingEntity target;

    public UniversalSwellGoal(Mob creeper) {
        this.creeper = creeper;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        LivingEntity livingEntity = this.creeper.getTarget();
        return getSwellDir() > 0 || livingEntity != null && this.creeper.distanceToSqr(livingEntity) < 9.0D;
    }

    public void start() {
        this.creeper.getNavigation().stop();
        this.target = this.creeper.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            setSwellDir(-1);
        } else if (this.creeper.distanceToSqr(this.target) > 49.0D) {
            setSwellDir(-1);
        } else if (!this.creeper.getSensing().hasLineOfSight(this.target)) {
            setSwellDir(-1);
        } else {
            setSwellDir(1);
        }
    }

    private void setSwellDir(int dir) {
        ((MobDuck)creeper).$setSwellDir(dir);
    }

    private int getSwellDir() {
        return ((MobDuck)creeper).$getSwellDir();
    }
}

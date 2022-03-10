import processing.core.PImage;

import java.util.List;

public abstract class PlantEntity extends ActiveEntity {
    private int health;
    private int healthLimit;
    private static final String STUMP_KEY = "stump";


    public PlantEntity(String id,
                      Point position, int actionPeriod, int animationPeriod, int health, int healthLimit, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
        this.health = health;
        this.healthLimit = healthLimit;

    }

    public boolean transform(WorldModel world,
                             EventScheduler scheduler,
                             ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Factory.createStump(super.getId(),
                    super.getPosition(),
                    imageStore.getImageList(STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

    public int getHealth() {
        return this.health;
    }

    public int getHealthLimit(){return this.healthLimit;}

    public void editHealth(int health) {
        this.health = this.health + health;
    }
}
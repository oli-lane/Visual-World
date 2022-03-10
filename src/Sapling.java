import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Sapling extends PlantEntity {
    private static final int TREE_ANIMATION_MAX = 600;
    private static final int TREE_ANIMATION_MIN = 50;
    private static final int TREE_ACTION_MAX = 1400;
    private static final int TREE_ACTION_MIN = 1000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;

    public Sapling(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
                   int health, int healthLimit) {
        super(id, position, actionPeriod, animationPeriod, health, healthLimit, images);
    }



    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler) {
        super.editHealth(1);
        if (!this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    super.getActionPeriod());
        }
    }

    private static int getNumFromRange(int max, int min)
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }

    @Override
    public boolean transform(WorldModel world,
                             EventScheduler scheduler,
                             ImageStore imageStore) {
        boolean result = super.transform(world, scheduler, imageStore);
        if (super.getHealth() >= super.getHealthLimit())
        {
            Tree tree = Factory.createTree("tree_" + super.getId(),
                    super.getPosition(),
                    getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
                    getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleAction(scheduler, world, imageStore);

            return true;
        }
        return result;
    }
}

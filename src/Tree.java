import processing.core.PImage;

import java.util.List;

public class Tree extends PlantEntity{

    public Tree(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
                int health, int healthLimit) {
        super(id, position, actionPeriod, animationPeriod, health, healthLimit, images);
    }

    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler) {
        if (!this.transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    super.getActionPeriod());
        }
    }

}

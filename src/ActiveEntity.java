import processing.core.PImage;

import java.util.List;

public abstract class ActiveEntity extends AnimateEntity {
    private int actionPeriod;

    public ActiveEntity(String id,
                         Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, animationPeriod, images);
        this.actionPeriod = actionPeriod;

    }

    abstract void executeActivity(WorldModel world,
                         ImageStore imageStore,
                         EventScheduler scheduler);

    @Override
    public void scheduleAction(EventScheduler scheduler,
                               WorldModel world,
                               ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
        super.scheduleAction(scheduler, world, imageStore);
    }

    public int getActionPeriod() {return actionPeriod;}

}

import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Dude_Not_Full extends DudeEntity {
    private int resourceCount;

    public Dude_Not_Full (String id,
                          Point position,
                          int actionPeriod,
                          int animationPeriod,
                          int resourceLimit,
                          List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, resourceLimit, images);
        this.resourceCount = 0;

    }

    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !this.moveTo(world,
                target.get(),
                scheduler)
                || !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    super.getActionPeriod());
        }
    }

    public boolean transformNotFull(
            //Entity entity,
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (this.resourceCount >= super.getResourceLimit()) {
            Dude_Full miner = Factory.createDudeFull(super.getId(),
                    super.getPosition(), super.getActionPeriod(),
                    super.getAnimationPeriod(),
                    super.getResourceLimit(),
                    super.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleAction(scheduler, world, imageStore);

            return true;
        }

        return false;
    }




    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (adjacent(super.getPosition(), target.getPosition())) {
            this.resourceCount += 1;
            ((PlantEntity)target).editHealth(-1);
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!super.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

}

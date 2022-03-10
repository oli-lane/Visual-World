import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Fairy extends MoveEntity {

    public Fairy(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, actionPeriod, animationPeriod, images);
    }


    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler) {
        Optional<Entity> fairyTarget =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveTo(world, fairyTarget.get(), scheduler)) {
                PlantEntity sapling = Factory.createSapling("sapling_" + super.getId(), tgtPos,
                        imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleAction(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                super.getActionPeriod());
    }


    protected boolean _nextPositionHelper(Point newPos, WorldModel world) {
        return true;
    }


    @Override
    public boolean moveTo(
            //Entity fairy,
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (adjacent(super.getPosition(), target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
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

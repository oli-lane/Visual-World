import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class BlackPawn extends Pawn {

    public BlackPawn(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
    }

    @Override
    boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        Point destination = new Point(this.getPosition().x, 30);
        if (adjacent(super.getPosition(), destination)) {
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, destination);

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

    @Override
    void transformQueen(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        BlackQueen miner = Factory.createBlackQueen(super.getId(),
                super.getPosition(), super.getActionPeriod(),
                super.getAnimationPeriod(),
                super.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleAction(scheduler, world, imageStore);
    }

}

import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class Queen extends MoveEntity {

    private Point destination;
    Random rand = new Random();

    public Queen(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
        destination = new Point(rand.nextInt(39), rand.nextInt(29));
    }

    @Override
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                super.getActionPeriod());
    }

    @Override
    protected boolean _nextPositionHelper(Point newPos, WorldModel world) {
        return true;
    }

    @Override
    boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (adjacent(super.getPosition(), target.getPosition())) {
            destination = new Point(rand.nextInt(39), rand.nextInt(29));
        }

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

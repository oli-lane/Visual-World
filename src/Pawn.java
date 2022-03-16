import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Pawn extends MoveEntity {

    public Pawn(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
    }

    @Override
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (this.moveTo(world,
                null, scheduler))
        {
            System.out.println("yes");
            this.transformQueen(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    super.getActionPeriod());
        }
    }

    abstract void transformQueen(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    @Override
    protected boolean _nextPositionHelper(Point newPos, WorldModel world) {
        return true;
    }

}

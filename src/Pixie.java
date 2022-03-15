import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Pixie extends MoveEntity {

    public Pixie(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
    }

    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Queen.class)));

        if (target.isPresent()) {
            Point tgtPos = target.get().getPosition();
            String color;
            if (target.get() instanceof WhiteQueen){
                color = "White";
            }
            else{
                color = "Black";
            }
            if (this.moveTo(world, target.get(), scheduler)) {
                //this portion adds white/black pawn based off of target
                if (color.equals("White")){
                    //create black pawn
                    BlackPawn bpawn = Factory.createBlackPawn("blackPawn", tgtPos,
                            3, 5, imageStore.getImageList("blackPawn"));
                    world.addEntity(bpawn);
                    bpawn.scheduleAction(scheduler, world, imageStore);
                }
                else{
                    // create white pawn
                    WhitePawn wpawn = Factory.createWhitePawn("whitePawn", tgtPos, 3, 5,
                            imageStore.getImageList("whitePawn"));
                    world.addEntity(wpawn);
                    wpawn.scheduleAction(scheduler, world, imageStore);
                }
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
            //Entity pixie,
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

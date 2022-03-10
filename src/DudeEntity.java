import processing.core.PImage;

import java.util.List;

public abstract class DudeEntity extends MoveEntity{

    private int resourceLimit;
    public DudeEntity(String id,
                      Point position, int actionPeriod, int animationPeriod, int resourceLimit, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
        this.resourceLimit = resourceLimit;

    }

    protected boolean _nextPositionHelper(Point newPos, WorldModel world) {
        return world.getOccupancyCell(newPos).getClass() != Stump.class;
    }

    public int getResourceLimit() {return resourceLimit;}
}

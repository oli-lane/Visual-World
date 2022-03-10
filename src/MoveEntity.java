import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class MoveEntity extends ActiveEntity {

    public MoveEntity(String id,
                        Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);

    }
    public Point nextPosition(
            WorldModel world, Point destPos)
    {
        PathingStrategy strat = new AStarPathingStrategy();


        Point start = super.getPosition();
        Point end = destPos;
        Predicate<Point> canPassThrough = p -> !(world.isOccupied(p) && _nextPositionHelper(p, world)) && world.withinBounds(p);
        BiPredicate<Point, Point> withinReach = MoveEntity::adjacent;
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        List<Point> path = strat.computePath(start, end, canPassThrough, withinReach, potentialNeighbors);

        if (path.isEmpty()) { //right check for no path? Or if path.get(0) is null etc
            return start;
        }

        return path.get(0);

//        int horiz = Integer.signum(destPos.x - super.getPosition().x);
//        Point newPos = new Point(super.getPosition().x + horiz, super.getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && _nextPositionHelper(newPos, world)) {
//            int vert = Integer.signum(destPos.y - super.getPosition().y);
//            newPos = new Point(super.getPosition().x, super.getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) &&  _nextPositionHelper(newPos, world)) {
//                newPos = super.getPosition();
//            }
//        }
//
//        return newPos;
    }

    protected abstract boolean _nextPositionHelper(Point newPos, WorldModel world);

    abstract boolean moveTo(WorldModel world,
                   Entity target,
                   EventScheduler scheduler);


    public static boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y
                && Math.abs(p1.x - p2.x) == 1);
    }

}
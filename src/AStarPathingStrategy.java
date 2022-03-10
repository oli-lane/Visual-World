import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();

        PriorityQueue<AStarNode> openList = new PriorityQueue<>(); // nodes we've checked
        HashMap<Point, AStarNode> openHash = new HashMap<>();

        HashMap<Point, AStarNode> closedList = new HashMap<>();

        AStarNode current = new AStarNode(start, end, null);

        openList.add(current);
        openHash.put(current.getLocation(), current);

       while (!openList.isEmpty() && !(withinReach.test(current.getLocation(), end))) { // while we aren't right next to goal (don't want to be on goal)
            current = openList.remove(); // sets current to node with lowest fval
           openHash.remove(current.getLocation());
           List<Point> neighbors =
                   potentialNeighbors.apply(current.getLocation()).filter(canPassThrough).filter(loc -> !closedList.containsKey(loc)).collect(Collectors.toList());

           for (Point n: neighbors) {
               if (!(closedList.containsKey(n))) {
                   AStarNode newNode = new AStarNode(n, end, current);

                   if (openHash.containsKey(n)) {
                       AStarNode existing = openHash.get(n);
                       if (newNode.getGval() < existing.getGval()) {
                            // update openList
                           openList.remove(existing);
                           openList.add(newNode);
                           // update openHash
                           openHash.replace(n, newNode);
                       }
                   } else {
                       openList.add(newNode);
                       openHash.put(n, newNode);
                   }


               }
           }
           closedList.put(current.getLocation(), current); // move current to closed list so it's not checked again
       }

       while (current.getPrev() != null) {
           path.add(0, current.getLocation()); // create path start -> end
           //path.add(current.getLocation()); // create path end -> start
           current = current.getPrev();
       }

        return path;
    }
}

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class AStarNode implements Comparable<AStarNode> {
    private Point location;
    private int fval; // would it be better to make these all doubles
    private int hval;
    private int gval;
    private AStarNode prev;

    public AStarNode(Point location, Point dest, AStarNode prev) {
        this.prev = prev;
        this.location = location;
        this.hval = (int) Math.sqrt(Math.pow((location.x - dest.x), 2) + Math.pow((location.y - dest.y), 2));  //Math.abs(location.x - dest.x) + Math.abs(location.y - dest.y);
        this.gval = ((prev == null) ? -1 : this.prev.gval) + 1;
        this.fval = gval + hval;
    }

    public Point getLocation() {
        return location;
    }

    public int getGval() {
        return gval;
    }

    public AStarNode getPrev() {
        return prev;
    }

//    public void betterGval(int gval) {
//        this.gval = gval;
//        this.fval = hval + gval; // updates the fval
//    }

    @Override
    public int hashCode() {
        int hash = 1;

        hash = hash * 31 + ((Integer)fval).hashCode();
        hash = hash * 31 + ((Integer)hval).hashCode();
        hash = hash * 31 + ((Integer)gval).hashCode();
        hash = hash * 31 + ((prev == null) ? 0 : prev.hashCode()); // prev node not null
        hash = hash * 31 + location.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        AStarNode a = (AStarNode)o;
        return Objects.equals(this.prev, a.prev) && Objects.equals(this.location, a.location) &&
                this.fval == a.fval && this.hval == a.hval && this.gval == a.gval;
    }


    @Override
    public int compareTo(AStarNode other) {
        return fval - other.fval;
    }
}

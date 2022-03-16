import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class WhiteQueen extends Queen {
    public WhiteQueen(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
    }

}

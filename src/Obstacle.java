import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimateEntity {

    public Obstacle(String id, Point position, int animationPeriod, List<PImage> images) {
        super(id, position, animationPeriod, images);
    }

}

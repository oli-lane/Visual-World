import processing.core.PImage;

import java.util.List;

public abstract class AnimateEntity extends Entity {
    private int animationPeriod;

    public AnimateEntity(String id,
                 Point position, int animationPeriod, List<PImage> images) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;

    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public void nextImage() {
        super.setImageIndex((super.getImageIndex() + 1) % super.getImages().size());
        //this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void scheduleAction(EventScheduler scheduler,
                               WorldModel world,
                               ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0),
                getAnimationPeriod());
    }
}

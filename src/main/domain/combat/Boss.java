package main.domain.combat;

// A node's boss enemy. A Boss is an Enemy with stronger stats/thresholds and
// boss-specific behavior (e.g. reward or dialogue overrides), so it can be
// used anywhere an Enemy is expected.
public class Boss extends Enemy {
    public Boss(String id, String name, int strengthThreshold, int magicThreshold){
        super(id,name,strengthThreshold,magicThreshold);
    }
}

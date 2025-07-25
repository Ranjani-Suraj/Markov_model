package dyn_connectivity;

import java.util.Random;
import java.util.Map;

public class ConnVertex {
    int id;
    private final int hash;

    public ConnVertex(int id) {
        this.id = id;
        Random random = new Random();
        this.hash = random.nextInt(1000000);
    }
    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}

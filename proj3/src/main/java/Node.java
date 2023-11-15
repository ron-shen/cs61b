import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Objects;

public class Node {
    protected long id;
    protected double lat;
    protected double lon;
    protected AbstractList<Long> neighbours;

    public Node(long id, double lon, double lat) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        neighbours = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lon);
    }
}

public class Position3D {
    public double x, y, z;

    public Position3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void rotateAroundZ(double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);
        this.x = newX;
        this.y = newY;
    }

    public void rotateAroundY(double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double newX = x * cosA + z * sinA;
        double newZ = -x * sinA + z * cosA;
        this.x = newX;
        this.z = newZ;
    }
}
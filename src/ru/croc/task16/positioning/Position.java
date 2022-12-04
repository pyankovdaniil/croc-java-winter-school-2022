package ru.croc.task16.positioning;

public class Position {
    private double latitude;
    private double longitude;

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position(Position position) {
        this(position.latitude, position.longitude);
    }

    public double getDistance(Position position) {
        double longitudeDiff = longitude - position.longitude;
        double val1 = Math.pow(Math.sin((latitude - position.latitude) / 2.0), 2);
        double val2 = Math.cos(latitude) * Math.cos(position.latitude) *
                Math.pow(Math.sin((longitudeDiff) / 2.0), 2);
        return 2.0 * Math.asin(Math.sqrt(val1 + val2));
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

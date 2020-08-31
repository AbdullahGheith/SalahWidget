package abdulg.widget.salahny.Model.Places;


import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class Coordinates {

    private String latitude;
    private String longitude;
    private Integer accuracy;

    public Coordinates() {
    }

    @Ignore
    public Coordinates(String latitude, String longitude) {
        try {
            Double.valueOf(latitude);
            Double.valueOf(longitude);
            this.latitude = latitude;
            this.longitude = longitude;
        } catch (Exception e) {
            throw new IllegalArgumentException("Coordinates must be parseable to double!");
        }

    }

    @Ignore
    public Coordinates(Double latitude, Double longitude) {
        this.latitude = latitude.toString();
        this.longitude = longitude.toString();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        try {
            Double.valueOf(latitude);
            this.latitude = latitude;
        } catch (Exception e) {
            throw new IllegalArgumentException("Coordinates must be parseable to double!");
        }
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        try {
            Double.valueOf(longitude);
            this.longitude = longitude;
        } catch (Exception e) {
            throw new IllegalArgumentException("Coordinates must be parseable to double!");
        }
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    @NonNull
    @Override
    public String toString() {
        return latitude + ", "+longitude+": "+accuracy;
    }
}

package abdulg.widget.salahny.Model.Places;

import androidx.room.Embedded;

/**
 * Created by Abdullah on 06/02/2018.
 */

public class Mosque {
	private String mosqueName;
  private String mosqueLatitude;
  private String mosqueLongitude;
  private String mosqueAddress;
  private String mosqueZipCode;

	public String getMosqueName() {
		return mosqueName;
	}

	public void setMosqueName(String mosqueName) {
		this.mosqueName = mosqueName;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.mosqueLatitude = coordinates.getLatitude();
		this.mosqueLongitude = coordinates.getLongitude();
	}

	public String getMosqueLatitude() {
		return mosqueLatitude;
	}

	public void setMosqueLatitude(String mosqueLatitude) {
		this.mosqueLatitude = mosqueLatitude;
	}

	public String getMosqueLongitude() {
		return mosqueLongitude;
	}

	public void setMosqueLongitude(String mosqueLongitude) {
		this.mosqueLongitude = mosqueLongitude;
	}

	public String getMosqueAddress() {
		return mosqueAddress;
	}

	public void setMosqueAddress(String mosqueAddress) {
		this.mosqueAddress = mosqueAddress;
	}

	public String getMosqueZipCode() {
		return mosqueZipCode;
	}

	public void setMosqueZipCode(String mosqueZipCode) {
		this.mosqueZipCode = mosqueZipCode;
	}
}

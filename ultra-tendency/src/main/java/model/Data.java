package model;

import java.util.UUID;

public class Data {
	private UUID deviceId;
	private Location location;
	private Integer temperature;
	private Long time;
	

	public static class Location {
		private Double longitude;
		private Double latitude;

		public Double getLongitude() {
			return longitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}

		public Double getLatitude() {
			return latitude;
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
			result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Location other = (Location) obj;
			if (latitude == null) {
				if (other.latitude != null)
					return false;
			} else if (!latitude.equals(other.latitude))
				return false;
			if (longitude == null) {
				if (other.longitude != null)
					return false;
			} else if (!longitude.equals(other.longitude))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Location [longitude=" + longitude + ", latitude=" + latitude + "]";
		}

	}


	public UUID getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(UUID deviceId) {
		this.deviceId = deviceId;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Data [deviceId=" + deviceId + ", location=" + location + ", time=" + time + "]";
	}


	public Integer getTemperature() {
		return temperature;
	}


	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

}

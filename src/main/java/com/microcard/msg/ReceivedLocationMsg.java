/**
 * 
 */
package com.microcard.msg;

/**
 * @author jack
 *
 */
@MsgTypeAnnotation(msg=MsgType.event,event=EventType.LOCATION)
public class ReceivedLocationMsg extends ReceivedEventMsg {

	//Latitude 地理位置纬度
	@MsgFieldAnnotation("Latitude")
	private String latitude;
	
	//Longitude 地理位置经度
	@MsgFieldAnnotation("Longitude")
	private String longitude;
	
	//Precision 地理位置精度
	@MsgFieldAnnotation("Precision")
	private String precision;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}
	
	
}

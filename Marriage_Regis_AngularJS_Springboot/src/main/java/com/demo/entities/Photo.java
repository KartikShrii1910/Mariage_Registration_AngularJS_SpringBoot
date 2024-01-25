package com.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Photo {

	@Id
	private int id;

	private String panCardName;
	
	private String panCardPath;
	
	private String adharCardName;
	
	private String adharCardPath;
	
	private String tenMarksheetName;
	
	private String tenMarksheetPath;
	
	private String twelveMarksheetName;
	
	private String twelveMarksheetPath;
	
	private String imageName;
	
	private String imagePath;

	public Photo() {
		super();
	}

	public Photo(String panCardName, String panCardPath, String adharCardName, String adharCardPath,
			String tenMarksheetName, String tenMarksheetPath, String twelveMarksheetName, String twelveMarksheetPath,
			String imageName, String imagePath) {
		super();
		this.panCardName = panCardName;
		this.panCardPath = panCardPath;
		this.adharCardName = adharCardName;
		this.adharCardPath = adharCardPath;
		this.tenMarksheetName = tenMarksheetName;
		this.tenMarksheetPath = tenMarksheetPath;
		this.twelveMarksheetName = twelveMarksheetName;
		this.twelveMarksheetPath = twelveMarksheetPath;
		this.imageName = imageName;
		this.imagePath = imagePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPanCardName() {
		return panCardName;
	}

	public void setPanCardName(String panCardName) {
		this.panCardName = panCardName;
	}

	public String getPanCardPath() {
		return panCardPath;
	}

	public void setPanCardPath(String panCardPath) {
		this.panCardPath = panCardPath;
	}

	public String getAdharCardName() {
		return adharCardName;
	}

	public void setAdharCardName(String adharCardName) {
		this.adharCardName = adharCardName;
	}

	public String getAdharCardPath() {
		return adharCardPath;
	}

	public void setAdharCardPath(String adharCardPath) {
		this.adharCardPath = adharCardPath;
	}

	public String getTenMarksheetName() {
		return tenMarksheetName;
	}

	public void setTenMarksheetName(String tenMarksheetName) {
		this.tenMarksheetName = tenMarksheetName;
	}

	public String getTenMarksheetPath() {
		return tenMarksheetPath;
	}

	public void setTenMarksheetPath(String tenMarksheetPath) {
		this.tenMarksheetPath = tenMarksheetPath;
	}

	public String getTwelveMarksheetName() {
		return twelveMarksheetName;
	}

	public void setTwelveMarksheetName(String twelveMarksheetName) {
		this.twelveMarksheetName = twelveMarksheetName;
	}

	public String getTwelveMarksheetPath() {
		return twelveMarksheetPath;
	}

	public void setTwelveMarksheetPath(String twelveMarksheetPath) {
		this.twelveMarksheetPath = twelveMarksheetPath;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", panCardName=" + panCardName + ", panCardPath=" + panCardPath + ", adharCardName="
				+ adharCardName + ", adharCardPath=" + adharCardPath + ", tenMarksheetName=" + tenMarksheetName
				+ ", tenMarksheetPath=" + tenMarksheetPath + ", twelveMarksheetName=" + twelveMarksheetName
				+ ", twelveMarksheetPath=" + twelveMarksheetPath + ", imageName=" + imageName + ", imagePath="
				+ imagePath + "]";
	}

}

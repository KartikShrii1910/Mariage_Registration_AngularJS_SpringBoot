package com.demo.services;

import java.io.IOException;

import com.demo.entities.Photo;

public interface PhotoService {
	
	public Photo saveImage(Photo file) throws IOException;

}

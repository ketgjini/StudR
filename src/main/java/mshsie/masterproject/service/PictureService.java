package mshsie.masterproject.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import mshsie.masterproject.model.Picture;
import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.PictureRepository;

@Service
public class PictureService {
	
	@Autowired
	PictureRepository pictureRepository;
	
	public Picture savePictures(MultipartFile picture, User user) {
		Picture pic = new Picture();
		String picName = StringUtils.cleanPath(picture.getOriginalFilename());
		deletePicture(user.getId());
		pic.setPictureName(picName);
		try {
			pic.setPicture(picture.getBytes());
			pic.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pictureRepository.save(pic);
	}
	
	public Picture findPictureById(Long id) {
		Picture pic = pictureRepository.findPictureById(id);
		if(pic != null)
			return pic;
		else return new Picture();
	}
	
	public void deletePicture(Long id) {
		pictureRepository.deletePicture(id);
	}

}

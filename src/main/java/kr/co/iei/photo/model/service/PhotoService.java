package kr.co.iei.photo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.photo.model.dao.PhotoDao;

@Service
public class PhotoService {
	@Autowired
	private PhotoDao photoDao;
}

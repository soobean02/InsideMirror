package kr.co.iei.photo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.photo.model.dao.PhotoDao;
import kr.co.iei.photo.model.dto.Photo;

@Service
public class PhotoService {
	@Autowired
	private PhotoDao photoDao;

	@Transactional
	public int insertPhoto(Photo p) {
		int result = photoDao.insertPhoto(p);
		return result;
	}

	public int getTotalCount() {
		int totalCount = photoDao.getTotalCount();
		return totalCount;
	}

	public List selectPhotoList(int start, int amount) {
		int end = start + amount - 1;
		List photoList = photoDao.selectPhotoList(start, end);
		return photoList;
	}
}

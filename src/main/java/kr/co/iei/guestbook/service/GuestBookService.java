package kr.co.iei.guestbook.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.guestbook.dao.GuestBookDao;
import kr.co.iei.guestbook.dto.GuestBook;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookDao guestBookDao;

	@Transactional
	public int insertComment(GuestBook gb) {
		int result = guestBookDao.insertComment(gb);
		return result;
	}
	
	@Transactional
	public int updateComment(GuestBook gb) {
		int result = guestBookDao.updateComment(gb);
		return result;
	}
	@Transactional
	public int deleteComment(GuestBook gb) {
		int result = guestBookDao.deleteComment(gb);
		return result;
	}
	@Transactional
	public List<GuestBook> getAllComments() {
		return guestBookDao.getAllComments();
    }

}
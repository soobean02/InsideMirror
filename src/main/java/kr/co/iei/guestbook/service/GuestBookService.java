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
		return guestBookDao.updateComment(gb);
		
	}
	@Transactional
	public int deleteComment(GuestBook gb) {
		return guestBookDao.deleteComment(gb);
	}
	public List<GuestBook> getAllComments(GuestBook gb) {
		return guestBookDao.getAllComments(gb);
    }

	public GuestBook getCommentByNo(Integer guestCommentNo) {
		 return guestBookDao.getCommentByNo(guestCommentNo);
		
	}


	
	
	

}
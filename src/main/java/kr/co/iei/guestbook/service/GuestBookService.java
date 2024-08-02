package kr.co.iei.guestbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.guestbook.dao.GuestBookDao;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookDao guestBookDao;
}

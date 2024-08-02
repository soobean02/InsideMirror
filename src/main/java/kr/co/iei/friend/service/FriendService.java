package kr.co.iei.friend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.friend.dao.FriendDao;

@Service
public class FriendService {
	@Autowired
	private FriendDao friendDao;
}

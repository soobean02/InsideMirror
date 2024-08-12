package kr.co.iei.friend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.friend.dao.FriendDao;
import kr.co.iei.friend.dto.Friend;


@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;

	public List<Friend> selectAllList(Friend f) {
		List<Friend> list = friendDao.selectAllList(f);
		return list;
	}
	
	@Transactional
	public int friendCancel(Friend f) {
		return friendDao.friendCancel(f);
		
	}

   
}

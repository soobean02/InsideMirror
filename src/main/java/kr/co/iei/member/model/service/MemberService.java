package kr.co.iei.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberListData;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	
	public MemberListData selectAllMember(int reqPage) {
		int numPerPage = 10;
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		
		List member = memberDao.selectAllMember(start, end);
		
		int totalCount = memberDao.selectAllMemberTotalCount();
		
		int totalPage = 0;
		if(totalCount % totalPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage + 1;
		}//else
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage - 1)/pageNaviSize) * pageNaviSize + 1;
		
		String pageNavi = "<ul class='page-wrap'>";
		
		if(pageNo != 1) {
			pageNavi += "<li><a class='page-index' href='/admin/allMember?reqPage="+(pageNo - 1)+"'><span> < </span></a></li>";
		}
		
		for(int i = 0; i < pageNaviSize; i++){
			pageNavi += "<li>";
			if(pageNo == reqPage){
				pageNavi += "<a class='page-index active-page' href='/admin/allMember?reqPage="+pageNo+"'>";
			}
			else{
				pageNavi += "<a class='page-index' href='/admin/allMember?reqPage="+pageNo+"'>";
			}//else
			
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			
			if(pageNo > totalPage) break;
		}//for
		
		if(pageNo <= totalPage){
			pageNavi += "<li>";
			pageNavi += "<a class='page-index' href='/admin/allMember?reqPage="+pageNo+"'>";
			pageNavi += "<span> > </span>";
			pageNavi += "</a></li>";
		}//if

		pageNavi += "</ul>";
		
		MemberListData mld = new MemberListData(member, pageNavi);
		
		return mld;
	}//selectAllMember


	public Member selectOneMember(String memberId) {
		return memberDao.selectOneMember(memberId);
	}//selectOneMember
}

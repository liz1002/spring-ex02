package com.khrd.domain;

public class PageMaker {
	private int totalCount; //전체 게시물 개수
	private int startPage; //현재 보이는 페이지 시작번호
	private int endPage; //현재 보이는 페이지 끝번호
	private boolean prev; //이전 10개 존재 여부
	private boolean next; //이후 10개 존재 여부
	
	private int displayPageNum = 5; //보이는 페이지 숫자의 개수(보일 게시글 개수)
	
	private int lastPage; //마지막 페이지 번호
	
	private Criteria cri;

	private void calcDate() {
		//끝, 시작 페이지  번호
		endPage = (int) (Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		
		//현재 페이지 번호
		int tempEndPage = (int) Math.ceil(totalCount /(double) cri.getPerPageNum());
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		//페이지여부
		if(startPage == 1) {
			prev = false;
		}else {
			prev = true;
		}
		
		//이전페이지, 다음페이지 여부
		if(endPage * cri.getPerPageNum() >= totalCount) {
			next = false;
		}else {
			next = true;
		}
		
		//마지막 페이지 번호
		lastPage = (int) Math.ceil((double)totalCount/(double)displayPageNum);
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcDate(); //page Maker 값 구함
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}
}

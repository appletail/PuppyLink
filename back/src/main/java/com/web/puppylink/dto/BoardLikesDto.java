package com.web.puppylink.dto;

import com.web.puppylink.model.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikesDto {
    private int     boardNo;
    private String  subject;
    private String  contents;
    private String  pictureURL;
    private String  likes;
    private String  regDate;
    private Member  email;
    private String isLikes;
    
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Member getEmail() {
		return email;
	}
	public void setEmail(Member email) {
		this.email = email;
	}
	public String isLikes() {
		return isLikes;
	}
	public void setIsLikes(String isLikes) {
		this.isLikes = isLikes;
	}
	
	@Override
	public String toString() {
		return "BoardLikesDto [boardNo=" + boardNo + ", subject=" + subject + ", contents=" + contents + ", pictureURL="
				+ pictureURL + ", likes=" + likes + ", regDate=" + regDate + ", email=" + email + ", isLikes=" + isLikes
				+ "]";
	}
}

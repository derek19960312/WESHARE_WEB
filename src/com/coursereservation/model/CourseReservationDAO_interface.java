package com.coursereservation.model;

import java.util.List;

import com.inscoursetime.model.InsCourseTimeVO;
import com.member.model.MemberVO;
import com.withdrawalrecord.model.WithdrawalRecordVO;

public interface CourseReservationDAO_interface {
	public void insert(CourseReservationVO courseReservationVO);

	public void update(CourseReservationVO courseReservationVO);
	
	public void updateClassStatus(String crvId);

	public List<CourseReservationVO> findByStatus(Integer xxxStatus);

	public List<CourseReservationVO> findByRate(String inscId);

	public List<CourseReservationVO> findByPrimaryKey(String xxxId);

	public List<CourseReservationVO> getAll();

	// 交易是簡單的
	public void insertWithMemberWithRecod(CourseReservationVO courseReservationVO, MemberVO memberVO,
			WithdrawalRecordVO withdrawalRecordVO, InsCourseTimeVO insCourseTimeVO);

}

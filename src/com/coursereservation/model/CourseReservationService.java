package com.coursereservation.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;



public class CourseReservationService {
	
	private CourseReservationDAO_interface dao;

	public CourseReservationService() {
		dao = new CourseReservationDAO();

	}
	
	
	public CourseReservationVO addCourseReservation(String crvId, Date crvDate, String teacherId, String memId, String inscId, String teamId,
			Integer crvStatus, Integer classStatus, Integer tranStatus, Timestamp crvMFD, Timestamp crvEXP,
			String crvLoc, Integer crvTotalTime, Integer crvTotalPrice, Double crvScore, String crvRate) {
		
		CourseReservationVO courseReservationVO = new CourseReservationVO();
		
		courseReservationVO.setCrvId(crvId);
		courseReservationVO.setTeacherId(teacherId);
		courseReservationVO.setMemId(memId);
		courseReservationVO.setInscId(inscId);
		courseReservationVO.setTeamId(teamId);
		courseReservationVO.setCrvStatus(crvStatus);
		courseReservationVO.setClassStatus(classStatus);
		courseReservationVO.setTranStatus(tranStatus);
		courseReservationVO.setCrvMFD(crvMFD);
		courseReservationVO.setCrvEXP(crvEXP);
		courseReservationVO.setCrvLoc(crvLoc);
		courseReservationVO.setCrvTotalTime(crvTotalTime);
		courseReservationVO.setCrvTotalPrice(crvTotalPrice);
		courseReservationVO.setCrvScore(crvScore);
		
		dao.insert(courseReservationVO);
		return courseReservationVO;

	}
	public CourseReservationVO update(String teamId,Integer crvStatus,Integer classStatus,Integer transStatus,Double crvScore,String crvRate,String crvId) {
		CourseReservationVO courseReservationVO = new CourseReservationVO();
		
		courseReservationVO.setCrvId(crvId);
		courseReservationVO.setTeamId(teamId);
		courseReservationVO.setCrvStatus(crvStatus);
		courseReservationVO.setClassStatus(classStatus);
		courseReservationVO.setCrvScore(crvScore);
		
		dao.update(courseReservationVO);
		return courseReservationVO;
		
	}
	public List<CourseReservationVO> findByStatus(Integer xxxStatus){
		return dao.findByStatus(xxxStatus);
		
	}
	public List<CourseReservationVO> findByPrimaryKey(String xxxId){
		return dao.findByPrimaryKey(xxxId);
		
	}
	public List<CourseReservationVO> getAll(){
		return dao.getAll();
		
	}

}

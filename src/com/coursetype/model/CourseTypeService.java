package com.coursetype.model;

import java.util.List;

public class CourseTypeService {
	private CourseTypeDAO_interface dao;
	
	
	
	public CourseTypeService() {
		dao = new CourseTypeJDBCDAO();
	}
	
	public void addCourseType(CourseTypeVO courseTypeVO) {
		
	}
	public void updateCourseType(CourseTypeVO courseTypeVO) {
		
	}
	public void deleteCourseType(Integer courseTypeId) {
		
	}

	public List<CourseTypeVO> getAll(){
		return dao.getAll();
	}

}

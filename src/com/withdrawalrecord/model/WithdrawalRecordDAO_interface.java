package com.withdrawalrecord.model;

import java.util.*;

public interface WithdrawalRecordDAO_interface {

	public void insert(WithdrawalRecordVO withdrawalRecordVO);

	public void update(WithdrawalRecordVO withdrawalRecordVO);
//	       public void delete(String withdrawalRecordVO);

	public List<WithdrawalRecordVO> findByKey(String xxxId);

	public List<WithdrawalRecordVO> getAll();

	public WithdrawalRecordVO findByPrimaryKey(String withdrawalRecordVO);

//	       public Set<WithdrawalRecordVO> getEmpsByDeptno(Integer deptno);

}

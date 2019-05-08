package com.coursereservation.model;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisConfirmShake {
	

	public void setNewConfirm(String crvId, String requestIP) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.set(crvId, requestIP);
		jedis.close();
	}

	public String checkIfWait(String crvId, String requestIP ) {
		// 對雙方來說，都要各存著歷史聊天記錄
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		if(requestIP.equals(jedis.get(crvId))) {
			jedis.close();
			return "hadCome";
		}else if(requestIP.isEmpty()){
			jedis.close();
			return "noData";
		}else {
			jedis.close();
			return "success";
		}
		
		
		
		
	}
	
}

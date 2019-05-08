package com.coursereservation.model;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisConfirmShake {
	

	public void setNewConfirm(String crvId) {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.set(crvId, "exsist");
		jedis.close();
	}

	public boolean checkIfWait(String crvId) {
		// 對雙方來說，都要各存著歷史聊天記錄
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		if("exsist".equals(jedis.get(crvId))) {
			jedis.close();
			return true;
		}else {
			jedis.close();
			return false;
		}
		
		
		
		
	}
	
}

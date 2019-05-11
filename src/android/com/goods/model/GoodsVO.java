package android.com.goods.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import android.com.teacher.model.TeacherVO;
	@Entity
	@Table(name = "GOODS")
	public class GoodsVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String goodId;
	private TeacherVO teacherVO;
	private String goodName;
	private Integer goodPrice;
	private String goodInfo;
	private byte[] goodImg;
	private Integer goodStatus;
	
	
	@Id
	@GenericGenerator(name = "name2", strategy = "generator.GoodsGenerator")
	@GeneratedValue(generator="name2")    
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	
	
	@ManyToOne()
	@JoinColumn(name = "TEACHERID") 
	public TeacherVO getTeacherId() {
		return teacherVO;
	}
	public void setTeacherId(TeacherVO teacherVO) {
		this.teacherVO = teacherVO;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public Integer getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(Integer goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getGoodInfo() {
		return goodInfo;
	}
	public void setGoodInfo(String goodInfo) {
		this.goodInfo = goodInfo;
	}
	public byte[] getGoodImg() {
		return goodImg;
	}
	public void setGoodImg(byte[] goodImg) {
		this.goodImg = goodImg;
	}
	public Integer getGoodStatus() {
		return goodStatus;
	}
	public void setGoodStatus(Integer goodStatus) {
		this.goodStatus = goodStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GoodsVO goodsVO = (GoodsVO) o;
		return Objects.equals(goodId, goodsVO.goodId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(goodId);
	}
}

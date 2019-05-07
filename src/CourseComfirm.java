

public class CourseComfirm {
	private String type;
	private String sender;
	private String receiver;
	private String check;

	public CourseComfirm(String type,String sender ,String receiver, String check) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.check = check;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

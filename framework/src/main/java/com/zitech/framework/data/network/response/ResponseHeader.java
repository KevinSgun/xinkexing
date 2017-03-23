package com.zitech.framework.data.network.response;

/**
 * 响应头
 * 
 * @author Ludaiqian
 * 
 */
public class ResponseHeader {
	/**
	"sign": "aad41232060e6b96256215081739211f",
			"status": "1",
			"datecode": "godo001",
			"msg": "发送短信成功"
	 **/

	// 操作成功或者失败的代码,1代表成功，负数代表失败
	private int status;
	// 操作失败的提示
	private String sign;
	// 接口业务代码
	private String datacode;
	//
	private String msg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDatecode() {
		return datacode;
	}

	public void setDatecode(String datecode) {
		this.datacode = datecode;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

package org.pojo;

/**
 * 统计结果
 */
public class AppResult
{
    private String              fid;
    private long                request;
    private long                push;
    private long                view;
    private long                click;
    private long              download;
    private long              install;
    
    ///~ getter and setter

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public long getRequest() {
		return request;
	}

	public void setRequest(long request) {
		this.request = request;
	}

	
	public long getPush() {
		return push;
	}

	public void setPush(long push) {
		this.push = push;
	}

	public long getView() {
		return view;
	}

	public void setView(long view) {
		this.view = view;
	}

	public long getClick() {
		return click;
	}

	public void setClick(long click) {
		this.click = click;
	}

	public long getDownload() {
		return download;
	}

	public void setDownload(long download) {
		this.download = download;
	}

	public long getInstall() {
		return install;
	}

	public void setInstall(long install) {
		this.install = install;
	}
	
}

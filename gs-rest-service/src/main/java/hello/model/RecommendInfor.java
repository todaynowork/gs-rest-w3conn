package hello.model;

public class RecommendInfor {

	private String title;
	private String totalResult;
	private String id;
	private String contributorsUrl;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContributorsUrl() {
		return contributorsUrl;
	}
	public void setContributorsUrl(String contributorsUrl) {
		this.contributorsUrl = contributorsUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(String totalResult) {
		this.totalResult = totalResult;
	}
	
}

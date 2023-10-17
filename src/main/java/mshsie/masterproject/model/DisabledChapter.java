package mshsie.masterproject.model;

public class DisabledChapter {
	
	private String chapterTitle;
	private Boolean isEnabled;
	
	
	public DisabledChapter() {}

	public DisabledChapter(String chapterTitle, Boolean isEnabled) {
		super();
		this.chapterTitle = chapterTitle;
		this.isEnabled = isEnabled;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}

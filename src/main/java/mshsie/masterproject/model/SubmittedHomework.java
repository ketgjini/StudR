package mshsie.masterproject.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="chapter_submitted_homework")
public class SubmittedHomework {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shw_id")
	private Long id;

	@Column(name = "shw_name")
	private String submittedHomeworkName;
	
	@Column(name = "shw_file")
    @Lob
    private byte[] submittedHomeworkFile;
	
	@Column(name = "submitted_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private LocalDateTime submittedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chapter_id")
    private Chapter chapter;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

	public SubmittedHomework() {}

	public SubmittedHomework(Long id, String submittedHomeworkName, byte[] submittedHomeworkFile,
			LocalDateTime submittedDate, Chapter chapter, User user) {
		this.id = id;
		this.submittedHomeworkName = submittedHomeworkName;
		this.submittedHomeworkFile = submittedHomeworkFile;
		this.submittedDate = submittedDate;
		this.chapter = chapter;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubmittedHomeworkName() {
		return submittedHomeworkName;
	}

	public void setSubmittedHomeworkName(String submittedHomeworkName) {
		this.submittedHomeworkName = submittedHomeworkName;
	}

	public byte[] getSubmittedHomeworkFile() {
		return submittedHomeworkFile;
	}

	public void setSubmittedHomeworkFile(byte[] submittedHomeworkFile) {
		this.submittedHomeworkFile = submittedHomeworkFile;
	}

	public LocalDateTime getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDateTime submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

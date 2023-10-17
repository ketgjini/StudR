package mshsie.masterproject.model;

import java.time.LocalDate;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "chapter_assigned_homework")
public class AssignedHomework {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hw_id")
	private Long id;

	@Column(name = "hw_name")
	private String hwName;
	
	@Column(name = "hw_file")
    @Lob
    private byte[] hwFile;
	
	@Column(name = "submission_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private LocalDateTime submissionDate;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chapter_id")
    private Chapter chapter;

	public AssignedHomework() {}

	public AssignedHomework(Long id, String hwName, byte[] hwFile, LocalDateTime submissionDate, Chapter chapter) {
		this.id = id;
		this.hwName = hwName;
		this.hwFile = hwFile;
		this.submissionDate = submissionDate;
		this.chapter = chapter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHwName() {
		return hwName;
	}

	public void setHwName(String hwName) {
		this.hwName = hwName;
	}

	public byte[] getHwFile() {
		return hwFile;
	}

	public void setHwFile(byte[] hwFile) {
		this.hwFile = hwFile;
	}

	public LocalDateTime getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(LocalDateTime submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	
}

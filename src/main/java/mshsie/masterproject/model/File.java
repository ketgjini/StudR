package mshsie.masterproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chapter_files")
public class File {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file")
    @Lob
    private byte[] file;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chapter_id")
    private Chapter chapter;

	public File() {}

	public File(Long id, String fileName, byte[] file, Chapter chapter) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.file = file;
		this.chapter = chapter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	
}

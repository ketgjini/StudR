package mshsie.masterproject.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chapters")
public class Chapter implements Comparable<Chapter> {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chapter_id")
	private Long id;
	
	@Column(name = "title", nullable=false)
	private String title;
	
	@Column(name = "summary")
	private String summary;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;
	
	@OneToOne(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Quiz quiz;
	
	@OneToMany(mappedBy = "chapter", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<File> files;

	@OneToOne(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
    private AssignedHomework teacherHomework;
	
	@OneToMany(mappedBy = "chapter", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SubmittedHomework> studentHomework;
	
	public Chapter() {}

	public Chapter(Long id, String title, String summary, Course course) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	
	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public AssignedHomework getTeacherHomework() {
		return teacherHomework;
	}

	public void setTeacherHomework(AssignedHomework teacherHomework) {
		this.teacherHomework = teacherHomework;
	}

	public Set<SubmittedHomework> getStudentHomework() {
		return studentHomework;
	}

	public void setStudentHomework(Set<SubmittedHomework> studentHomework) {
		this.studentHomework = studentHomework;
	}

	@Override
    public int compareTo(Chapter other) {
        return id.compareTo(other.id);
    }
}

package mshsie.masterproject.model;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "courses")
public class Course implements Comparable<Course> {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long id;
	
	@Column(name = "course_name")
	private String courseName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "dega")
	private String dega;
	
	@Column(name = "semester")
	private int semester;
	
	@Column(name = "start_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	
	@Column(name = "end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@OneToMany(mappedBy = "course", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("id ASC")
    private SortedSet<Chapter> chapters;
	
	@ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(
	  name = "user_course", 
	  joinColumns = @JoinColumn(name = "course_id"), 
	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;

	public Course() {}

	public Course(String courseName, String department, String dega) {
		this.courseName = courseName;
		this.department = department;
		this.dega = dega;
	}
	
	public Course(Long id, String courseName, String description, String department, String dega, int semester,
			Date startTime, Date endTime) {
		super();
		this.id = id;
		this.courseName = courseName;
		this.description = description;
		this.department = department;
		this.dega = dega;
		this.semester = semester;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDega() {
		return dega;
	}

	public void setDega(String dega) {
		this.dega = dega;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public SortedSet<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(SortedSet<Chapter> chapters) {
		this.chapters = chapters;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public boolean hasUser(User user) {
		for (User courseUser : getUsers()) {
			if (courseUser.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
    public int compareTo(Course other) {
        return courseName.compareTo(other.courseName);
    }
}

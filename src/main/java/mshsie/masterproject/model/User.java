package mshsie.masterproject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.mysql.jdbc.Blob;

@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	@Column(nullable=false)
	private String firstname;
	
	@Column(nullable=false)
	private String lastname;
	
	@Email(message="Email nuk eshte shkruar si duhet")
	private String email;
	
	@Column(name = "username")
	private String username;

	@Column(name = "password")
    private String password;
	
	@Column(name = "dega")
    private String dega;
	
	@Column(nullable=false)
	private int active;
	
	@Column(nullable=false)
	private String role = "";
	
	@Transient
    private String confirmPassword;
	
	@ManyToMany(mappedBy = "users", cascade ={CascadeType.MERGE, CascadeType.PERSIST})
	@OrderBy("courseName ASC")
	private SortedSet<Course> courses;
	
	@OneToMany(mappedBy = "user", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserQuiz> userQuiz;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Picture picture;

	@OneToMany(mappedBy = "user", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SubmittedHomework> studentHomework;
	
	public User() {}

	public User(String firstname, String lastname,
			@Email(message = "Email nuk eshte shkruar si duhet") String email, String username, String password,
			int active, String role) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.active = active;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDega() {
		return dega;
	}

	public void setDega(String dega) {
		this.dega = dega;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public SortedSet<Course> getCourses() {
		return courses;
	}

	public void setCourses(SortedSet<Course> courses) {
		this.courses = courses;
	}

	public List<String> getRoleList(){
        if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

	public Set<UserQuiz> getUserQuiz() {
		return userQuiz;
	}

	public void setUserQuiz(Set<UserQuiz> userQuiz) {
		this.userQuiz = userQuiz;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public Set<SubmittedHomework> getStudentHomework() {
		return studentHomework;
	}

	public void setStudentHomework(Set<SubmittedHomework> studentHomework) {
		this.studentHomework = studentHomework;
	}

}

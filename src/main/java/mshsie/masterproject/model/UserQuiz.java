package mshsie.masterproject.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="user_quiz")
public class UserQuiz implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="quiz_id")
    private Quiz quiz;
	
	@Column(name="student_points")
	private double studentPoints;
	
	public UserQuiz() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public double getStudentPoints() {
		return studentPoints;
	}

	public void setStudentPoints(double studentPoints) {
		this.studentPoints = studentPoints;
	}
	
}

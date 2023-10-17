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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quizzes")
public class Quiz {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_id")
	private Long id;
	
	@Column(name="name")
	private String quizName;

	@Column(name = "guidelines")
	private String guidelines;
	
	@OneToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
    private Chapter chapter;
	
	@OneToMany(mappedBy = "quiz", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Question> questions;

	@OneToMany(mappedBy = "quiz", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserQuiz> userQuiz;
	
	public Quiz() {}

	public Quiz(Long id, String quizName, String guidelines, Chapter chapter, Set<Question> questions,
			Set<UserQuiz> userQuiz) {
		this.id = id;
		this.quizName = quizName;
		this.guidelines = guidelines;
		this.chapter = chapter;
		this.questions = questions;
		this.userQuiz = userQuiz;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getGuidelines() {
		return guidelines;
	}

	public void setGuidelines(String guidelines) {
		this.guidelines = guidelines;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<UserQuiz> getUserQuiz() {
		return userQuiz;
	}

	public void setUserQuiz(Set<UserQuiz> userQuiz) {
		this.userQuiz = userQuiz;
	}
	
}

package com.ecristobale.hibernate.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// anotate class as an entity and map it to the DB table
// create the fields letting MySQL the generation of the ID
// constructors (no args and args except id)
// getters and setters and toString
@Entity
@Table(name="instructor_detail")
public class InstructorDetailBI {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="youtube_channel")
	private String youtubeChannel;
	
	@Column(name="hobby")
	private String hobby;
	
	// mappedBy references to JAVA CLASS Instructor --> instructorDetail property (attribute)
	// there is no @JoinColumn or @Column because this does NOT points to any database property!!
	@OneToOne(mappedBy="instructorDetailBI", cascade=CascadeType.ALL)
	private InstructorBI instructorBI;

	public InstructorDetailBI() {}

	public InstructorDetailBI(String youtubeChannel, String hobby) {
		this.youtubeChannel = youtubeChannel;
		this.hobby = hobby;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYoutubeChannel() {
		return youtubeChannel;
	}

	public void setYoutubeChannel(String youtubeChannel) {
		this.youtubeChannel = youtubeChannel;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public InstructorBI getInstructorBI() {
		return instructorBI;
	}

	public void setInstructorBI(InstructorBI instructorBI) {
		this.instructorBI = instructorBI;
	}

	@Override
	public String toString() {
		return "InstructorDetailBI [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
	}
	
}

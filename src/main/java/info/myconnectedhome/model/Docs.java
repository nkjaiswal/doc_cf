package info.myconnectedhome.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class Docs implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7214354839343825119L;

	@Id
	@Column(name = "documentid")
	private Integer documentID;
	
	@Column(name = "documentname")
	private String documentName;
	
	@Column(name = "document")
	private String document;

	public int getDocumentID() {
		return documentID;
	}

	public void setDocumentID(int documentID) {
		this.documentID = documentID;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
	
	
	
}

package com.revature.dto;

public class UpdateReimbursementStatusDTO {

	private String status;
	private int authorId;

	public UpdateReimbursementStatusDTO() {
		super();
	}

	public UpdateReimbursementStatusDTO(String status, int authorId) {
		super();
		this.status = status;
		this.authorId = authorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateReimbursementStatusDTO other = (UpdateReimbursementStatusDTO) obj;
		if (authorId != other.authorId)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UpdateReimbursementStatusDTO [status=" + status + ", authorId=" + authorId + "]";
	}
}

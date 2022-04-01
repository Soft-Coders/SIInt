package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrimaryKey implements Serializable {
	
	@Column(name="ADMIN_ID", nullable = false)
	private Long adminId;
	
	@Column(name="ADMIN_PASSWORD", nullable = false)
	private String adminPassword;
	
	/**
	 * @return the adminId
	 */
	public Long getAdminId() {
		return adminId;
	}
	
	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	
	/**
	 * @return the adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}
	
	/**
	 * @param adminPassword the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(adminId, adminPassword);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimaryKey other = (PrimaryKey) obj;
		return Objects.equals(adminId, other.adminId) && Objects.equals(adminPassword, other.adminPassword);
	}
	
	@Override
	public String toString() {
		return "primaryKey [adminId=" + adminId + ", adminPassword=" + adminPassword + "]";
	}
	
}

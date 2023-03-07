/**
 * 
 */
package com.churchclerk.memberapi.entity;

import com.churchclerk.churchapi.entity.ChurchEntity;
import com.churchclerk.churchapi.model.Church;
import com.churchclerk.memberapi.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * 
 * @author dongp
 *
 */
@Entity
@Table(name="member")
@SuperBuilder
@NoArgsConstructor
@Setter
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity extends Member {

	private Set<ChurchEntity> churchEntities;

	@Column(name="active")
	@Override
	public boolean isActive() {
		return super.isActive();
	}

	@Id
	@Column(name="id")
	@Override
	public UUID getId() {
		return super.getId();
	}

	@Column(name="name")
	@Override
	public String getName() {
		return super.getName();
	}

	@Column(name="othername")
	@Override
	public String getOtherName() {
		return super.getOtherName();
	}

	@Column(name="start_date")
	@Override
	public Date getStartDate() {
		return super.getStartDate();
	}

	@Column(name="end_date")
	@Override
	public Date getEndDate() {
		return super.getEndDate();
	}

	@Column(name="regular")
	@Override
	public boolean isRegular() {
		return super.isRegular();
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	@JoinTable(name = "member_church",
			joinColumns = { @JoinColumn(name = "member_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "church_id", referencedColumnName = "id") }
	)
	public Set<ChurchEntity> getChurchEntities() {
		return churchEntities;
	}

	@Transient
	@Override
	public Set<Church> getChurches() {
		return super.getChurches();
	}

	@Column(name="created_date")
	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Column(name="created_by")
	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Column(name="updated_date")
	@Override
	public Date getUpdatedDate() {
		return super.getUpdatedDate();
	}

	@Column(name="updated_by")
	@Override
	public String getUpdatedBy() {
		return super.getUpdatedBy();
	}
}

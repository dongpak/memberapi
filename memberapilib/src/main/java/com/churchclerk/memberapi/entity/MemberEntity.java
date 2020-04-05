/**
 * 
 */
package com.churchclerk.memberapi.entity;

import com.churchclerk.churchapi.entity.ChurchEntity;
import com.churchclerk.churchapi.model.Church;
import com.churchclerk.memberapi.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author dongp
 *
 */
@Entity
@Table(name="member")
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
	public String getId() {
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

	public void setChurchEntities(Set<ChurchEntity> churchEntities) {
		this.churchEntities = churchEntities;

		Set<Church>	set = new HashSet<Church>();
		churchEntities.forEach(entity -> {
			set.add(entity);
		});
		setChurches(set);
	}

	@Transient
	@Override
	public Set<Church> getChurches() {
		return super.getChurches();
	}

	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	public Date getUpdatedDate() {
		return super.getUpdatedDate();
	}

	@Override
	public String getUpdatedBy() {
		return super.getUpdatedBy();
	}
}

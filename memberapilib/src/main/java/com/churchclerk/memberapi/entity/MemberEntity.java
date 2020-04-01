/**
 * 
 */
package com.churchclerk.memberapi.entity;

import com.churchclerk.memberapi.model.Member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 
 * @author dongp
 *
 */
@Entity
@Table(name="member")
public class MemberEntity extends Member {

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

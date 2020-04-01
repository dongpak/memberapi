/**
 * 
 */
package com.churchclerk.memberapi.service;

import com.churchclerk.memberapi.entity.MemberEntity;
import com.churchclerk.memberapi.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;


/**
 * 
 * @author dongp
 *
 */
@Service
public class MemberService {

	private static Logger logger	= LoggerFactory.getLogger(MemberService.class);

	@Autowired
	private MemberStorage storage;


	/**
	 *
	 * @return
	 */
	public Page<? extends Member> getResources(Pageable pageable, Member criteria) {

		Page<MemberEntity> page = storage.findAll(new MemberResourceSpec(criteria), pageable);

		return page;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Member getResource(String id) {

		Optional<MemberEntity> entity = storage.findById(id);

		checkResourceNotFound(id, entity);
		return entity.get();
	}

	private void checkResourceNotFound(String id, Optional<MemberEntity> optional) {
		if (optional.isPresent() == false) {
			throw new NotFoundException("No such Member resource with id: " + id);
		}
	}

	/**
	 *
	 * @param resource
	 * @return
	 */
	public Member createResource(Member resource) {
		MemberEntity entity = new MemberEntity();

		entity.copy(resource);

		return storage.save(entity);
	}


	/**
	 *
	 * @param resource
	 * @return
	 */
	public Member updateResource(Member resource) {
		Optional<MemberEntity> optional = storage.findById(resource.getId());

		checkResourceNotFound(resource.getId(), optional);

		MemberEntity entity = optional.get();

		entity.copyNonNulls(resource);
		return storage.save(entity);
	}



	/**
	 *
	 * @param id
	 * @return
	 */
	public Member deleteResource(String id) {
		Optional<MemberEntity> optional = storage.findById(id);

		checkResourceNotFound(id, optional);

		storage.deleteById(id);
		return optional.get();
	}
}

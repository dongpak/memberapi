/**
 * 
 */
package com.churchclerk.memberapi.service;

import com.churchclerk.churchapi.entity.ChurchEntity;
import com.churchclerk.churchapi.model.Church;
import com.churchclerk.memberapi.entity.MemberEntity;
import com.churchclerk.memberapi.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


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

		page.forEach(this::moveChurches);

		return page;
	}

	private void moveChurches(MemberEntity entity) {
		if (entity.getChurchEntities() != null) {
			Set<Church> set = new HashSet<Church>();

			entity.getChurchEntities().forEach(set::add);
			entity.setChurches(set);
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Member getResource(String id) {

		Optional<MemberEntity> optional = storage.findById(id);

		checkResourceNotFound(id, optional);

		MemberEntity	entity = optional.get();

		moveChurches(entity);
		return entity;
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
		if (resource.getChurches() != null) {
			entity.setChurchEntities(new HashSet<>());
			resource.getChurches().forEach(church -> {
				ChurchEntity ce = new ChurchEntity();

				ce.copy(church);

				entity.getChurchEntities().add(ce);
			});
		}
		MemberEntity saved = storage.save(entity);
		moveChurches(saved);

		return saved;
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

		if (resource.getChurches() != null) {
			if (resource.getChurches().size() > 0) {
				entity.getChurchEntities().clear();
				resource.getChurches().forEach(church -> {
					ChurchEntity ce = new ChurchEntity();

					ce.copy(church);

					entity.getChurchEntities().add(ce);
				});
			}
		}

		MemberEntity saved = storage.save(entity);
		moveChurches(saved);

		return saved;
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

		MemberEntity entity = optional.get();
		moveChurches(entity);
		return entity;
	}
}

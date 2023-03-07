/**
 * 
 */
package com.churchclerk.memberapi.service;

import com.churchclerk.memberapi.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


/**
 * 
 * @author dongp
 *
 */
public interface MemberStorage extends CrudRepository<MemberEntity, UUID>, JpaSpecificationExecutor<MemberEntity> {

}

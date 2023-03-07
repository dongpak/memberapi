/*

 */
package com.churchclerk.memberapi;

import com.churchclerk.baseapi.BaseApi;
import com.churchclerk.baseapi.model.ApiCaller;
import com.churchclerk.memberapi.model.Member;
import com.churchclerk.memberapi.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.Date;
import java.util.UUID;

/**
 *
 */
@Component
@Path("/member")
@Slf4j
public class MemberApi extends BaseApi<Member> {

    @QueryParam("name")
    protected String nameLike;

    @QueryParam("otherName")
    protected String  otherNameLike;

    @QueryParam("startDate")
    protected Date    startDateLike;

    @QueryParam("endDate")
    protected Date    endDateLike;

    @QueryParam("regular")
    protected Boolean regularLike;


    @Autowired
    private MemberService       service;


    /**
     *
     */
    public MemberApi() {
        super(Member.class);
        setReadRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK, ApiCaller.Role.OFFICIAL, ApiCaller.Role.MEMBER, ApiCaller.Role.NONMEMBER);
        setCreateRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK);
        setUpdateRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK, ApiCaller.Role.OFFICIAL, ApiCaller.Role.MEMBER, ApiCaller.Role.NONMEMBER);
        setDeleteRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK);

    }

    @Override
    protected Page<? extends Member> doGet(Pageable pageable) {

        return service.getResources(pageable, createCriteria());
    }

    /**
     *
     * @return
     */
    protected Member createCriteria() {
        Member criteria	= new Member();

        addBaseCriteria(criteria);

        criteria.setName(nameLike);
        criteria.setOtherName(otherNameLike);
        criteria.setStartDate(startDateLike);
        criteria.setEndDate(endDateLike);
        criteria.setRegular(regularLike == null ? true : regularLike);


        if (id == null) {
            apiCaller.getMemberOf().forEach(churchId -> {
                if (id == null) {
                    id = churchId;
                }
            });
        }

        if (readAllowed(id, this::hasSuperRole)) {
            if (id != null) {
                criteria.setId(UUID.fromString(id));
            }
        }
        else {
            // force return of empty array
            criteria.setId(null);
        }

        log.info("id="+criteria.getId());
        return criteria;
    }

    @Override
    protected Member doGet() {
        if ((id == null) || (id.trim().isEmpty())) {
            throw new BadRequestException("Church id cannot be empty");
        }

        Member resource = service.getResource(id);

        if ((resource == null) || (readAllowed(resource.getId().toString()) == false)) {
            throw new NotFoundException();
        }

        return resource;
    }

    @Override
    protected Member doCreate(Member resource) {
        if (resource.getId() != null) {
            throw new BadRequestException("Church id should not be present");
        }

        if (resource.getName() == null) {
            throw new BadRequestException("Church's name cannot be null");
        }

        if (createAllowed(null, this::hasSuperRole) == false) {
            throw new ForbiddenException();
        }

        Date now = new Date();
        resource.setId(UUID.randomUUID());

        if (resource.getChurches() != null) {
            resource.getChurches().forEach(church -> {
//                church.setCreatedBy(apiCaller.getUserid());
//                church.setCreatedDate(now);
//                church.setUpdatedBy(apiCaller.getUserid());
//                church.setUpdatedDate(now);
            });
        }
        return service.createResource(resource);
    }

    @Override
    protected Member doUpdate(Member resource) {
        if ((id == null) || (id.isEmpty()) || (resource.getId() == null)) {
            throw new BadRequestException("Church id cannot be empty");
        }

        if (resource.getId().toString().equals(id) == false) {
            throw new BadRequestException("Church id does not match");
        }

        if ((resource.getName() == null) || (resource.getName().trim().isEmpty())) {
            throw new BadRequestException("Church name cannot be empty");
        }

        if (updateAllowed(id) == false) {
            throw new ForbiddenException();
        }

        Date now = new Date();
        resource.setUpdatedBy(apiCaller.getUserid());
        resource.setUpdatedDate(now);

        if (resource.getChurches() != null) {
            resource.getChurches().forEach(church -> {
//                church.setCreatedBy(apiCaller.getUserid());
//                church.setCreatedDate(now);
//                church.setUpdatedBy(apiCaller.getUserid());
//                church.setUpdatedDate(now);
            });
        }

        return service.updateResource(resource);
    }

    @Override
    protected Member doDelete() {
        if ((id == null) || id.isEmpty()) {
            throw new BadRequestException("Church id cannot be empty");
        }

        if (deleteAllowed(id) == false) {
            throw new ForbiddenException();
        }

        return service.deleteResource(id);
    }
}

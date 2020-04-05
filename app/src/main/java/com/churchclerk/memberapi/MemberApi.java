/*

 */
package com.churchclerk.memberapi;

import com.churchclerk.baseapi.BaseApi;
import com.churchclerk.baseapi.model.ApiCaller;
import com.churchclerk.memberapi.model.Member;
import com.churchclerk.memberapi.service.MemberService;
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
public class MemberApi extends BaseApi<Member> {

    private static Logger logger = LoggerFactory.getLogger(MemberApi.class);

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
    private MemberService service;

    /**
     *
     */
    public MemberApi() {
        super(logger, Member.class);
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
                criteria.setId(id);
            }
        }
        else {
            // force return of empty array
            criteria.setId("NOTALLOWED");
        }

        logger.info("id="+criteria.getId());
        return criteria;
    }

    @Override
    protected Member doGet() {
        if ((id == null) || (id.trim().isEmpty())) {
            throw new BadRequestException("Church id cannot be empty");
        }

        Member resource = service.getResource(id);

        if ((resource == null) || (readAllowed(resource.getId()) == false)) {
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

        if (createAllowed(resource.getId(), this::hasSuperRole) == false) {
            throw new ForbiddenException();
        }

        resource.setId(UUID.randomUUID().toString());
        resource.setCreatedBy(apiCaller.getUserid());
        resource.setCreatedDate(new Date());
        resource.setUpdatedBy(apiCaller.getUserid());
        resource.setUpdatedDate(new Date());

        return service.createResource(resource);
    }

    @Override
    protected Member doUpdate(Member resource) {
        if ((id == null) || (id.isEmpty()) || (resource.getId() == null) || (resource.getId().isEmpty())) {
            throw new BadRequestException("Church id cannot be empty");
        }

        if (resource.getId().equals(id) == false) {
            throw new BadRequestException("Church id does not match");
        }

        if ((resource.getName() == null) || (resource.getName().trim().isEmpty())) {
            throw new BadRequestException("Church name cannot be empty");
        }

        if (updateAllowed(id) == false) {
            throw new ForbiddenException();
        }

        resource.setUpdatedBy(apiCaller.getUserid());
        resource.setUpdatedDate(new Date());

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

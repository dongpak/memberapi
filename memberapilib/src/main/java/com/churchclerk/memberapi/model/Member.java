/*

 */
package com.churchclerk.memberapi.model;

import com.churchclerk.baseapi.model.BaseModel;
import com.churchclerk.churchapi.model.Church;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public class Member extends BaseModel {
    private String      name;
    private String      otherName;
    private Date        startDate;
    private Date        endDate;
    private boolean     regular;
    private Set<Church> churches;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isRegular() {
        return regular;
    }

    public void setRegular(boolean regular) {
        this.regular = regular;
    }

    public Set<Church> getChurches() {
        return churches;
    }

    public void setChurches(Set<Church> churches) {
        this.churches = churches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        if (!super.equals(o)) return false;
        Member member = (Member) o;
        return regular == member.regular &&
                Objects.equals(name, member.name) &&
                Objects.equals(otherName, member.otherName) &&
                Objects.equals(startDate, member.startDate) &&
                Objects.equals(endDate, member.endDate) &&
                Objects.equals(churches, member.churches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, otherName, startDate, endDate, regular, churches);
    }

    /**
     *
     * @param source
     */
    public void copy(Member source) {
        super.copy(source);
        setName(source.getName());
        setOtherName(source.getOtherName());
        setStartDate(source.getStartDate());
        setEndDate(source.getEndDate());
        setRegular(source.isRegular());
    }

    /**
     *
     * @param source
     */
    public void copyNonNulls(Member source) {
        super.copyNonNulls(source);
        copy(source.getName(), this::setName);
        copy(source.getOtherName(), this::setOtherName);
        copy(source.getStartDate(), this::setStartDate);
        copy(source.getEndDate(), this::setEndDate);
        copy(source.isRegular(), this::setRegular);
    }
}

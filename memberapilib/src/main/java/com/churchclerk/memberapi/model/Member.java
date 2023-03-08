/*

 */
package com.churchclerk.memberapi.model;

import com.churchclerk.baseapi.model.BaseModel;
import com.churchclerk.churchapi.model.Church;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Set;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class Member extends BaseModel {
    private String      name;
    private String      otherName;
    private Date        startDate;
    private Date        endDate;
    private boolean     regular;
    private Set<Church> churches;

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

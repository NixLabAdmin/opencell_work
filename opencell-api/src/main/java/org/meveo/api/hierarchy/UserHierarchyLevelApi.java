package org.meveo.api.hierarchy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.meveo.admin.exception.BusinessException;
import org.meveo.api.BaseApi;
import org.meveo.api.dto.hierarchy.UserHierarchyLevelDto;
import org.meveo.api.exception.DeleteReferencedEntityException;
import org.meveo.api.exception.EntityAlreadyExistsException;
import org.meveo.api.exception.EntityDoesNotExistsException;
import org.meveo.api.exception.MeveoApiException;
import org.meveo.commons.utils.StringUtils;
import org.meveo.model.hierarchy.HierarchyLevel;
import org.meveo.model.hierarchy.UserHierarchyLevel;
import org.meveo.service.hierarchy.impl.UserHierarchyLevelService;

public class UserHierarchyLevelApi extends BaseApi {

    @Inject
    private UserHierarchyLevelService userHierarchyLevelService;

    /**
     * Creates a new User Hierarchy Level entity.
     *
     * @param postData
     * @throws org.meveo.api.exception.MeveoApiException
     * @throws org.meveo.admin.exception.BusinessException
     */
    public void create(UserHierarchyLevelDto postData) throws MeveoApiException, BusinessException {

        String hierarchyLevelCode = postData.getCode();
        String parentLevelCode = postData.getParentLevel();

        if (StringUtils.isBlank(hierarchyLevelCode)) {
            missingParameters.add("hierarchyLevelCode");
        }

        handleMissingParameters();

        UserHierarchyLevel userHierarchyLevel = userHierarchyLevelService.findByCode(hierarchyLevelCode);

        if (userHierarchyLevel != null) {
            throw new EntityAlreadyExistsException(UserHierarchyLevel.class, hierarchyLevelCode);
        }

        UserHierarchyLevel parentLevel = null;
        if (!StringUtils.isBlank(postData.getParentLevel())) {
            parentLevel = userHierarchyLevelService.findByCode(parentLevelCode);
            if (parentLevel == null) {
                throw new EntityDoesNotExistsException(UserHierarchyLevel.class, parentLevelCode);
            }
        }

        userHierarchyLevel = fromDto(postData, parentLevel, null);

        userHierarchyLevelService.create(userHierarchyLevel);

    }

    /**
     * Updates a User Hierarchy Level Entity based on title code.
     *
     * @param postData

     * @throws org.meveo.api.exception.MeveoApiException
     * @throws org.meveo.admin.exception.BusinessException
     */
    public void update(UserHierarchyLevelDto postData) throws MeveoApiException, BusinessException {
        String hierarchyLevelCode = postData.getCode();
        String parentLevelCode = postData.getParentLevel();

        if (StringUtils.isBlank(hierarchyLevelCode)) {
            missingParameters.add("hierarchyLevelCode");
        }

        handleMissingParameters();

        UserHierarchyLevel userHierarchyLevel = userHierarchyLevelService.findByCode(hierarchyLevelCode);
        UserHierarchyLevel parentLevel = null;
        if (userHierarchyLevel != null) {
            if (!StringUtils.isBlank(postData.getParentLevel())) {
                parentLevel = userHierarchyLevelService.findByCode(parentLevelCode);
                if (parentLevel == null) {
                    throw new EntityDoesNotExistsException(UserHierarchyLevel.class, parentLevelCode);
                }
            }
            userHierarchyLevel = fromDto(postData, parentLevel, userHierarchyLevel);
            userHierarchyLevelService.update(userHierarchyLevel);
        } else {
            throw new EntityDoesNotExistsException(UserHierarchyLevel.class, hierarchyLevelCode);
        }
    }

    /**
     * Returns UserHierarchyLevelDto based on hierarchy Level Code.
     *
     * @param hierarchyLevelCode
     * @return
     * @throws org.meveo.api.exception.MeveoApiException
     */
    public UserHierarchyLevelDto find(String hierarchyLevelCode) throws MeveoApiException {
        if (StringUtils.isBlank(hierarchyLevelCode)) {
            missingParameters.add("hierarchyLevelCode");
        }
        handleMissingParameters();
        UserHierarchyLevel userHierarchyLevel = userHierarchyLevelService.findByCode(hierarchyLevelCode, Arrays.asList("parentLevel", "childLevels"));
        if (userHierarchyLevel != null) {
            UserHierarchyLevelDto userHierarchyLevelDto = new UserHierarchyLevelDto(userHierarchyLevel);
            if (userHierarchyLevel.getParentLevel() != null) {
                userHierarchyLevelDto.setParentLevel(userHierarchyLevel.getParentLevel().getCode());
            }
            if (!userHierarchyLevel.getChildLevels().isEmpty()){
                userHierarchyLevelDto.setChildLevels(convertToUserHierarchyLevelDto(userHierarchyLevel.getChildLevels()));
            }
            return userHierarchyLevelDto;
        }
        throw new EntityDoesNotExistsException(UserHierarchyLevel.class, hierarchyLevelCode);
    }

    /**
     * Removes a User Hierarchy Level based on user hierarchy level code.
     *
     * @param hierarchyLevelCode

     * @throws org.meveo.api.exception.MeveoApiException
     * @throws BusinessException 
     */
    public void remove(String hierarchyLevelCode) throws MeveoApiException, BusinessException {

        if (StringUtils.isBlank(hierarchyLevelCode)) {
            missingParameters.add("hierarchyLevelCode");
        }

        handleMissingParameters();

        UserHierarchyLevel userHierarchyLevel = userHierarchyLevelService.findByCode(hierarchyLevelCode);
        if (userHierarchyLevel != null) {
            if (!userHierarchyLevelService.canDeleteUserHierarchyLevel(userHierarchyLevel.getId())) {
                throw new DeleteReferencedEntityException(UserHierarchyLevel.class, hierarchyLevelCode);
            }
            userHierarchyLevelService.remove(userHierarchyLevel);

        } else {
            throw new EntityDoesNotExistsException(UserHierarchyLevel.class, hierarchyLevelCode);
        }
    }

    
    /**
     * Create or Update a User Hierarchy Level Entity based on title code.
     *
     * @param postData

     * @throws org.meveo.api.exception.MeveoApiException
     * @throws org.meveo.admin.exception.BusinessException
     */
    public void createOrUpdate(UserHierarchyLevelDto postData) throws MeveoApiException, BusinessException {
        String hierarchyLevelCode = postData.getCode();   
        if (StringUtils.isBlank(hierarchyLevelCode)) {
            missingParameters.add("hierarchyLevelCode");
        }
        handleMissingParameters();
        UserHierarchyLevel userHierarchyLevel = userHierarchyLevelService.findByCode(hierarchyLevelCode);
        if (userHierarchyLevel != null) {
           update(postData);
        } else {
            create(postData);
        }
    }
    
    
    @SuppressWarnings("rawtypes")
    private List<UserHierarchyLevelDto> convertToUserHierarchyLevelDto(Set<HierarchyLevel> userHierarchyLevels) {
        List<UserHierarchyLevelDto> userHierarchyLevelDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userHierarchyLevels)) {
            for (HierarchyLevel userHierarchyLevel : userHierarchyLevels) {
                UserHierarchyLevelDto userHierarchyLevelDto = new UserHierarchyLevelDto(userHierarchyLevel);
                userHierarchyLevelDtos.add(userHierarchyLevelDto);
            }
        }
        return userHierarchyLevelDtos;
    }

    protected UserHierarchyLevel fromDto(UserHierarchyLevelDto userHierarchyLevelDto, UserHierarchyLevel parentLevel, UserHierarchyLevel hierarchyLevelUpdate) {
        UserHierarchyLevel userHierarchyLevel = new UserHierarchyLevel();
        if (hierarchyLevelUpdate != null) {
            userHierarchyLevel = hierarchyLevelUpdate;
        }
        userHierarchyLevel.setCode(userHierarchyLevelDto.getCode());
        userHierarchyLevel.setDescription(userHierarchyLevelDto.getDescription());
        userHierarchyLevel.setOrderLevel(userHierarchyLevelDto.getOrderLevel());
        userHierarchyLevel.setParentLevel(parentLevel);
        return userHierarchyLevel;
    }
}

package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.UserPackageResponse;
import com.vn.investion.model.UserPackage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2UserPackageResponse extends BeanMapper<UserPackage, UserPackageResponse>{
    Entity2UserPackageResponse INSTANCE = Mappers.getMapper(Entity2UserPackageResponse.class);
}

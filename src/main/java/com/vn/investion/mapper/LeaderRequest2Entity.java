package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.LeaderPackageRequest;
import com.vn.investion.model.LeaderPackage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LeaderRequest2Entity extends BeanMapper<LeaderPackageRequest, LeaderPackage>{
    LeaderRequest2Entity INSTANCE = Mappers.getMapper(LeaderRequest2Entity.class);
}

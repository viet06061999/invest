package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.InvestPackageRequest;
import com.vn.investion.model.InvestPackage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InvestRequest2Entity extends BeanMapper<InvestPackageRequest, InvestPackage>{
    InvestRequest2Entity INSTANCE = Mappers.getMapper(InvestRequest2Entity.class);
}

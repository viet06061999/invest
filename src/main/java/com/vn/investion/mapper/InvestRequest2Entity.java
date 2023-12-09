package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.InvestPackageRequest;
import com.vn.investion.model.InvestPackage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvestRequest2Entity extends BeanMapper<InvestPackageRequest, InvestPackage>{
    InvestRequest2Entity INSTANCE = Mappers.getMapper(InvestRequest2Entity.class);
}

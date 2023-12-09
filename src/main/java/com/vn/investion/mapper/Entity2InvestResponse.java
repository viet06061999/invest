package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.InvestPackageRequest;
import com.vn.investion.dto.ipackage.InvestPackageResponse;
import com.vn.investion.model.InvestPackage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2InvestResponse extends BeanMapper<InvestPackage, InvestPackageResponse> {
    Entity2InvestResponse INSTANCE = Mappers.getMapper(Entity2InvestResponse.class);
}

package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.LeaderPackageRequest;
import com.vn.investion.dto.ipackage.LeaderPackageResponse;
import com.vn.investion.model.InvestPackage;
import com.vn.investion.model.LeaderPackage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2LeaderResponse extends BeanMapper<LeaderPackage, LeaderPackageResponse> {
    Entity2LeaderResponse INSTANCE = Mappers.getMapper(Entity2LeaderResponse.class);
}

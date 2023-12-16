package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.InterestHisResponse;
import com.vn.investion.model.InvestHis;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2InterestHisResponse extends BeanMapper<InvestHis, InterestHisResponse>{
    Entity2InterestHisResponse INSTANCE = Mappers.getMapper(Entity2InterestHisResponse.class);
}

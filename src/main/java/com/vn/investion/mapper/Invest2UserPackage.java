package com.vn.investion.mapper;

import com.vn.investion.model.InvestPackage;
import com.vn.investion.model.UserPackage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface Invest2UserPackage extends BeanMapper<InvestPackage, UserPackage>{
    Invest2UserPackage INSTANCE = Mappers.getMapper(Invest2UserPackage.class);
}

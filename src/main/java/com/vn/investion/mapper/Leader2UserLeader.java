package com.vn.investion.mapper;

import com.vn.investion.model.LeaderPackage;
import com.vn.investion.model.UserLeader;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface Leader2UserLeader extends BeanMapper<LeaderPackage, UserLeader>{
    Leader2UserLeader INSTANCE = Mappers.getMapper(Leader2UserLeader.class);
}

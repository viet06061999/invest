package com.vn.investion.mapper;

import com.vn.investion.dto.ipackage.UserLeaderResponse;
import com.vn.investion.model.UserLeader;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2UserLeaderResponse extends BeanMapper<UserLeader, UserLeaderResponse>{
    Entity2UserLeaderResponse INSTANCE = Mappers.getMapper(Entity2UserLeaderResponse.class);
}

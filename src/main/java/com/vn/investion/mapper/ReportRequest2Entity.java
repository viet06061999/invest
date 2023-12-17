package com.vn.investion.mapper;

import com.vn.investion.dto.auth.ReportRequest;
import com.vn.investion.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportRequest2Entity extends BeanMapper<ReportRequest, Report>{
    ReportRequest2Entity INSTANCE = Mappers.getMapper(ReportRequest2Entity.class);
}

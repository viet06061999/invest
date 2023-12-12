package com.vn.investion.mapper;

import com.vn.investion.dto.auth.ReportResponse;
import com.vn.investion.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Entity2ReportResponse extends BeanMapper<Report, ReportResponse>{
    Entity2ReportResponse INSTANCE = Mappers.getMapper(Entity2ReportResponse.class);
}

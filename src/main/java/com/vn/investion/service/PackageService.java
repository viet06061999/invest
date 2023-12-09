package com.vn.investion.service;

import com.vn.investion.dto.ipackage.InvestPackageRequest;
import com.vn.investion.dto.ipackage.InvestPackageResponse;
import com.vn.investion.dto.ipackage.LeaderPackageRequest;
import com.vn.investion.dto.ipackage.LeaderPackageResponse;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.mapper.Entity2InvestResponse;
import com.vn.investion.mapper.Entity2LeaderResponse;
import com.vn.investion.mapper.InvestRequest2Entity;
import com.vn.investion.mapper.LeaderRequest2Entity;
import com.vn.investion.repo.InvestPackageRepository;
import com.vn.investion.repo.LeaderPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final InvestPackageRepository investPackageRepository;
    private final LeaderPackageRepository leaderPackageRepository;

    @Transactional
    public InvestPackageResponse createInvestPackage(InvestPackageRequest request) {
        var entity = InvestRequest2Entity.INSTANCE.map(request);
        entity.setIsActive(true);
        return Entity2InvestResponse.INSTANCE.map(investPackageRepository.save(entity));
    }

    @Transactional
    public LeaderPackageResponse createLeaderPackage(LeaderPackageRequest request) {
        var entity = LeaderRequest2Entity.INSTANCE.map(request);
        entity.setIsActive(true);
        return Entity2LeaderResponse.INSTANCE.map(leaderPackageRepository.save(entity));
    }

    @Transactional
    public InvestPackageResponse updateInvestPackage(InvestPackageRequest request, Long packageId) {
        var entityOptional = investPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Invest package not exists!", 404);
        }
        var entity = entityOptional.get();
        InvestRequest2Entity.INSTANCE.mapTo(request, entity);
        return Entity2InvestResponse.INSTANCE.map(investPackageRepository.save(entity));
    }

    @Transactional
    public LeaderPackageResponse updateLeaderPackage(LeaderPackageRequest request, Long packageId) {
        var entityOptional = leaderPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Leader package not exists!", 404);
        }
        var entity = entityOptional.get();
        LeaderRequest2Entity.INSTANCE.mapTo(request, entity);
        return Entity2LeaderResponse.INSTANCE.map(leaderPackageRepository.save(entity));
    }

    public InvestPackageResponse getDetailInvest(Long packageId) {
        var entityOptional = investPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Invest package not exists!", 404);
        }
        var entity = entityOptional.get();
        return Entity2InvestResponse.INSTANCE.map(entity);
    }

    public LeaderPackageResponse getDetailLeader(Long packageId) {
        var entityOptional = leaderPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Leader package not exists!", 404);
        }
        var entity = entityOptional.get();
        return Entity2LeaderResponse.INSTANCE.map(entity);
    }

    public List<InvestPackageResponse> getAllInvest() {
        var entityList = investPackageRepository.findAllByIsActiveTrue();
        if (entityList.isEmpty()) {
            throw new BusinessException(4004, "Reference Invest package not exists!", 404);
        }
        return entityList.stream().map(Entity2InvestResponse.INSTANCE::map).toList();
    }

    public List<LeaderPackageResponse> getAllLeader() {
        var entityList = leaderPackageRepository.findAllByIsActiveTrue();
        if (entityList.isEmpty()) {
            throw new BusinessException(4004, "Reference Leader package not exists!", 404);
        }
        return entityList.stream().map(Entity2LeaderResponse.INSTANCE::map).toList();
    }

    @Transactional
    public Boolean deleteInvest(Long packageId) {
        var entityOptional = investPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Invest package not exists!", 404);
        }
        var entity = entityOptional.get();
        entity.setIsActive(false);
        return investPackageRepository.save(entity) != null;
    }

    @Transactional
    public Boolean deleteLeader(Long packageId) {
        var entityOptional = leaderPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Leader package not exists!", 404);
        }
        var entity = entityOptional.get();
        entity.setIsActive(false);
        return leaderPackageRepository.save(entity) != null;
    }
}

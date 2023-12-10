package com.vn.investion.service;

import com.vn.investion.dto.ipackage.*;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.mapper.*;
import com.vn.investion.model.User;
import com.vn.investion.model.define.UserPackageStatus;
import com.vn.investion.repo.*;
import com.vn.investion.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final InvestPackageRepository investPackageRepository;
    private final LeaderPackageRepository leaderPackageRepository;
    private final UserPackageRepository userPackageRepository;
    private final UserRepository userRepository;
    private final UserLeaderRepository userLeaderRepository;
    private final MultiLevelRateRepository multiLevelRateRepository;

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

    @Transactional
    public UserPackageResponse jointPackage(String phone, Long packageId) {
        var user = getUserByPhoneLock(phone);
        var entityOptional = investPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference invest package not exists!", 404);
        }
        var entity = entityOptional.get();
        if (user.getPoint() - entity.getAmt() < 0) {
            throw new BusinessException(4015, "Account Balance not enough!", 400);
        }
        user.setPoint(user.getPoint() - entity.getAmt());
        user = userRepository.save(user);

        var now = OffsetDateTime.now();
        var userPackage = Invest2UserPackage.INSTANCE.map(entity);
        userPackage.setUser(user);
        userPackage.setId(null);
        userPackage.setCreatedAt(null);
        userPackage.setInvestPackage(entity);
        userPackage.setInterestDate(now);
        userPackage.setWithdrawDate(DateTimeUtils.getDateFromInvest(entity.getInvestType(), entity.getDuration()));
        return Entity2UserPackageResponse.INSTANCE.map(userPackageRepository.save(userPackage));
    }

    @Transactional
    public UserLeaderResponse jointLeader(String phone, Long packageId) {
        var user = getUserByPhoneLock(phone);
        var entityOptional = leaderPackageRepository.findByIdAndIsActiveTrue(packageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference leader package not exists!", 404);
        }
        var entity = entityOptional.get();
        if (user.getPoint() - entity.getAmt() < 0) {
            throw new BusinessException(4015, "Account Balance not enough!", 400);
        }
        user.setPoint(user.getPoint() - entity.getAmt());
        user = userRepository.save(user);

        var now = OffsetDateTime.now();
        var userLeader = Leader2UserLeader.INSTANCE.map(entity);
        userLeader.setUser(user);
        userLeader.setId(null);
        userLeader.setCreatedAt(null);
        userLeader.setLeaderPackage(entity);
        userLeader.setInterestDate(now);
        userLeader.setWithdrawDate(DateTimeUtils.getDateFromInvest(entity.getInvestType(), entity.getDuration()));
        return Entity2UserLeaderResponse.INSTANCE.map(userLeaderRepository.save(userLeader));
    }

    public List<UserLeaderResponse> getUserLeaderByPhone(String phone) {
        var entityList = userLeaderRepository.findAllByPhone(phone);
        if (entityList.isEmpty()) {
            throw new BusinessException(4004, "Reference leader not exists!", 404);
        }
        return entityList.stream().map(Entity2UserLeaderResponse.INSTANCE::map).toList();
    }

    public List<UserPackageResponse> getUserPackageByPhone(String phone) {
        var entityList = userPackageRepository.findAllByPhone(phone);
        if (entityList.isEmpty()) {
            throw new BusinessException(4004, "Reference package not exists!", 404);
        }
        return entityList.stream().map(Entity2UserPackageResponse.INSTANCE::map).toList();
    }

    public List<UserLeaderResponse> getAllUserLeader() {
        var entityList = userLeaderRepository.findAll();
        if (entityList.isEmpty()) {
            throw new BusinessException(4004, "Reference leader not exists!", 404);
        }
        return entityList.stream().map(Entity2UserLeaderResponse.INSTANCE::map).toList();
    }

    public List<UserPackageResponse> getAllUserPackage() {
        var entityList = userPackageRepository.findAll();
        if (entityList.isEmpty()) {
            throw new BusinessException(4004, "Reference package not exists!", 404);
        }
        return entityList.stream().map(Entity2UserPackageResponse.INSTANCE::map).toList();
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

    @Transactional
    public UserPackageResponse withdrawIntInvest(Long userPackageId) {
        var entityOptional = userPackageRepository.findById(userPackageId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference invest package not exists!", 404);
        }
        var entity = entityOptional.get();
        var interest = entity.getCurrentInterest();
        if (interest <= 0) {
            throw new BusinessException(4015, "Interest not enough to withdraw!", 400);
        }
        entity.setInterestDate(OffsetDateTime.now());
        userPackageRepository.save(entity);
        var user = entity.getUser();
        user.setPoint(user.getPoint() + interest);
        userRepository.save(user);
        var refUser = getLeaderByRefId(user.getRefId());
        if(refUser != null){
            var rateF1 = interest * getRateOfF(1);
            refUser.setPoint(refUser.getPoint() + rateF1);
            userRepository.save(refUser);
        }
        return Entity2UserPackageResponse.INSTANCE.map(userPackageRepository.findById(userPackageId).get());
    }

    @Transactional
    public UserLeaderResponse withdrawIntLeader(Long userLeaderId) {
        var entityOptional = userLeaderRepository.findById(userLeaderId);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Leader package not exists!", 404);
        }
        var entity = entityOptional.get();
        var interest = entity.getCurrentInterest();
        if (interest <= 0) {
            throw new BusinessException(4015, "Interest not enough to withdraw!", 400);
        }
        entity.setInterestDate(OffsetDateTime.now());
        userLeaderRepository.save(entity);
        var user = entity.getUser();
        user.setPoint(user.getPoint() + interest);
        userRepository.save(user);
        return Entity2UserLeaderResponse.INSTANCE.map(userLeaderRepository.findById(userLeaderId).get());
    }


    @Transactional
    public UserLeaderResponse withdrawLeader(Long userLeaderId) {
        var entityOptional = userLeaderRepository.findByIdAndStatus(userLeaderId, UserPackageStatus.INVESTING);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Leader package not exists!", 404);
        }
        var entity = entityOptional.get();
        var interest = entity.getCurrentInterest();
        if (entity.getInvestDuration() < entity.getDuration()) {
            throw new BusinessException(4016, "Not enough duration to withdraw!", 400);
        }
        entity.setWithdrawDate(OffsetDateTime.now());
        entity.setInterestDate(OffsetDateTime.now());
        entity.setStatus(UserPackageStatus.COMPLETED);
        userLeaderRepository.save(entity);
        var total = entity.getAmt() + interest;
        var user = entity.getUser();
        user.setPoint(user.getPoint() + total);
        userRepository.save(user);
        return Entity2UserLeaderResponse.INSTANCE.map(userLeaderRepository.findById(userLeaderId).get());
    }

    @Transactional
    public UserPackageResponse withdrawInvest(Long userInvestId) {
        var entityOptional = userPackageRepository.findByIdAndStatus(userInvestId, UserPackageStatus.INVESTING);
        if (entityOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Invest package not exists!", 404);
        }
        var entity = entityOptional.get();
        if (entity.getInvestDuration() < entity.getDuration()) {
            throw new BusinessException(4016, "Not enough duration to withdraw!", 400);
        }
        entity.setWithdrawDate(OffsetDateTime.now());
        entity.setInterestDate(OffsetDateTime.now());
        entity.setStatus(UserPackageStatus.COMPLETED);
        userPackageRepository.save(entity);
        var interest = entity.getCurrentInterest();
        var total = entity.getAmt() + interest;
        var user = entity.getUser();
        user.setPoint(user.getPoint() + total);
        userRepository.save(user);
        var refUser = getLeaderByRefId(user.getRefId());
        if(refUser != null){
            var rateF1 = interest * getRateOfF(1);
            refUser.setPoint(refUser.getPoint() + rateF1);
            userRepository.save(refUser);
        }
        return Entity2UserPackageResponse.INSTANCE.map(userPackageRepository.findById(userInvestId).get());
    }

    private User getUserByPhoneLock(String phone) {
        var userOptional = userRepository.findByPhone(phone);
        if (userOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Account not exists!", 404);
        }
        if (userOptional.get().getIsLockPoint() == Boolean.TRUE) {
            throw new BusinessException(4014, "Account Balance was locked for other transaction!", 400);
        }
        return userOptional.get();
    }

    private User getLeaderByRefId(String ref){
        if(ref == null){
            return null;
        }
        var optional = userRepository.findByCode(ref);
        if (optional.isPresent()){
            var user = optional.get();
            var userLeaders = userLeaderRepository.findAllByUserStatus(user.getId(), UserPackageStatus.INVESTING);
            if(!userLeaders.isEmpty()){
                return user;
            }
        }
        return null;
    }

    private Double getRateOfF(Integer f){
        return multiLevelRateRepository.findByLevel(1).get().getRate();
    }
}

package com.vn.investion.service;

import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.dto.ipackage.*;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.mapper.*;
import com.vn.investion.model.InvestHis;
import com.vn.investion.model.InvestPackage;
import com.vn.investion.model.LeaderPackage;
import com.vn.investion.model.User;
import com.vn.investion.model.define.UserPackageStatus;
import com.vn.investion.repo.*;
import com.vn.investion.utils.Commission;
import com.vn.investion.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final InvestPackageRepository investPackageRepository;
    private final LeaderPackageRepository leaderPackageRepository;
    private final UserPackageRepository userPackageRepository;
    private final UserRepository userRepository;
    private final UserLeaderRepository userLeaderRepository;
    private final MultiLevelRateRepository multiLevelRateRepository;
    private final InterestHisRepository interestHisRepo;
    private final UserService userService;

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
        if (user.getDepositBalance() - entity.getAmt() < 0) {
            throw new BusinessException(4015, "Account Balance not enough!", 400);
        }
        if(entity.getRemainBuy() != null && entity.getRemainBuy() <= 0){
            throw new BusinessException(4017, "The number of packages has run out!", 400);
        }
        if(entity.getUserCanBuy() != null){
            var buy = userPackageRepository.findAllByUserAndLeader(user.getId(), entity.getId());
            if(buy.size() >= entity.getUserCanBuy()){
                throw new BusinessException(4018, "Package limited!", 400);
            }
        }
        if(entity.getRemainBuy() != null){
            entity.setRemainBuy(entity.getRemainBuy() - 1);
            investPackageRepository.saveAndFlush(entity);
        }
        user.setDepositBalance(user.getDepositBalance() - entity.getAmt());
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
        if (user.getDepositBalance() - entity.getAmt() < 0) {
            throw new BusinessException(4015, "Account Balance not enough!", 400);
        }
        if(entity.getRemainBuy() != null && entity.getRemainBuy() <= 0){
            throw new BusinessException(4017, "The number of packages has run out!", 400);
        }
        if(entity.getUserCanBuy() != null){
            var buy = userLeaderRepository.findAllByUserAndLeader(user.getId(), entity.getId());
            if(buy.size() >= entity.getUserCanBuy()){
                throw new BusinessException(4018, "Package limited!", 400);
            }
        }
        if(entity.getRemainBuy() != null){
            entity.setRemainBuy(entity.getRemainBuy() - 1);
            leaderPackageRepository.saveAndFlush(entity);
        }
        user.setDepositBalance(user.getDepositBalance() - entity.getAmt());
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
        return entityList.stream().map(Entity2InvestResponse.INSTANCE::map).toList();
    }

    public List<LeaderPackageResponse> getAllLeader() {
        var entityList = leaderPackageRepository.findAllByIsActiveTrue();
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
    public UserPackageResponse withdrawIntInvest(Long userPackageId, String phone) {
        var entityOptional = userPackageRepository.findById(userPackageId);
        if (entityOptional.isEmpty() || !entityOptional.get().getUser().getPhone().equals(phone)) {
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
        user.setAvailableBalance(user.getAvailableBalance() + interest);
        userRepository.save(user);
        updateF(interest, 0,null, entity.getInvestPackage(), user);
        createInterestHis(null, entity.getInvestPackage(), user, 0, interest, null);
        var res = Entity2UserPackageResponse.INSTANCE.map(userPackageRepository.findById(userPackageId).get());
        res.setInterestWithdraw(interest);
        return res;
    }

    @Transactional
    public UserLeaderResponse withdrawIntLeader(Long userLeaderId, String phone) {
        var entityOptional = userLeaderRepository.findById(userLeaderId);
        if (entityOptional.isEmpty() || !entityOptional.get().getUser().getPhone().equals(phone)) {
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
        user.setAvailableBalance(user.getAvailableBalance() + interest);
        userRepository.save(user);
        createInterestHis(entity.getLeaderPackage(), null, user, 0, interest, null);
        var res = Entity2UserLeaderResponse.INSTANCE.map(userLeaderRepository.findById(userLeaderId).get());
        res.setInterestWithdraw(interest);
        return res;
    }

    @Transactional
    public UserLeaderResponse withdrawLeader(Long userLeaderId, String phone) {
        var entityOptional = userLeaderRepository.findByIdAndStatus(userLeaderId, UserPackageStatus.INVESTING);
        if (entityOptional.isEmpty() || !entityOptional.get().getUser().getPhone().equals(phone)) {
            throw new BusinessException(4004, "Reference Leader package not exists!", 404);
        }
        var entity = entityOptional.get();
        if (entity.getInvestDuration() < entity.getDuration()) {
            throw new BusinessException(4016, "Not enough duration to withdraw!", 400);
        }
        var interest = entity.getCurrentInterest();
        var total = entity.getAmt() + interest;
        var user = entity.getUser();
        entity.setWithdrawDate(OffsetDateTime.now());
        entity.setInterestDate(OffsetDateTime.now());
        entity.setStatus(UserPackageStatus.COMPLETED);
        userLeaderRepository.save(entity);
        user.setAvailableBalance(user.getAvailableBalance() + total);
        userRepository.save(user);
        createInterestHis(entity.getLeaderPackage(), null, user, entity.getAmt(), interest, null);
        var res = Entity2UserLeaderResponse.INSTANCE.map(userLeaderRepository.findById(userLeaderId).get());
        res.setInterestWithdraw(interest);
        return res;
    }

    @Transactional
    public UserPackageResponse withdrawInvest(Long userInvestId, String phone) {
        var entityOptional = userPackageRepository.findByIdAndStatus(userInvestId, UserPackageStatus.INVESTING);
        if (entityOptional.isEmpty() || !entityOptional.get().getUser().getPhone().equals(phone)) {
            throw new BusinessException(4004, "Reference Invest package not exists!", 404);
        }
        var entity = entityOptional.get();
        if (entity.getInvestDuration() < entity.getDuration()) {
            throw new BusinessException(4016, "Not enough duration to withdraw!", 400);
        }
        var interest = entity.getCurrentInterest();
        var total = entity.getAmt() + interest;
        var user = entity.getUser();
        entity.setWithdrawDate(OffsetDateTime.now());
        entity.setInterestDate(OffsetDateTime.now());
        entity.setStatus(UserPackageStatus.COMPLETED);
        userPackageRepository.save(entity);
        user.setAvailableBalance(user.getAvailableBalance() + total);
        userRepository.save(user);
        updateF(interest, 0,null, entity.getInvestPackage(), user);
        createInterestHis(null, entity.getInvestPackage(), user, entity.getAmt(), interest, null);
        var res = Entity2UserPackageResponse.INSTANCE.map(userPackageRepository.findById(userInvestId).get());
        res.setInterestWithdraw(interest);
        return res;
    }

    public List<InterestHisResponse> getIntHisUser(String phone) {
        return interestHisRepo.getInterestHisByPhone(phone).stream().map(Entity2InterestHisResponse.INSTANCE::map).toList();
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

    private User getLeaderByCode(String code) {
        if (code == null) {
            return null;
        }
        var optional = userRepository.findByCode(code);
        if (optional.isPresent()) {
            var user = optional.get();
            var userLeaders = userLeaderRepository.findAllByUserStatus(user.getId(), UserPackageStatus.INVESTING);
            if (!userLeaders.isEmpty()) {
                return user;
            }
        }
        return null;
    }

    private void createInterestHis(LeaderPackage leaderPackage,
                                   InvestPackage investPackage,
                                   User user,
                                   long amt,
                                   long intAmt,
                                   User ref) {
        var intHist = new InvestHis(null, amt, intAmt, user, leaderPackage, investPackage, ref, user.getAvailableBalance());
        interestHisRepo.save(intHist);
    }

    private Double getRateOfF(Integer f) {
        return Commission.rate.get(f);
    }

    private void updateF(long interest, long amt, LeaderPackage leaderPackage, InvestPackage investPackage, User user) {
        //todo update logic + ls for f
        Map<Integer, UserResponse> userLv = userService.getParentHierarchy(user.getPhone());
        userLv.entrySet().forEach(entry -> {
            var userLeader = getLeaderByCode(entry.getValue().getCode());
            if (userLeader != null) {
                var intRate = (long) (interest * getRateOfF(entry.getKey()));
                var amtRate = (long) (amt * getRateOfF(entry.getKey()));
                userLeader.setAvailableBalance(userLeader.getAvailableBalance() + intRate + amtRate);
                userRepository.save(userLeader);
                createInterestHis(leaderPackage, investPackage, userLeader, amtRate, intRate, user);
            }
        });
    }
}

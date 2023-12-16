package com.vn.investion.service;

import com.vn.investion.dto.auth.TeamResponse;
import com.vn.investion.dto.auth.UserBankRequest;
import com.vn.investion.dto.auth.UserBankResponse;
import com.vn.investion.dto.auth.UserResponse;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.mapper.Entity2UserBankResponse;
import com.vn.investion.mapper.Entity2UserResponse;
import com.vn.investion.mapper.UserBankRequest2Entity;
import com.vn.investion.model.User;
import com.vn.investion.model.UserBank;
import com.vn.investion.model.define.Role;
import com.vn.investion.model.define.TransactionType;
import com.vn.investion.model.define.UserPackageStatus;
import com.vn.investion.repo.TransactionHisRepository;
import com.vn.investion.repo.UserBankRepository;
import com.vn.investion.repo.UserLeaderRepository;
import com.vn.investion.repo.UserRepository;
import com.vn.investion.utils.Commission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {
    private final UserBankRepository userBankRepository;
    private final UserRepository userRepository;
    private final UserLeaderRepository userLeaderRepository;
    private final TransactionHisRepository transactionHisRepository;

    @Transactional
    public UserBankResponse createUserBank(UserBankRequest request, String phone) {
        var user = getUserByPhone(phone);
        var userbank = new UserBank(null, user, request.getBank(), request.getNumberAccount(), request.getAccountName());
        userbank = userBankRepository.save(userbank);
        return Entity2UserBankResponse.INSTANCE.map(userbank);
    }

    @Transactional
    public UserBankResponse updateUserBank(Long userBankId, UserBankRequest request, String phone) {
        var userBank = getUserBankById(userBankId);
        assert userBank.getUser().getPhone().equals(phone);
        UserBankRequest2Entity.INSTANCE.mapTo(request, userBank);
        userBank = userBankRepository.save(userBank);
        return Entity2UserBankResponse.INSTANCE.map(userBank);
    }

    @Transactional
    public Boolean delete(Long userBankId, String phone) {
        var userBank = getUserBankById(userBankId);
        assert userBank.getUser().getPhone().equals(phone);
        userBankRepository.delete(userBank);
        return true;
    }

    public List<UserBankResponse> getBankOfUser(String phone) {
        var entityList = userBankRepository.getByPhone(phone);
        return entityList.stream().map(Entity2UserBankResponse.INSTANCE::map).toList();
    }

    public List<UserBankResponse> getBankAdmin() {
        var entityList = userBankRepository.getByRole(Role.ADMIN);
        return entityList.stream().map(Entity2UserBankResponse.INSTANCE::map).toList();
    }

    public Map<Integer, List<UserResponse>> getUserUserHierarchy(String phone) {
        var user = getUserByPhone(phone);
        var resultList = userRepository.findUserHierarchy(user.getCode(), 3);
        var userHierarchyList = new HashMap<Integer, List<UserResponse>>();
        for (Object[] row : resultList) {
            int level = (int) row[2];
            var userLv = new User(
                    (Long) row[3],
                    (String) row[4],
                    (String) row[5],
                    (String) row[6],
                    (String) row[7],
                    (String) row[8],
                    (String) row[9],
                    row[10].equals(Role.USER.name()) ? Role.USER : Role.ADMIN,
                    (Boolean) row[15],
                    (Boolean) row[16],
                    (long) row[17],
                    (long) row[18]
            );
            try {
                ZoneOffset systemZoneOffset = ZoneOffset.systemDefault().getRules().getOffset(java.time.Instant.now());
                userLv.setCreatedAt(((Instant) row[11]).atOffset(systemZoneOffset));
                userLv.setUpdatedAt(((Instant) row[13]).atOffset(systemZoneOffset));
                userLv.setCreatedBy((String) row[12]);
                userLv.setUpdatedBy((String) row[14]);
            } catch (Exception e) {
                System.out.println(e);
            }
            userHierarchyList.computeIfAbsent(level, k -> new ArrayList<>()).add(Entity2UserResponse.INSTANCE.map(userLv));
        }
        return userHierarchyList;
    }

    public Map<Integer, UserResponse> getParentHierarchy(String phone) {
        var user = getUserByPhone(phone);
        var resultList = userRepository.findParentHierarchy(user.getCode(), 3);
        var userHierarchyList = new HashMap<Integer, UserResponse>();
        for (Object[] row : resultList) {
            int level = (int) row[2];
            if (level == 0) {
                continue;
            }
            var userLv = new User(
                    (Long) row[3],
                    (String) row[4],
                    (String) row[5],
                    (String) row[6],
                    (String) row[7],
                    (String) row[8],
                    (String) row[9],
                    row[10].equals(Role.USER.name()) ? Role.USER : Role.ADMIN,
                    (Boolean) row[15],
                    (Boolean) row[16],
                    (long) row[17],
                    (long) row[18]
            );
            try {
                ZoneOffset systemZoneOffset = ZoneOffset.systemDefault().getRules().getOffset(java.time.Instant.now());
                userLv.setCreatedAt(((Timestamp) row[11]).toInstant().atOffset(systemZoneOffset));
                userLv.setUpdatedAt(((Timestamp) row[13]).toInstant().atOffset(systemZoneOffset));
                userLv.setCreatedBy((String) row[12]);
                userLv.setUpdatedBy((String) row[14]);
            } catch (Exception e) {

            }
            userHierarchyList.put(level, Entity2UserResponse.INSTANCE.map(userLv));
        }
        return userHierarchyList;
    }

    public User getUserByPhone(String phone) {
        var userOptional = userRepository.findByPhone(phone);
        if (userOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Account not exists!", 404);
        }
        return userOptional.get();
    }

    public TeamResponse getLeaderTeam(String phone) {
        var user = getLeaderByPhone(phone);
        if (user == null) {
            throw new BusinessException(4018, "Leader package not exists!", 404);
        }
        var userHierarchy = getUserUserHierarchy(phone);
        var totalF1 = userHierarchy.get(1).size();
        var totalMember = 1;
        var userList = new ArrayList<Long>();
        for (Map.Entry<Integer, List<UserResponse>> entry: userHierarchy.entrySet()){
            totalMember += entry.getValue().size();
            userList.addAll(entry.getValue().stream().map(UserResponse::getId).toList());
        }
        userList.add(user.getId());
        var now = OffsetDateTime.now();
        var totalDeposit = transactionHisRepository.getTotalAmountByUserIdsAndMonth(
                userList,
                now.getYear(),
                now.getMonthValue(),
                TransactionType.DEPOSIT.ordinal()
                );
        var giftLevel = Commission.getGiftLevel(totalDeposit, totalMember, totalF1);
        var userRes = Entity2UserResponse.INSTANCE.map(user);
        return new TeamResponse(userRes, userHierarchy, totalMember, totalF1, totalDeposit, giftLevel);
    }

    private UserBank getUserBankById(Long id) {
        var userOptional = userBankRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference user bank not exists!", 404);
        }
        return userOptional.get();
    }

    public User getLeaderByPhone(String phone) {
        var optional = userRepository.findByPhone(phone);
        if (optional.isPresent()) {
            var user = optional.get();
            var userLeaders = userLeaderRepository.findAllByUserStatus(user.getId(), UserPackageStatus.INVESTING);
            if (!userLeaders.isEmpty()) {
                return user;
            }
        }
        return null;
    }
}

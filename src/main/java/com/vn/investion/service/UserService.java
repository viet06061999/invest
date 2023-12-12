package com.vn.investion.service;

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
import com.vn.investion.repo.UserBankRepository;
import com.vn.investion.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
        var resultList = userRepository.findUserHierarchy(user.getCode(), 10);
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
                    row[10].equals(Role.USER.name())? Role.USER: Role.ADMIN,
                    (Boolean) row[15],
                    (Boolean) row[16],
                    (Double) row[17]
                    );
            try {
                ZoneOffset systemZoneOffset = ZoneOffset.systemDefault().getRules().getOffset(java.time.Instant.now());
                userLv.setCreatedAt(((Timestamp) row[11]).toInstant().atOffset(systemZoneOffset));
                userLv.setUpdatedAt(((Timestamp) row[13]).toInstant().atOffset(systemZoneOffset));
                userLv.setCreatedBy((String) row[12]);
                userLv.setUpdatedBy((String) row[14]);
            }catch (Exception e){

            }
            userHierarchyList.computeIfAbsent(level, k -> new ArrayList<>()).add(Entity2UserResponse.INSTANCE.map(userLv));
        }
        return userHierarchyList;
//        return entityList.stream().map(Entity2UserBankResponse.INSTANCE::map).toList();
    }

    private User getUserByPhone(String phone) {
        var userOptional = userRepository.findByPhone(phone);
        if (userOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Account not exists!", 404);
        }
        return userOptional.get();
    }

    private UserBank getUserBankById(Long id) {
        var userOptional = userBankRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference user bank not exists!", 404);
        }
        return userOptional.get();
    }
}

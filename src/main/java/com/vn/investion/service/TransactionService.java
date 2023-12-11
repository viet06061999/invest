package com.vn.investion.service;

import com.vn.investion.dto.transaction.TransactionRequest;
import com.vn.investion.dto.transaction.TransactionResponse;
import com.vn.investion.dto.transaction.TransactionUpdateStatusRequest;
import com.vn.investion.exception.BusinessException;
import com.vn.investion.feign.TeleClient;
import com.vn.investion.feign.model.SendMessageRequest;
import com.vn.investion.mapper.Entity2TransactionResponse;
import com.vn.investion.mapper.TransactionRequest2Entity;
import com.vn.investion.model.TransactionHis;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
import com.vn.investion.repo.TransactionHisRepository;
import com.vn.investion.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionHisRepository transactionHisRepository;
    private final TeleClient teleClient;

    @Value("${telegram.bot.message}")
    private String message;
    @Value("${telegram.bot.chat_id}")
    private String chatId;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, String phone) {
        var userOptional = userRepository.findByPhone(phone);
        if (userOptional.isEmpty()) {
            throw new BusinessException(4004, "Reference Account not exists!", 404);
        }
        var user = userOptional.get();
        if (user.getIsLockPoint() == Boolean.TRUE) {
            throw new BusinessException(4014, "Account Balance was locked for other transaction!", 400);
        }
        if (request.getTransactionType().equals(TransactionType.WITHDRAW) && user.getBalance() - request.getAmount() < 0) {
            throw new BusinessException(4015, "Account Balance not enough!", 400);
        }
        var transaction = TransactionRequest2Entity.INSTANCE.map(request);
        transaction.setUser(user);
        transaction.setRemainBalance(user.getBalance());
        var entity = Entity2TransactionResponse.INSTANCE.map(transactionHisRepository.save(transaction));
        if (request.getTransactionType().equals(TransactionType.WITHDRAW)) {
            user.setBalance(user.getBalance() - request.getAmount());
        }
//        user.setIsLockPoint(true);
        userRepository.save(user);
        var userName = user.getFirstname() + " " + user.getLastname() + "(" + user.getPhone() + ")";
        var function = "Nạp tiền";
        if (request.getTransactionType().equals(TransactionType.WITHDRAW)) {
            function = "Rút tiền";
        }
        sendMessage(request, userName, function);
        return entity;
    }

    @Transactional
    public TransactionResponse updateStatusTransaction(TransactionUpdateStatusRequest request, Long id) {
        var transactionOptional = transactionHisRepository.findByIdAndStatus(id, TransactionStatus.PENDING);
        if (transactionOptional.isEmpty()) {
            throw new BusinessException(4013, "Reference Transaction not exists!", 404);
        }
        var transaction = transactionOptional.get();
        var user = transaction.getUser();
        transaction.setStatus(Enum.valueOf(TransactionStatus.class, request.getStatus()));
        if ((transaction.getStatus().equals(TransactionStatus.APPROVE)
                && transaction.getTransactionType().equals(TransactionType.DEPOSIT))
                || (transaction.getStatus().equals(TransactionStatus.CANCEL)
                && transaction.getTransactionType().equals(TransactionType.WITHDRAW))) {
            var balance = 0.0;
            balance += transaction.getAmount();
            user.setBalance(user.getBalance() + balance);
        }
        transaction.setRemainBalance(user.getBalance());
        transaction = transactionHisRepository.save(transaction);
//        user.setIsLockPoint(false);
        userRepository.save(user);
        return Entity2TransactionResponse.INSTANCE.map(transactionHisRepository.save(transaction));
    }

    @Transactional
    public TransactionResponse updateTransaction(TransactionRequest request, Long id) {
        var transactionOptional = transactionHisRepository.findByIdAndStatus(id, TransactionStatus.PENDING);
        if (transactionOptional.isEmpty()) {
            throw new BusinessException(4013, "Reference Transaction not exists!", 404);
        }
        var transaction = transactionOptional.get();
        var user = transaction.getUser();
        if (request.getTransactionType().equals(TransactionType.WITHDRAW) && user.getBalance() - request.getAmount() < 0) {
            throw new BusinessException(4015, "Account Balance not enough!", 400);
        }
        var copy = transaction.copy();
        TransactionRequest2Entity.INSTANCE.mapTo(request, transaction);
        if (copy.equals(transaction)) {
            throw new BusinessException(4016, "Data not change!", 400);
        }
        transaction.setRemainBalance(user.getBalance());
        transaction = transactionHisRepository.save(transaction);
        user.setIsLockPoint(true);
        userRepository.save(user);
        var userName = user.getFirstname() + " " + user.getLastname() + "(" + user.getPhone() + ")";
        var function = "Nạp tiền(Đã chỉnh sửa)";
        if (request.getTransactionType().equals(TransactionType.WITHDRAW)) {
            function = "Rút tiền(Đã chỉnh sửa)";
        }
        sendMessage(request, userName, function);
        return Entity2TransactionResponse.INSTANCE.map(transactionHisRepository.save(transaction));
    }

    public List<TransactionResponse> getAll(Specification<TransactionHis> specification, Pageable pageable) {
        var entityList = transactionHisRepository.findAll(specification, pageable);
        return entityList.stream().map(Entity2TransactionResponse.INSTANCE::map).toList();
    }

    public List<TransactionResponse> getByUser(String phone) {
        var entityList = transactionHisRepository.findAllByUserPhone(phone);
        return entityList.stream().map(Entity2TransactionResponse.INSTANCE::map).toList();
    }

    private void sendMessage(TransactionRequest request, String user, String function) {
        var teleMessage = message.formatted(
                function,
                user,
                BigDecimal.valueOf(request.getAmount()).toPlainString(),
                request.getBank(),
                request.getNumberAccount(),
                request.getDescription());
        var messageRequest = new SendMessageRequest();
        messageRequest.setText(teleMessage);
        messageRequest.setChatId(chatId);
        var res = teleClient.sendMessage(messageRequest);
    }
}

package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.CallsEntity;
import com.example.backend.Exceptions.RecordNotFoundException;
import com.example.backend.Repository.CallsRepository;

@Service
public class CallsService {

    @Autowired
    CallsRepository repository;

    public List<CallsEntity> getAllCalls() {
        List<CallsEntity> result = (List<CallsEntity>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<CallsEntity>();
        }
    }

    public CallsEntity getCallsById(BigDecimal id) throws RecordNotFoundException {
        Optional<CallsEntity> calls = repository.findById(id);
        if (calls.isPresent()) {
            return calls.get();
        } else {
            throw new RecordNotFoundException("No calls record exist for given id");
        }
    }

    public CallsEntity createCalls(CallsEntity entity) throws RecordNotFoundException {
        Optional<CallsEntity> calls = repository.findById(entity.getId());
        if (calls.isPresent()) {
            throw new RecordNotFoundException("Calls record exist for given id");
        } else {
            return repository.save(entity);
        }
    }

    public CallsEntity updateCalls(BigDecimal id, CallsEntity entity) throws RecordNotFoundException {
        CallsEntity existingCalls = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No calls record exist for given id"));

        if (entity.getC_code() != null) {
            existingCalls.setC_code(entity.getC_code());
        }
        if (entity.getE_code() != null) {
            existingCalls.setE_code(entity.getE_code());
        }
        if (entity.getReq_date() != null) {
            existingCalls.setReq_date(entity.getReq_date());
        }
        if (entity.getReq_time() != null) {
            existingCalls.setReq_time(entity.getReq_time());
        }
        if (entity.getRp_code() != null) {
            existingCalls.setRp_code(entity.getRp_code());
        }
        if (entity.getResp_date() != null) {
            existingCalls.setResp_date(entity.getResp_date());
        }
        if (entity.getResp_time() != null) {
            existingCalls.setResp_time(entity.getResp_time());
        }
        if (entity.getTime_arrive() != null) {
            existingCalls.setTime_arrive(entity.getTime_arrive());
        }
        if (entity.getTime_left() != null) {
            existingCalls.setTime_left(entity.getTime_left());
        }
        if (entity.getSusp_flag() != null) {
            existingCalls.setSusp_flag(entity.getSusp_flag());
        }
        if (entity.getF_cat() != null) {
            existingCalls.setF_cat(entity.getF_cat());
        }
        if (entity.getInv_no() != null) {
            existingCalls.setInv_no(entity.getInv_no());
        }
        if (entity.getRem1() != null) {
            existingCalls.setRem1(entity.getRem1());
        }
        if (entity.getRem2() != null) {
            existingCalls.setRem2(entity.getRem2());
        }
        if (entity.getRem3() != null) {
            existingCalls.setRem3(entity.getRem3());
        }
        if (entity.getRem4() != null) {
            existingCalls.setRem4(entity.getRem4());
        }
        if (entity.getAct_rem1() != null) {
            existingCalls.setAct_rem1(entity.getAct_rem1());
        }
        if (entity.getAct_rem2() != null) {
            existingCalls.setAct_rem2(entity.getAct_rem2());
        }
        if (entity.getAct_rem3() != null) {
            existingCalls.setAct_rem3(entity.getAct_rem3());
        }
        if (entity.getAct_rem4() != null) {
            existingCalls.setAct_rem4(entity.getAct_rem4());
        }

        return repository.save(existingCalls);
    }

    public void deleteCallsById(BigDecimal id) throws RecordNotFoundException {
        Optional<CallsEntity> calls = repository.findById(id);

        if (calls.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No calls record exist for given id");
        }
    }
}

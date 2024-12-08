package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.ResultDTO;
import com.example.toeicwebsite.data.dto.ResultInputDTO;
import com.example.toeicwebsite.data.entity.Result;
import com.example.toeicwebsite.data.entity.User;
import com.example.toeicwebsite.data.repository.ResultRepository;
import com.example.toeicwebsite.data.repository.UserRepository;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Override
    public MessageResponse saveResult(ResultInputDTO resultInputDTO) {
        Date currentDate = new Date();
        User userCurrent = getNguoiDungByToken();
        String status = "Hoan thanh";

        Result result = new Result();
//        result.setId(resultDTO.getId());
        result.setStatus(status);
        result.setTotalMark(resultInputDTO.getTotalMark());
        result.setCreateAt(currentDate);
        result.setUser(userCurrent);
        result.setStructureId(resultInputDTO.getStructureId());

        resultRepository.save(result);

        return new MessageResponse(HttpServletResponse.SC_OK, "luu ket qua thanh cong");
    }

    @Override
    public PaginationDTO filterResult(String keyword, int pageNumber, int pageSize) {
        User userCurrent = getNguoiDungByToken();



        Page<Result> page = resultRepository.filterResult(keyword, userCurrent.getId(), PageRequest.of(pageNumber, pageSize));
        List<ResultDTO> list = new ArrayList<>();

        for (Result result: page.getContent()) {

            ResultDTO resultDTO = new ResultDTO();

            resultDTO.setId(result.getId());
            resultDTO.setStatus(result.getStatus());
            resultDTO.setTotalMark(result.getTotalMark());
            resultDTO.setCreateAt(result.getCreateAt());
            resultDTO.setStructureId(result.getStructureId());

            list.add(resultDTO);
        }
        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public long countResult() {
        return resultRepository.countResult();
    }

    public User getNguoiDungByToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEmail = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "Tai khoan nay khong ton tai")));

        return userRepository.findById(userEmail.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "nguoi dung nay khong ton tai"))
        );
    }
}

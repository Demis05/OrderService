package com.exadel.fedorov.orders.service;

import com.exadel.fedorov.orders.domain.Membership;
import com.exadel.fedorov.orders.dto.MembershipDTO;
import com.exadel.fedorov.orders.repository.MembershipDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    @Autowired
    private MembershipDAO membershipDAO;

    @Autowired
    private ModelMapper modelMapper;

    public void update(Long id, LocalDateTime endDate) {
        membershipDAO.update(id, endDate);
    }

    public void delete(Long id) {
        membershipDAO.delete(id);
    }

    public void create(MembershipDTO membershipDTO) {
        membershipDAO.create(modelMapper.map(membershipDTO, Membership.class));
    }

    public Optional<MembershipDTO> findCurrent(Long clientId) {
        Membership membership = membershipDAO.findCurrent(clientId);
        MembershipDTO membershipDTO = modelMapper.map(membership, MembershipDTO.class);
        membershipDTO.setStartDate(membership.getStartDate().toLocalDateTime());
        membershipDTO.setEndDate(membership.getEndDate().toLocalDateTime());
        return Optional.of(membershipDTO);
    }

    public List<MembershipDTO> findAll(Long clientId) {
        return membershipDAO.findAll(clientId).stream()
                .map(this::getMembershipMembershipDTOFunction)
                .collect(Collectors.toList());
    }

    private MembershipDTO getMembershipMembershipDTOFunction(Membership membership) {
        MembershipDTO membershipDTO = modelMapper.map(membership, MembershipDTO.class);
        membershipDTO.setStartDate(membership.getStartDate().toLocalDateTime());
        membershipDTO.setEndDate(membership.getEndDate().toLocalDateTime());
        return membershipDTO;
    }

}
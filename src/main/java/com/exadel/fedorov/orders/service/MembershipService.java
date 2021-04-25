package com.exadel.fedorov.orders.service;

import com.exadel.fedorov.orders.domain.Membership;
import com.exadel.fedorov.orders.dto.MembershipDTO;
import com.exadel.fedorov.orders.repository.MembershipDAO;
import org.modelmapper.ModelMapper;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    @Autowired
    private MembershipDAO membershipDAO;

    @Autowired
    private ModelMapper modelMapper;

    public void create(MembershipDTO membershipDTO) throws SQLException {
        membershipDAO.create(convertDtoToMembership(membershipDTO));
    }

    public Optional<MembershipDTO> findCurrent(Long clientId) {
        Membership membership = membershipDAO.findCurrent(clientId);
        Optional<Membership> optional = Optional.ofNullable(membership);
        return optional.map(value -> convertMembershipToDto(membership));
    }

    public List<MembershipDTO> findAll(Long clientId) {
        return membershipDAO.findAll(clientId).stream()
                .map(this::convertMembershipToDto)
                .collect(Collectors.toList());
    }

    public void update(Long id, LocalDateTime endDate) {
        membershipDAO.update(id, endDate);
    }

    public void delete(Long id) {
        membershipDAO.delete(id);
    }

    private MembershipDTO convertMembershipToDto(Membership membership) {
        MembershipDTO membershipDTO = modelMapper.map(membership, MembershipDTO.class);
        membershipDTO.setStartDate(membership.getStartDate().toLocalDateTime().toString());
        membershipDTO.setEndDate(membership.getEndDate().toLocalDateTime().toString());
        return membershipDTO;
    }

    private Membership convertDtoToMembership(MembershipDTO dto) throws SQLException {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDateTime startTime = LocalDateTime.parse(dto.getStartDate(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(dto.getEndDate(), formatter);

        return new Membership(0L, dto.getTitle(),
                new PGInterval(dto.getValidity()),
                Timestamp.valueOf(startTime),
                Timestamp.valueOf(endTime),
                dto.getDiscount(),
                dto.getClientId());
    }

}
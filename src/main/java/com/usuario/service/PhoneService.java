package com.usuario.service;


import com.usuario.converter.PhoneConverter;
import com.usuario.dto.phone.PhoneRequestDTO;
import com.usuario.dto.phone.PhoneResponseDTO;
import com.usuario.entity.Phone;
import com.usuario.entity.User;
import com.usuario.exceptions.AccessDeniedException;
import com.usuario.exceptions.ResourceNotFoundException;
import com.usuario.repository.PhoneRepository;
import com.usuario.repository.UserRepository;
import com.usuario.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final PhoneConverter phoneConverter;
    private final JwtUtil jwtUtil;

    @Transactional
    public PhoneResponseDTO createPhone(String token, PhoneRequestDTO phoneRequestDTO) {
        User user = getAuthenticatedUser(token);
        Phone phone = phoneConverter.toPhoneEntity(phoneRequestDTO);
        phone.setUser(user);
        Phone savedPhone = phoneRepository.save(phone);
        return phoneConverter.toPhoneDTO(savedPhone);
    }

    @Transactional
    public PhoneResponseDTO updatePhone(Long phoneId, String token, PhoneRequestDTO phoneRequestDTO) {
        User user = getAuthenticatedUser(token);
        Phone phone = findPhoneById(phoneId);

        validatePhoneOwner(phone, user);

        phone.setNumber(phoneRequestDTO.getNumber() != null ? phoneRequestDTO.getNumber() : phone.getNumber());
        phone.setType(phoneRequestDTO.getType() != null ? phoneRequestDTO.getType() : phone.getType());

        return phoneConverter.toPhoneDTO(phoneRepository.save(phone));
    }

    @Transactional
    public void deletePhone(Long phoneId, String token) {
        User user = getAuthenticatedUser(token);
        Phone phone = findPhoneById(phoneId);
        validatePhoneOwner(phone, user);
        phoneRepository.delete(phone);
    }

    //MÉTODOS PRIVADOS
    private User getAuthenticatedUser(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Phone findPhoneById(Long phoneId) {
        return phoneRepository.findById(phoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Phone number not found."));
    }

    private void validatePhoneOwner(Phone phone, User user) {
        if (!phone.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to change this phone number..");
        }
    }
}

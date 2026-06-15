package com.rakesh.razorpay.merchant.service.impl;

import com.rakesh.razorpay.common.enums.MerchantStatus;
import com.rakesh.razorpay.common.enums.UserRole;
import com.rakesh.razorpay.common.exceptions.DuplicateResourceException;
import com.rakesh.razorpay.merchant.dto.request.MerchantSignUpRequest;
import com.rakesh.razorpay.merchant.dto.response.MerchantResponse;
import com.rakesh.razorpay.merchant.entity.AppUser;
import com.rakesh.razorpay.merchant.entity.Merchant;
import com.rakesh.razorpay.merchant.repository.AppUserRepository;
import com.rakesh.razorpay.merchant.repository.MerchantRepository;
import com.rakesh.razorpay.merchant.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public MerchantResponse signUp(MerchantSignUpRequest signUpRequest) {
        if(merchantRepository.existsByEmail(signUpRequest.email())){
            log.error("Merchant with email {} already exists", signUpRequest.email());
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL","Merchant with email " + signUpRequest.email() + " already exists");
        }

        Merchant merchant = Merchant.builder()
                .businessName(signUpRequest.businessName())
                .businessType(signUpRequest.businessType())
                .name(signUpRequest.name())
                .email(signUpRequest.email())
                .merchantStatus(MerchantStatus.PENDING_KYC)
                .build();
        merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(signUpRequest.email())
                .passwordHash(signUpRequest.password()) //TODO: Encrypt using Bcrypt
                .role(UserRole.OWNER)
                .merchant(merchant)
                .build();
        appUserRepository.save(appUser);

        return new MerchantResponse(merchant.getId(), merchant.getName(), merchant.getEmail(),merchant.getBusinessName(), merchant.getBusinessType(), merchant.getMerchantStatus());
    }
}

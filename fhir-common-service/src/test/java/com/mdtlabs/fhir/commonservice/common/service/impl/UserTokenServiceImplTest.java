package com.mdtlabs.fhir.commonservice.common.service.impl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;
import com.mdtlabs.fhir.commonservice.common.repository.UserTokenRepository;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * <p>
 * UserTokenServiceImplTest class has the test methods for the UserTokenServiceImpl class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserTokenServiceImplTest {

    @InjectMocks
    private UserTokenServiceImpl userTokenService;

    @Mock
    private UserTokenRepository userTokenRepository;

    @Test
    void saveUserTokenTest() {
        //given
        UserToken userToken = new UserToken();
        userToken.setAuthToken(Constants.TOKEN);
        userToken.setUserId(1L);
        userToken.setActive(Boolean.TRUE);
        userTokenService.saveUserToken(Constants.TOKEN, Constants.USER_DATA, 1L);
        //then
        verify(userTokenRepository, atLeastOnce()).save(userToken);

    }

    @Test
    void deleteUserTokenByTokenTest() {
        //when
        userTokenService.deleteUserTokenByToken(Constants.TOKEN);
        //then
        verify(userTokenRepository, atLeastOnce()).deleteByAuthToken(Constants.TOKEN);
    }

    @Test
    void deleteUserTokenByUserName() {
        //when
        userTokenService.deleteUserTokenByUserName(Constants.NAME, Constants.LONG_ONE);
        //then
        verify(userTokenRepository, atLeastOnce()).deleteByUserId(Constants.LONG_ONE);
    }

    @Test
    void findByAuthTokenTest() {
        //when
        userTokenService.findByAuthToken(Constants.TOKEN);
        //then
        verify(userTokenRepository, atLeastOnce()).findByAuthToken(Constants.TOKEN);
    }

    @Test
    void deleteInvalidUserTokensByUserId() {
        List<UserToken> userTokens = new ArrayList<>();
        List<String> tokenList = new ArrayList<>();
        userTokens.add(TestDataProvider.getUserToken());
        //when
        when(userTokenRepository.findByUserIdAndIsActiveTrue(Constants.LONG_ONE)).thenReturn(userTokens);
        userTokenService.deleteInvalidUserTokensByUserId(Constants.LONG_ONE);
        verify(userTokenRepository, atLeastOnce()).deleteByAuthTokens(tokenList);


    }
    @Test
    void deleteValidUserTokensByUserId() {
        //givrn
        List<UserToken> userTokens = new ArrayList<>();
        List<String> tokenList = new ArrayList<>();
        tokenList.add(Constants.TOKEN);
        UserToken userToken = TestDataProvider.getUserToken();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, -40);
        Date fortyMinBefore = calendar.getTime();
        userToken.setLastSessionTime(fortyMinBefore);
        userTokens.add(userToken);
        //when
        when(userTokenRepository.findByUserIdAndIsActiveTrue(Constants.LONG_ONE)).thenReturn(userTokens);
        userTokenService.deleteInvalidUserTokensByUserId(Constants.LONG_ONE);
        //then
        verify(userTokenRepository, atLeastOnce()).deleteByAuthTokens(tokenList);
    }
}

import org.junit.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;

public class AuthenticateUserTest {
    private UserInfo userInfo;
    private UserDao userDao = mock(UserDao.class);
    private UserInfo storedUserInfo;
    private AuthenticateUserService authUserService = new AuthenticateUserService(userDao);
    
    @Before
    public void setup() {
        userInfo = new UserInfo("admin", "1234");
        storedUserInfo = new UserInfo("admin", "1234");
        when(userDao.getUser_byName()).thenReturn(storedUserInfo);
    }

    @After
    public void tearDown() {
        userDao = null;
        userInfo = null;
        storedUserInfo = null;
        authUserService = null;
    }

    @Test
    public void testAuthUser() {
        boolean result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao).getUser_byName();
        Assert.assertTrue(result);
    }

    @Test
    public void testAuthUserFailure() {
        userInfo = new UserInfo("admin123", "1234");

        boolean result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao).getUser_byName();
        Assert.assertFalse(result);

        userInfo = new UserInfo("admin", "123445");
        result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao, times(2)).getUser_byName();
        Assert.assertFalse(result);
    }

    @Test
    public void testAuthUserInfoNull() {
        userInfo = null;
        boolean result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao).getUser_byName();
        Assert.assertFalse(result);
    }

    @Test
    public void testAuthUserWrongPassword() {
        userInfo = new UserInfo("admin", "1");
        boolean result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao).getUser_byName();
        Assert.assertFalse(result);
    }

    //Case 6: Username is not case sensitive.
    @Test
    public void testAuthUserUserCaseSensitive() {
        userInfo = new UserInfo("ADMIN", "1234");
        boolean result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao).getUser_byName();
        Assert.assertTrue(result);
    }

    //Case 5: Empty Name should not be allowed.
    @Test
    public void testAuthUserEmptySpaces() {
        userInfo = new UserInfo("     ", "1  234");
        boolean result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao).getUser_byName();
        Assert.assertFalse(result);

        userInfo = new UserInfo(" admin ", " 1234 ");
        result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao, times(2)).getUser_byName();
        Assert.assertTrue(result);
    }

    //Case 7: password should be case sensitive.
    @Test
    public void testAuthUserPasswordCaseSensitive() {

        userInfo = new UserInfo("admin", "AdMiN123");
        storedUserInfo = new UserInfo("admin", "AdMiN123");
        when(userDao.getUser_byName()).thenReturn(storedUserInfo);

        boolean result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao).getUser_byName();
        Assert.assertTrue(result);

        userInfo = new UserInfo("admin", "admin123");

        result = authUserService.authUserInfo(userInfo);
        Mockito.verify(userDao, times(2)).getUser_byName();
        Assert.assertFalse(result);
    }
}

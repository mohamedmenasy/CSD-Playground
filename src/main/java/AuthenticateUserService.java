public class AuthenticateUserService {
    UserDao userDao;

    public AuthenticateUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean authUserInfo(UserInfo userInfo) {
        UserInfo userDaoInfo = userDao.getUser_byName();
        if (userInfo != null) {
                return userDaoInfo.getUserName().toLowerCase().trim().equals(userInfo.getUserName().toLowerCase().trim()) &&
                        (userDaoInfo.getPassword().trim().equals(userInfo.getPassword().trim()));
            }

        return false;
    }

}

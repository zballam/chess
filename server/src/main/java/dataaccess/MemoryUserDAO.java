package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
    final private Collection<UserData> userDataList = new ArrayList<>();

    public void clear() throws DataAccessException {
        this.userDataList.clear();
    }

    public void createUser(UserData user) throws DataAccessException {
        String hashedPW = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        this.userDataList.add(new UserData(user.username(),hashedPW,user.email()));
    }

    public UserData getUser(String username) throws DataAccessException {
        for (UserData data : userDataList) {
            if (username.equals(data.username())) { //Objects.equals(data.username(), username) also works
                return data;
            }
        }
        return null;
    }
}

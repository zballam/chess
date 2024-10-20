package dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO{
    final private Collection<UserData> userDataList = new ArrayList<>();

//    public Collection<UserData> getUserDataList() {
//        return userDataList;
//    }

    public void clear() throws DataAccessException {
        this.userDataList.clear();
    }

    public void createUser(UserData user) throws DataAccessException {
        this.userDataList.add(user);
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

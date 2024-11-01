package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO{
    Collection<AuthData> authDataList = new ArrayList<>();

    public void clear() throws DataAccessException {
        this.authDataList.clear();
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        for (AuthData data : this.authDataList) {
            if (data.authToken() == authData.authToken()) {
                throw new DataAccessException("AuthToken already exists");
            }
        }
        this.authDataList.add(authData);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        for (AuthData data : this.authDataList) {
            if (data.authToken().equals(authToken)) { // Make sure this part works when testing
                return data;
            }
        }
        throw new DataAccessException("AuthToken doesn't exist");
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        for (AuthData data : this.authDataList) {
            if (data.authToken().equals(authToken)) { // Make sure this part works when testing
                this.authDataList.remove(data);
                return;
            }
        }
        throw new DataAccessException("No item to delete");
    }
}

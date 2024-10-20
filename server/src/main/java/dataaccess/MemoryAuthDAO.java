package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MemoryAuthDAO implements AuthDAO{
    Collection<AuthData> authDataList = new ArrayList<>();

    public void clear() throws DataAccessException {
        this.authDataList.clear();
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        this.authDataList.add(authData);
    }

    /**
     * @return Returns authToken or null if not found
     */
    public String getAuth(String authToken) throws DataAccessException {
        for (AuthData data : this.authDataList) {
            if (data.authToken().equals(authToken)) { // Make sure this part works when testing
                return data.authToken();
            }
        }
        return null;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        for (AuthData data : this.authDataList) {
            if (data.authToken().equals(authToken)) { // Make sure this part works when testing
                this.authDataList.remove(data);
            }
        }
    }
}

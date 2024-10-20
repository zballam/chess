package dataaccess;

import model.AuthData;

public interface AuthDAO {
    // clear Method
    void clear() throws DataAccessException;

    // createAuth Method
    void createAuth(AuthData authData) throws DataAccessException;

    // getAuthToken Method
    String getAuthToken(String authToken) throws DataAccessException;

    // getAuth Method
    AuthData getAuth(String authToken) throws DataAccessException;

    // deleteAuth Method
    void deleteAuth(String authToken) throws DataAccessException;
}

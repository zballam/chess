package dataaccess;

public interface AuthDAO {
    // clear Method
    void clear() throws DataAccessException;

    // createAuth Method
    void createAuth() throws DataAccessException;

    // getAuth Method
    String getAuth() throws DataAccessException;

    // deleteAuth Method
    void deleteAuth() throws DataAccessException;
}

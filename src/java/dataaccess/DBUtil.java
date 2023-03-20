package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Swift
 */
public class DBUtil {

    private static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("UserPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}

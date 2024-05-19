package application;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class ServerApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public ServerApplication() {
        singletons.add(new ChessResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}

package org.springframework.core.io;

import java.io.IOException;

public interface ResourceLoader {

    Resource getResource(String location);
}

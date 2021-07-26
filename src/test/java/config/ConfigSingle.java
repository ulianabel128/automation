package config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigSingle {
    public final static ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
}

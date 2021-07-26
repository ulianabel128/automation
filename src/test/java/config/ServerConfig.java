package config;

import org.aeonbits.owner.Config;

public interface ServerConfig extends Config {
    @Key("base.uri")
    @DefaultValue("https://pp86.hostco.ru")
    String getBaseUriProperties();

}

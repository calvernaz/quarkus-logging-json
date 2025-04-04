package io.quarkiverse.loggingjson.providers;

import java.util.Optional;
import java.util.logging.Level;

import org.jboss.logmanager.ExtLogRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.loggingjson.config.Config;

public class LoggerClassNameJsonProviderJsonbTest extends JsonProviderBaseTest {
    @Override
    protected Type type() {
        return Type.JSONB;
    }

    @Test
    void testDefaultConfig() throws Exception {
        final Config.FieldConfig config = new Config.FieldConfig() {
            @Override
            public Optional<String> fieldName() {
                return Optional.empty();
            }

            @Override
            public Optional<Boolean> enabled() {
                return Optional.empty();
            }
        };
        final LoggerClassNameJsonProvider provider = new LoggerClassNameJsonProvider(config);

        final JsonNode result = getResultAsJsonNode(provider,
                new ExtLogRecord(Level.ALL, "", "LoggerClassNameJsonProviderTest"));

        String loggerClassName = result.findValue("loggerClassName").asText();
        Assertions.assertNotNull(loggerClassName);
        Assertions.assertFalse(loggerClassName.isEmpty());
        Assertions.assertEquals("LoggerClassNameJsonProviderTest", loggerClassName);
        Assertions.assertTrue(provider.isEnabled());
    }

    @Test
    void testCustomConfig() throws Exception {
        final var config = new Config.FieldConfig() {
            private Optional<Boolean> enabled = Optional.of(false);

            @Override
            public Optional<String> fieldName() {
                return Optional.of("loggerClass");
            }

            @Override
            public Optional<Boolean> enabled() {
                return enabled;
            }
        };
        final LoggerClassNameJsonProvider provider = new LoggerClassNameJsonProvider(config);

        final JsonNode result = getResultAsJsonNode(provider,
                new ExtLogRecord(Level.ALL, "", "LoggerClassNameJsonProviderTest"));

        String loggerClass = result.findValue("loggerClass").asText();
        Assertions.assertNotNull(loggerClass);
        Assertions.assertFalse(loggerClass.isEmpty());
        Assertions.assertEquals("LoggerClassNameJsonProviderTest", loggerClass);
        Assertions.assertFalse(provider.isEnabled());

        config.enabled = Optional.of(true);
        Assertions.assertTrue(new LoggerClassNameJsonProvider(config).isEnabled());
    }
}

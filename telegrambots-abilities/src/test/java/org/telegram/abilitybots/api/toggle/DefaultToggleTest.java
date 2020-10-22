package org.telegram.abilitybots.api.toggle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.abilitybots.api.bot.DefaultBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;

import java.io.IOException;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.telegram.abilitybots.api.bot.DefaultAbilities.*;
import static org.telegram.abilitybots.api.db.MapDBContext.offlineInstance;

class DefaultToggleTest {
    private DBContext db;
    private AbilityToggle toggle;
    private DefaultBot defaultBot;

    @BeforeEach
    void setUp() {
        db = offlineInstance("db");
    }

    @AfterEach
    void tearDown() throws IOException {
        db.clear();
        db.close();
    }

    @Test
    public void claimsEveryAbilityIsOn() {
        Ability random = DefaultBot.getDefaultBuilder()
            .name("randomsomethingrandom").build();
        toggle = new DefaultToggle();
        defaultBot = new DefaultBot(EMPTY, EMPTY, db, toggle);
        defaultBot.onRegister();

        assertFalse(toggle.isOff(random));
    }

    @Test
    public void passedSameAbilityRefOnProcess() {
        Ability random = DefaultBot.getDefaultBuilder()
            .name("randomsomethingrandom").build();
        toggle = new DefaultToggle();
        defaultBot = new DefaultBot(EMPTY, EMPTY, db, toggle);
        defaultBot.onRegister();

        assertSame(random, toggle.processAbility(random), "Toggle returned a different ability");
    }

    @Test
    public void allAbilitiesAreRegistered() {
        toggle = new DefaultToggle();
        defaultBot = new DefaultBot(EMPTY, EMPTY, db, toggle);
        defaultBot.onRegister();
        Set<String> defaultNames = newHashSet(
            CLAIM, BAN, UNBAN,
            PROMOTE, DEMOTE, RECOVER,
            BACKUP, REPORT, COMMANDS);

        assertTrue(defaultBot.abilities().keySet().containsAll(defaultNames), "Toggle returned a different ability");
    }
}
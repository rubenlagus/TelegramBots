package org.telegram.abilitybots.api.objects;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AbilityBuilderFactory {
    public static AbilityBuilder builder() {
        return new AbilityBuilder();
    }

    public static class AbilityBuilder {
        private String name;
        private String info;
        private Privacy privacy;
        private Locality locality;
        private int argNum;
        private boolean statsEnabled;
        private Consumer<MessageContext> action;
        private Consumer<MessageContext> postAction;
        private List<Reply> replies;
        private Predicate<Update>[] flags;

        private AbilityBuilder() {
            statsEnabled = false;
            replies = Lists.newArrayList();
        }

        public AbilityBuilder action(Consumer<MessageContext> consumer) {
            this.action = consumer;
            return this;
        }

        public AbilityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AbilityBuilder info(String info) {
            this.info = info;
            return this;
        }

        public AbilityBuilder flag(Predicate<Update>... flags) {
            this.flags = flags;
            return this;
        }

        public AbilityBuilder locality(Locality type) {
            this.locality = type;
            return this;
        }

        public AbilityBuilder input(int argNum) {
            this.argNum = argNum;
            return this;
        }

        public AbilityBuilder enableStats() {
            statsEnabled = true;
            return this;
        }

        public AbilityBuilder setStatsEnabled(boolean statsEnabled) {
            this.statsEnabled = statsEnabled;
            return this;
        }

        public AbilityBuilder privacy(Privacy privacy) {
            this.privacy = privacy;
            return this;
        }

        public AbilityBuilder post(Consumer<MessageContext> postAction) {
            this.postAction = postAction;
            return this;
        }

        public AbilityBuilder reply(BiConsumer<BaseAbilityBot, Update> action, Predicate<Update>... conditions) {
            replies.add(Reply.of(action, conditions));
            return this;
        }

        public AbilityBuilder reply(Reply reply) {
            replies.add(reply);
            return this;
        }

        public AbilityBuilder basedOn(Ability ability) {
            replies.clear();
            replies.addAll(ability.replies());

            return name(ability.name())
                    .info(ability.info())
                    .input(ability.tokens())
                    .locality(ability.locality())
                    .privacy(ability.privacy())
                    .action(ability.action())
                    .post(ability.postAction());
        }

        public Ability build() {
            return new Ability(name, info, locality, privacy, argNum, statsEnabled, action, postAction, replies, flags);
        }
    }
}


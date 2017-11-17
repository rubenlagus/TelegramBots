# Advanced
This will be more of a FAQ on some important notes before you embark on your next big bot project!

## Default Abilties

It is possible to declare "DEFAULT" abilities that process non-command messages. This is quite close to a reply. If a user says "Hey there" and the default ability is implemented, it will process this input.
```java
 /**
   * This ability has an extra "flag". It needs a photo to activate! This feature is activated by default if there is no /command given.
   */
  public Ability sayNiceToPhoto() {
    return Ability.builder()
        .name(DEFAULT) // DEFAULT ability is executed if user did not specify a command -> Bot needs to have access to messages (check FatherBot)
        .flag(PHOTO)
        .privacy(PUBLIC)
        .locality(ALL)
        .input(0)
        .action(ctx -> silent.send("Daaaaang, what a nice photo!", ctx.chatId()))
        .build();
  }
```

This ability will send a *"Daaaaang, what a nice photo!"* whenever the bot receives a photo. This is one use case where replies and abilities are interchangeable.

## The Global Flag
There is a global flag in AbilityBot that restricts the kind of "updates" it can process. The default implementation is passthrough - it allows all updates to be processed.
As an example, if you want to restrict the updates to photos only, then you may do:

```java
  @Override
  public boolean checkGlobalFlags(Update update) {
    return Flag.PHOTO;
  }
```
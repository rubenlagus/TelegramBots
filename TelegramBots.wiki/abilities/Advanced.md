# Advanced
This will be more of a FAQ on some important notes before you embark on your next big bot project!

## Default Abilities

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
    return Flag.PHOTO.test(update);
  }
```

## Custom Command Processing
### Command Prefix
Customizing the command prefix is as simple as overriding the `getCommandPrefix` method as shown below.
```java
@Override
protected String getCommandPrefix() {
  return "!";
}
```

### Command Regex Split
The method that the bot uses to capture command tokens is through the regex splitters. By default, it's set to `" "`. However, this can be customized. For example,
if you'd like to split on digits and whitespaces, then you may do the following: 
```java
@Override
protected String getCommandRegexSplit() {
  return "\\s\\d";
}
```
### Commands with Continuous Text
Feeling ambitious? You may allow your bot to process tokens that are technically attached to your command. Imagine you have a command
`/do` and you'd like users to send commands as `/do1` and still trigger the `do` ability. In order to do that, override the `allowContinuousText` function.
```java
@Override
protected boolean allowContinuousText() {
  return true;
}
```
Please note that this may cause ability overlap. If multiple abilities can match the same command, the longest match will be taken. For example, 
if you have two abilities `do` and `do1`, the command `/do1` will trigger the `do1` ability. 
## Statistics
AbilityBot can accrue basic statistics about the usage of your abilities and replies. Simply `enableStats()` on an Ability builder or `enableStats(<name>)` on replies to activate this feature. Once activated, you may call `/stats` and the bot will print a basic list of statistics. At the moment, AbilityBot only tracks hits. In the future, this will be enhanced to track more stats.

## Execute code on bot registration
If you want to execute custom logic to initialize your bot, but you can't do it in the constructor,
you can override the `onRegister()` method:
```
@Override
public void onRegister() { 
  super.onRegister();
  // Execute custom initialize logic here
}
```

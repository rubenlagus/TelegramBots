# Ability Extensions
You have around 100 abilities in your bot and you're looking for a way to refactor that mess into more modular classes. `AbillityExtension` is here to support just that! It's not a secret that AbilityBot uses refactoring backstage to be able to construct all of your abilities and map them accordingly. However, AbilityBot searches initially for all methods that return an `AbilityExtension` type. Then, those extensions will be used to search for declared abilities. Here's an example.
```java
public class MrGoodGuy implements AbilityExtension {
  
  private final SilentSender silent;

  public MrGoodGuy(SilentSender silent) {
    this.silent = silent;
  }

  public Ability beenGoodGuy() {
    return Ability.builder()
           .name("nice")
           .privacy(PUBLIC)
           .locality(ALL)
           .action(ctx -> silent.send("You're awesome!", ctx.chatId())
          );
  }
}

public class MrBadGuy implements AbilityExtension {

  private final SilentSender silent;
  
  public MrBadGuy(SilentSender silent) {
    this.silent = silent;
  }

  public Ability beenBadGuy() {
    return Ability.builder()
           .name("notnice")
           .privacy(PUBLIC)
           .locality(ALL)
           .action(ctx -> silent.send("You're horrible!", ctx.chatId())
          );
  }
 }
 
 public class YourAwesomeBot implements AbilityBot {
    
    // Constructor for your bot
  
    public AbilityExtension goodGuy() {
      return new MrGoodGuy(silent);
    }
    
    public AbilityExtension badGuy() {
      return new MrBadGuy(silent);
    }
    
    // Override creatorId
 }
```

In case when your ability should use, for example, `creatorId`, then you have to use `call-by-name`-like variables. Otherwise, you will receive `NullPointerException` during bot initialization.
```java
public class SilenceAbility implements AbilityExtension {

  private final SilentSender silent;
  private final Supplier<Integer> getCreatorId;
  
  public SilenceAbility(SilentSender silent, Supplier<Integer> getCreatorId) {
    this.silent = silent;
    this.getCreatorId = getCreatorId;
  }

  public Reply ignoreAnyoneExceptCreator() {
    Consumer<Update> action = upd -> silent.send(
            "The bot is under construction... Try again later.",
            AbilityUtils.getChatId(upd)
    );
    
    // Catch all messages if they are not from the creator
    return Reply.of(action, x -> !Objects.equals(x.getMessage().getFrom().getId(), getCreatorId.get()));
  }
 }
 
 public class YourAwesomeBot implements AbilityBot {
    
    // Constructor for your bot
  
    public AbilityExtension ignoreAnyoneExceptCreator() {
        return new SilenceAbility(silent, this::creatorId); // pass method reference instead of variable
    }
    
    // Override creatorId
 }
```
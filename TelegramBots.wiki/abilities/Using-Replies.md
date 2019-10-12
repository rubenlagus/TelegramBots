# Replies

A reply is AbilityBot's swiss army knife. It comes in two variants and is able to handle all possible use cases.

## Standalone Reply
Standalone replies are replies declared on their own without being attached to an ability. Here's an example of a possible reply declaration:
```java
/**
* A reply that says "yuck" to all images sent to the bot.
*/
public Reply sayYuckOnImage() {
  // getChatId is a public utility function in rg.telegram.abilitybots.api.util.AbilityUtils
  Consumer<Update> action = upd -> silent.send("Yuck", getChatId(upd)); 
  
  return Reply.of(action, Flag.PHOTO)
}
```

Let's break this down. Replies require a lambda function (consumer) that is able to consume our update. In this case, our consumer simply fetches the chatId
from the update and sends a "Yuck" message. `Reply.of(upd)` would be enough. However, replies accept a var-arg of type `Predicate<Update>`. These predicates are the necessary conditions so that the bot acts the reply. We specify Flag.PHOTO to let the bot know
 that we only want the reply to act on images only! The Flag is a public enum at your disposal. It contains other conditionals like checking for videos, messages, voice, documents, etc...
 
## Ability Reply
In exactly the same manner, you are able to attach replies to abilities. This way you can localize replies that relate to the same ability.
```java
public Ability playWithMe() {
    String playMessage = "Play with me!";

    return Ability.builder()
        .name("play")
        .info("Do you want to play with me?")
        .privacy(PUBLIC)
        .locality(ALL)
        .input(0)
        .action(ctx -> silent.forceReply(playMessage, ctx.chatId()))
        // The signature of a reply is -> (Consumer<Update> action, Predicate<Update>... conditions)
        // So, we  first declare the action that takes an update (NOT A MESSAGECONTEXT) like the action above
        // The reason of that is that a reply can be so versatile depending on the message, context becomes an inefficient wrapping
        .reply(upd -> {
              // Prints to console
              System.out.println("I'm in a reply!");
              // Sends message
              silent.send("It's been nice playing with you!", upd.getMessage().getChatId());
            },
            // Now we start declaring conditions, MESSAGE is a member of the enum Flag class
            // That class contains out-of-the-box predicates for your replies!
            // MESSAGE means that the update must have a message
            // This is imported statically, Flag.MESSAGE
            MESSAGE,
            // REPLY means that the update must be a reply, Flag.REPLY
            REPLY,
            // A new predicate user-defined
            // The reply must be to the bot
            isReplyToBot(),
            // If we process similar logic in other abilities, then we have to make this reply specific to this message
            // The reply is to the playMessage
            isReplyToMessage(playMessage)
        )
        // You can add more replies by calling .reply(...)
        .build();
  }
  
    private Predicate<Update> isReplyToMessage(String message) {
      return upd -> {
        Message reply = upd.getMessage().getReplyToMessage();
        return reply.hasText() && reply.getText().equalsIgnoreCase(message);
      };
    }
  
    private Predicate<Update> isReplyToBot() {
      return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    }
```

In this example, we showcase how we can supply our own predicates. The two new predicates are `isReplyToMessage` and `isReplyToBot`. 
The checks are made so that, once you execute your logic there is no need to check for the validity of the reply.
They were all made once the action logic is being executed.
